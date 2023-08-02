package com.kickstarter.viewmodels.projectpage

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kickstarter.libs.Environment
import com.kickstarter.models.AiDisclosure
import com.kickstarter.ui.data.ProjectData

class ProjectAIViewModel private constructor(private val environment: Environment) : ViewModel() {
    var state by mutableStateOf(UiState())
        private set

    /**
     * Equivalent to Outputs interface in the on old ViewModels
     * UIState represents the possible UIState changes
     * and the data objects required to populate the state.
     */
    data class UiState(
        val openExternalUrl: String = AIPOLICY,
        val aiDisclosure: AiDisclosure? = null
    )

    /**
     * Equivalent to Inputs interface in the on old ViewModels
     * Event represents the event flowing  UIState changes
     * and the data objects required to populate the state.
     */
    data class Event(
        val externalClicked: Boolean = false,
        val projectData: ProjectData? = null
    )

    fun eventUpdate(event: Event) {
        val disclosure = event.projectData?.project()?.aiDisclosure()
        state = UiState(aiDisclosure = disclosure)
    }

    companion object {
        // "https://help.kickstarter.com/hc/en-us/articles/16848396410267" // - not accesible yet to users, ask stakeholder if URL might change before release
        const val AIPOLICY = "https://help.kickstarter.com/hc/en-us/articles/16848396410267"
    }

    class Factory(private val environment: Environment) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ProjectAIViewModel(environment = environment) as T
        }
    }
}
