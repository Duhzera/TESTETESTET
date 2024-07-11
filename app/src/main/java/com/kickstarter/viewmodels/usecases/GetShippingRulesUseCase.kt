package com.kickstarter.viewmodels.usecases

import com.kickstarter.libs.Config
import com.kickstarter.libs.utils.RewardUtils
import com.kickstarter.libs.utils.extensions.getDefaultLocationFrom
import com.kickstarter.mock.factories.ShippingRuleFactory
import com.kickstarter.models.Location
import com.kickstarter.models.Project
import com.kickstarter.models.Reward
import com.kickstarter.models.ShippingRule
import com.kickstarter.services.ApolloClientTypeV2
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.rx2.asFlow

data class ShippingRulesState(
    val shippingRules: List<ShippingRule> = emptyList(),
    val loading: Boolean = false,
    val error: String? = null,
    val selectedShippingRule: ShippingRule = ShippingRuleFactory.usShippingRule()
)

/**
 * Will provide ShippingRulesState where:
 * `shippingRules` is the list of available shipping rules for a given project
 * `selectedShippingRule` will be the initial default shipping rule for a given configuration
 *  `error/loading` states for internal networking calls
 *
 *  Should be provided with:
 *  @param scope
 *  @param dispatcher
 *
 *  As the UseCase is lifecycle agnostic and is scoped to the class that uses it.
 */
class GetShippingRulesUseCase(
    private val apolloClient: ApolloClientTypeV2,
    private val project: Project,
    private val config: Config?,
    private val scope: CoroutineScope,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    private var rewardsToQuery: List<Reward>
    init {

        // To avoid duplicates insert reward.id as key
        val rewardsToQuery = mutableMapOf<Long, Reward>()

        // Get first reward with unrestricted shipping preference, when quering `getShippingRules` will return ALL available locations, no need to query more rewards locations
        project.rewards()?.filter { RewardUtils.shipsWorldwide(reward = it) }?.firstOrNull()?.let {
            rewardsToQuery.put(it.id(), it)
        }

        // In case there is no unrestricted preference need to get restricted and local rewards, to query their specific locations
        if (rewardsToQuery.isEmpty()) {
            project.rewards()?.filter {
                RewardUtils.shipsToRestrictedLocations(reward = it)
            }?.forEach {
                rewardsToQuery.put(it.id(), it)
            }
        }

        this.rewardsToQuery = rewardsToQuery.values.toList()
    }

    // - Do not expose mutable states
    private val _mutableShippingRules =
        MutableStateFlow(ShippingRulesState())

    /**
     * Exposes result of this use case
     */
    val shippingRulesState: Flow<ShippingRulesState>
        get() = _mutableShippingRules

    // - IO dispatcher for network operations to avoid blocking main thread
    operator fun invoke() {
        if (rewardsToQuery.isNotEmpty()) {
            val shippingRules = mutableMapOf<Long, ShippingRule>()
            scope.launch(dispatcher) {
                _mutableShippingRules.emit(ShippingRulesState(loading = true))
                rewardsToQuery.forEachIndexed { index, reward ->
                    apolloClient.getShippingRules(reward)
                        .asFlow()
                        .map { rulesEnvelope ->
                            rulesEnvelope.shippingRules()?.map { rule ->
                                rule?.let { shippingRules.put(requireNotNull(it.location()?.id()), it) }
                            }

                            // - Emit ONLY if all the rewards shipping rules have been queried
                            if (index == rewardsToQuery.size - 1) {
                                _mutableShippingRules.emit(
                                    ShippingRulesState(
                                        shippingRules = shippingRules.values.toList(),
                                        loading = false,
                                        selectedShippingRule = getDefaultShippingRule(shippingRules, project)
                                    )
                                )
                            }
                        }
                        .catch { throwable ->
                            _mutableShippingRules.emit(
                                ShippingRulesState(
                                    loading = false,
                                    error = throwable.message
                                )
                            )
                        }.collect()
                }
            }
        }
    }

    /**
     * In case the project is backing, return the backed shippingRule
     * otherwise return the config default shippingRule
     */
    private fun getDefaultShippingRule(
        shippingRules: MutableMap<Long, ShippingRule>,
        project: Project
    ): ShippingRule =
        if (project.isBacking()) ShippingRule.builder()
            .apply {
                val locationId = project.backing()?.locationId() ?: 0L
                val locationName = project.backing()?.locationName() ?: ""

                this.location(Location.Builder().id(locationId).name(locationName).displayableName(locationName).build())
            }
            .build()
        else config?.getDefaultLocationFrom(shippingRules.values.toList()) ?: ShippingRule.builder()
            .build()
}
