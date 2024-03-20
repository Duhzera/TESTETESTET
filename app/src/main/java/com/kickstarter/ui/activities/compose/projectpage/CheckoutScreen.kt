package com.kickstarter.ui.activities.compose.projectpage

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.sp
import androidx.core.text.HtmlCompat
import com.kickstarter.R
import com.kickstarter.libs.Environment
import com.kickstarter.libs.KSString
import com.kickstarter.libs.utils.DateTimeUtils
import com.kickstarter.libs.utils.RewardViewUtils
import com.kickstarter.libs.utils.extensions.acceptedCardType
import com.kickstarter.libs.utils.extensions.hrefUrlFromTranslation
import com.kickstarter.libs.utils.extensions.isNotNull
import com.kickstarter.libs.utils.extensions.stringsFromHtmlTranslation
import com.kickstarter.mock.factories.RewardFactory
import com.kickstarter.mock.factories.StoredCardFactory
import com.kickstarter.models.Project
import com.kickstarter.models.Reward
import com.kickstarter.models.ShippingRule
import com.kickstarter.models.StoredCard
import com.kickstarter.models.extensions.getCardTypeDrawable
import com.kickstarter.models.extensions.isFromPaymentSheet
import com.kickstarter.ui.activities.DisclaimerItems
import com.kickstarter.ui.activities.compose.login.LoginToutTestTag
import com.kickstarter.ui.compose.designsystem.KSButton
import com.kickstarter.ui.compose.designsystem.KSPrimaryGreenButton
import com.kickstarter.ui.compose.designsystem.KSRadioButton
import com.kickstarter.ui.compose.designsystem.KSTheme
import com.kickstarter.ui.compose.designsystem.KSTheme.colors
import com.kickstarter.ui.compose.designsystem.KSTheme.dimensions
import com.kickstarter.ui.compose.designsystem.KSTheme.typography
import com.kickstarter.ui.compose.designsystem.kds_white
import com.kickstarter.ui.compose.designsystem.shapes
import com.kickstarter.ui.data.PledgeReason
import type.CreditCardTypes
import java.math.RoundingMode
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
@Preview(name = "Light", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(name = "Dark", uiMode = Configuration.UI_MODE_NIGHT_YES)
fun CheckoutScreenPreview() {
    KSTheme {
        val storedCards = listOf(
                StoredCardFactory.visa(), StoredCardFactory.discoverCard(), StoredCardFactory.visa()
        )
        CheckoutScreen(
                isCTAButtonEnabled = true,
                rewardsList = (1..6).map {
                    Pair("Cool Item $it", "$20")
                },
                environment = Environment.Builder().build(),
                shippingAmount = 4.0,
                selectedReward = RewardFactory.rewardWithShipping(),
                currentShippingRule = ShippingRule.builder().build(),
                totalAmount = 60.0,
                totalBonusSupport = 5.0,
                storedCards = listOf(
                        StoredCardFactory.visa(), StoredCardFactory.discoverCard(), StoredCardFactory.visa()
                ),
                project =
                Project.builder()
                        .currency("USD")
                        .currentCurrency("USD")
                        .state(Project.STATE_LIVE)
                        .availableCardTypes(listOf(
                                CreditCardTypes.AMEX.rawValue(),
                                CreditCardTypes.MASTERCARD.rawValue(),
                                CreditCardTypes.VISA.rawValue()
                        ))
                        .build(),
                pledgeReason = PledgeReason.PLEDGE,
                onPledgeCtaClicked = { },
                newPaymentMethodClicked = { }
        )
    }
}

@Composable
fun CheckoutScreen(
        storedCards : List<StoredCard>? = null,
        isCTAButtonEnabled: Boolean,
        environment : Environment,
        selectedReward: Reward? = null,
        project: Project,
        ksString: KSString? = null,
        rewardsList: List<Pair<String, String>> = listOf(),
        shippingAmount: Double = 0.0,
        pledgeReason: PledgeReason,
        totalAmount: Double,
        currentShippingRule: ShippingRule,
        totalAmountConverted: Double = 0.0,
        totalBonusSupport: Double = 0.0,
        onPledgeCtaClicked: () -> Unit,
        newPaymentMethodClicked: () -> Unit
) {
    Scaffold(
            backgroundColor = colors.backgroundAccentGraySubtle,
            modifier = Modifier
                    .background(kds_white)
                    .fillMaxWidth(),
            bottomBar = {
                Column {
                    Surface(
                            modifier = Modifier
                                    .fillMaxWidth(),
                            shape = RoundedCornerShape(
                                    topStart = dimensions.radiusLarge,
                                    topEnd = dimensions.radiusLarge
                            ),
                            color = colors.backgroundSurfacePrimary,
                            elevation = dimensions.elevationLarge,
                    ) {
                        Column(modifier = Modifier
                                .background(colors.kds_white)
                                .padding(bottom = dimensions.paddingMediumLarge, start = dimensions.paddingMediumLarge, end = dimensions.paddingMediumLarge, top = dimensions.paddingMediumLarge)
                        ) {

                            KSPrimaryGreenButton(
                                    modifier = Modifier
                                            .padding(bottom = dimensions.paddingMediumSmall)
                                            .fillMaxWidth(),
                                    onClickAction = onPledgeCtaClicked,
                                    isEnabled = !storedCards.isNullOrEmpty() && isCTAButtonEnabled, //feel free to remove one of these, just wanted to give the option of passing in the value or setting it here based on the information we have
                                    text = if (pledgeReason == PledgeReason.PLEDGE) stringResource(id = R.string.Pledge) else stringResource(id = R.string.Confirm)
                            )

                            Text(text = "Your payment method will be charged immediately upon pledge. You’ll receive a confirmation email at %{email} when your rewards are ready to fulfill so that you can finalize and pay shipping and tax.", textAlign = TextAlign.Center,
                                    style = typography.caption2, color = colors.kds_support_400)

                            Spacer(modifier = Modifier.height(dimensions.paddingMediumSmall))

                            TermsOfUseClickableText(
                                    onPrivacyPolicyClicked = {},
                                    onCookiePolicyClicked = {},
                                    onTermsOfUseClicked = {}
                            )
                        }
                    }
                }
            },

            ) { padding ->

        val totalAmountString = environment.ksCurrency()?.let {
            RewardViewUtils.styleCurrency(
                    totalAmount,
                    project,
                    it
            ).toString()
        } ?: ""

        val totalAmountConvertedString = if (totalAmountConverted.equals(0.0)) "" else
            environment.ksCurrency()?.format(
                    totalAmountConverted,
                    project,
                    true,
                    RoundingMode.HALF_UP,
                    true
            ) ?: ""

        val aboutTotalString = if (totalAmountConvertedString.isEmpty()) "" else environment.ksString()?.format(
                stringResource(id = R.string.About_reward_amount),
                "reward_amount",
                totalAmountConvertedString
        ) ?: "About $totalAmountConvertedString"

        val shippingLocation = currentShippingRule.location()?.displayableName() ?: ""

        val shippingLocationString = environment.ksString()?.format(
                stringResource(id = R.string.Shipping_to_country),
                "country",
                shippingLocation
        ) ?: "Shipping: $shippingLocation"

        val deliveryDateString = if (selectedReward?.estimatedDeliveryOn().isNotNull()) {
            stringResource(id = R.string.Estimated_delivery) + " " + DateTimeUtils.estimatedDeliveryOn(
                    requireNotNull(
                            selectedReward?.estimatedDeliveryOn()
                    )
            )
        } else {
            ""
        }
        Column(modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(padding)) {

            Text(
                    modifier = Modifier.padding(start = dimensions.paddingMediumLarge, top = dimensions.paddingMediumLarge),
                    text = "Checkout",
                    style = typography.title3Bold,
                    color = colors.kds_black,
            )
            Spacer(modifier = Modifier.height(dimensions.paddingMediumSmall))


            if (!storedCards.isNullOrEmpty()) {
                var (selectedOption, onOptionSelected) = remember { mutableStateOf(storedCards.firstOrNull { project.acceptedCardType(it.type())} ) }
                    storedCards.forEach {
                        val isAvailable = project.acceptedCardType(it.type()) || it.isFromPaymentSheet()
                        Card(
                                backgroundColor = colors.kds_white,
                                modifier = Modifier
                                .padding(start = dimensions.paddingMedium, end = dimensions.paddingMedium)
                                .fillMaxWidth()
                                .selectableGroup()
                                .selectable(
                                        enabled = isAvailable,
                                        selected = it == selectedOption,
                                        onClick = {
                                            onOptionSelected(it)
                                        })) {
                            Column {
                                Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier
                                                .padding(
                                                        top = dimensions.paddingSmall,
                                                        bottom = dimensions.paddingSmall
                                                )
                                ) {

                                    KSRadioButton(
                                            selected = it == selectedOption, onClick = { onOptionSelected(it) }, enabled = isAvailable)

                                    KSCardElement(card = it, environment.ksString(), isAvailable)
                                }

                                if (!isAvailable) {
                                    Text(
                                            modifier = Modifier.padding(start = dimensions.paddingDoubleLarge, end = dimensions.paddingMediumLarge, bottom = dimensions.paddingSmall),
                                            style = typography.caption1Medium,
                                            color = colors.kds_alert,
                                            text = stringResource(id = R.string.This_project_has_a_set_currency_that_cant_process_this_option))
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(dimensions.paddingXSmall))
                }

                Card(
                        backgroundColor = colors.kds_white,
                        modifier = Modifier
                        .padding(start = dimensions.paddingMedium, end = dimensions.paddingMedium)
                        .clickable { newPaymentMethodClicked.invoke() }
                        .fillMaxWidth()) {
                    Row(horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(top = dimensions.paddingMedium, bottom = dimensions.paddingMedium)) {
                        Icon(
                                painter = painterResource(id = R.drawable.ic_add_rounded),
                                contentDescription = "",
                                tint = colors.textAccentGreen,
                                modifier = Modifier.background(color = colors.kds_create_700.copy(alpha = 0.2f), CircleShape))

                        Text(
                                modifier = Modifier.padding(start = dimensions.paddingSmall),
                                color = colors.textAccentGreen,
                                style = typography.subheadlineMedium,
                                text = stringResource(id = R.string.New_payment_method))
                    }
                }

                Spacer(modifier = Modifier.height(dimensions.paddingLarge))

                Card(
                        modifier = Modifier.padding(start = dimensions.paddingMedium, end = dimensions.paddingMedium),
                        shape = RoundedCornerShape(
                                bottomStart = dimensions.radiusMediumLarge,
                                bottomEnd = dimensions.radiusMediumLarge,
                                topStart = dimensions.radiusMediumLarge,
                                topEnd = dimensions.radiusMediumLarge
                        ),
                        backgroundColor = colors.kds_support_200,
                ) {
                    Row(modifier = Modifier.padding(dimensions.paddingSmall)) {
                        Icon(
                                modifier = Modifier
                                        .padding(start = dimensions.paddingMediumSmall, end = dimensions.paddingLarge)
                                        .align(Alignment.CenterVertically),
                                painter = painterResource(id = R.drawable.ic_not_a_store),
                                contentDescription = null,
                                tint = colors.textAccentGreen
                        )

                        Column {

                            Text(modifier = Modifier.padding(bottom = dimensions.paddingXSmall, top = dimensions.paddingXSmall),
                                    text = stringResource(id = R.string.Kickstarter_is_not_a_store), style = typography.body2Medium, color = colors.kds_support_400)
                            TextWithClickableAccountabilityLink(
                                    padding = dimensions.paddingXSmall,
                                    html = stringResource(id = R.string.Its_a_way_to_bring_creative_projects_to_life_Learn_more_about_accountability),
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(dimensions.paddingMediumSmall))


                if (rewardsList.isNotEmpty()) {

                    ItemizedRewardListContainer(
                            ksString = ksString,
                            rewardsList = rewardsList,
                            shippingAmount = if (shippingAmount == 0.0) ""
                            else {
                                environment.ksCurrency()?.format(
                                        shippingAmount,
                                        project,
                                        true,
                                        RoundingMode.HALF_UP,
                                        true
                                ) ?: ""
                            },
                            initialShippingLocation = shippingLocationString,
                            totalAmount = totalAmountString,
                            totalAmountCurrencyConverted = aboutTotalString,
                            initialBonusSupport = "",
                            totalBonusSupport = if (totalBonusSupport > 0.0) {
                                environment.ksCurrency()?.let { ksCurrency ->
                                    RewardViewUtils.styleCurrency(
                                            totalBonusSupport,
                                            project,
                                            ksCurrency
                                    ).toString()
                                } ?: ""
                            } else "",
                            deliveryDateString = deliveryDateString
                    )
                } else {
                    ItemizedRewardListContainer(
                            totalAmount = totalAmountString,
                            totalAmountCurrencyConverted = aboutTotalString,
                            rewardsList = (1..1).map {
                                Pair(stringResource(id = R.string.Pledge_without_a_reward), totalAmountString)
                            },
                            initialBonusSupport = "",
                            totalBonusSupport = ""
                    )
                }
            }
        }
    }
}




    @Composable
    fun TermsOfUseClickableText(
            onTermsOfUseClicked: () -> Unit,
            onPrivacyPolicyClicked: () -> Unit,
            onCookiePolicyClicked: () -> Unit
    ) {
        val formattedText = HtmlCompat.fromHtml(
                stringResource(id = R.string.By_pledging_you_agree_to_Kickstarters_Terms_of_Use_Privacy_Policy_and_Cookie_Policy),
                0
        ).toString()

        val annotatedLinkString = buildAnnotatedString {
            val termsOfUseString =
                    stringResource(id = R.string.login_tout_help_sheet_terms).lowercase()
            val termsOfUseStartIndex = formattedText.indexOf(
                    string = termsOfUseString,
                    ignoreCase = true
            )
            val termsOfUserEndIndex = termsOfUseStartIndex + termsOfUseString.length

            val privacyPolicyString =
                    stringResource(id = R.string.login_tout_help_sheet_privacy).lowercase()
            val privacyPolicyStartIndex = formattedText.indexOf(
                    string = privacyPolicyString,
                    ignoreCase = true
            )
            val privacyPolicyEndIndex = privacyPolicyStartIndex + privacyPolicyString.length

            val cookiePolicyString =
                    stringResource(id = R.string.login_tout_help_sheet_cookie).lowercase()
            val cookiePolicyStartIndex = formattedText.indexOf(
                    string = cookiePolicyString,
                    ignoreCase = true
            )
            val cookiePolicyEndIndex = cookiePolicyStartIndex + cookiePolicyString.length

            append(formattedText)

            if (termsOfUseStartIndex != -1) {
                addStyle(
                        style = SpanStyle(
                                color = colors.textAccentGreen
                        ),
                        start = termsOfUseStartIndex,
                        end = termsOfUserEndIndex
                )

                addStringAnnotation(
                        tag = DisclaimerItems.TERMS.name,
                        annotation = "",
                        start = termsOfUseStartIndex,
                        end = termsOfUserEndIndex
                )
            }

            if (privacyPolicyStartIndex != -1) {
                addStyle(
                        style = SpanStyle(
                                color = colors.textAccentGreen
                        ),
                        start = privacyPolicyStartIndex,
                        end = privacyPolicyEndIndex
                )

                addStringAnnotation(
                        tag = DisclaimerItems.PRIVACY.name,
                        annotation = "",
                        start = privacyPolicyStartIndex,
                        end = privacyPolicyEndIndex
                )
            }

            if (cookiePolicyStartIndex != -1) {
                addStyle(
                        style = SpanStyle(
                                color = colors.textAccentGreen
                        ),
                        start = cookiePolicyStartIndex,
                        end = cookiePolicyEndIndex
                )

                addStringAnnotation(
                        tag = DisclaimerItems.COOKIES.name,
                        annotation = "",
                        start = cookiePolicyStartIndex,
                        end = cookiePolicyEndIndex
                )
            }
        }

        ClickableText(
                modifier = Modifier.testTag(LoginToutTestTag.TOU_PP_COOKIE_DISCLAIMER.name),
                text = annotatedLinkString,
                style = typography.caption2.copy(
                        color = colors.kds_support_400,
                        textAlign = TextAlign.Center
                ),
                onClick = { index ->
                    annotatedLinkString.getStringAnnotations(index, index)
                            .firstOrNull()?.let { annotation ->
                                when (annotation.tag) {
                                    DisclaimerItems.TERMS.name -> {
                                        onTermsOfUseClicked.invoke()
                                    }

                                    DisclaimerItems.PRIVACY.name -> {
                                        onPrivacyPolicyClicked.invoke()
                                    }

                                    DisclaimerItems.COOKIES.name -> {
                                        onCookiePolicyClicked.invoke()
                                    }
                                }
                            }
                }
        )
    }



@Composable
fun TextWithClickableAccountabilityLink(
        padding: Dp,
        html: String,
        onClickCallback: (String?) -> Unit = {}
) {

    val stringList = html.stringsFromHtmlTranslation()
    val annotation = html.hrefUrlFromTranslation()
    if (stringList.size == 3) {
        val annotatedText = buildAnnotatedString {
            append(stringList.first())
            pushStringAnnotation(
                    tag = annotation,
                    annotation = stringList[1]
            )
            withStyle(
                    style = SpanStyle(
                            color = colors.kds_create_700,
                    )
            ) {
                append(stringList[1])
            }
            append(stringList.last())

            pop()
        }

        ClickableText(
                modifier = Modifier.padding(bottom = padding),
                text = annotatedText,
                style = TextStyle(
                        fontWeight = FontWeight(400),
                        fontSize = 14.sp,
                        lineHeight = 20.sp,
                        letterSpacing = 0.25.sp,
                        color = colors.kds_support_400
                ),
                onClick = {
                    annotatedText.getStringAnnotations(
                            tag = annotation, start = it,
                            end = it
                    )
                            .firstOrNull()?.let { annotation ->
                                onClickCallback(annotation.tag)
                            } ?: onClickCallback(null)
                }
        )
    }
}

@Composable
@Preview(name = "Light", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(name = "Dark", uiMode = Configuration.UI_MODE_NIGHT_YES)
fun KSCardElementPreview() {
    KSTheme {
        KSCardElement(
                StoredCardFactory.visa(), Environment.builder().build().ksString(), true
        )
    }
}

@Composable
fun KSCardElement(card : StoredCard, ksString: KSString?, isAvailable : Boolean) {
        val sdf = SimpleDateFormat(StoredCard.DATE_FORMAT, Locale.getDefault())

        val expirationString = ksString?.let {
            card.expiration()?.let { expiration ->
                ksString.format(
                        stringResource(id = R.string.Credit_card_expiration),
                        "expiration_date", sdf.format(expiration).toString())
            }
        } ?: "Expiration Date 3/2025sfgdfhgdfkhgkdfhgkdjfhkghdfkghkdfhgkdfhgkdhfg"

        val lastFourString = ksString?.let {
            card.lastFourDigits()?.let { lastFour ->
                ksString.format(
                        stringResource(id = R.string.payment_method_last_four),
                        "last_four", lastFour
                )
            }
        } ?: ".... 1234"

            Row {
                Image(
                        modifier = Modifier
                                .padding(end = dimensions.paddingMediumSmall)
                                .align(Alignment.CenterVertically)
                                .width(dimensions.storedCardImageWidth)
                                .height(dimensions.storedCardImageHeight),
                        contentDescription = card.type()?.let { StoredCard.issuer(it)} ,
                        alpha = if (isAvailable) 1.0f else .5f,
                        painter = painterResource(id = card.getCardTypeDrawable()),
                )

                Column(
                        modifier = Modifier.align(Alignment.CenterVertically),
                        verticalArrangement = Arrangement.Center
                ) {
                    if (!lastFourString.isNullOrEmpty()) {
                        Text(
                                modifier = Modifier.padding(end = dimensions.paddingMediumLarge),
                                color = if (isAvailable) colors.kds_support_700 else colors.kds_support_400,
                                style = typography.body2Medium,
                                text = lastFourString
                        )
                    }

                    if (!expirationString.isNullOrEmpty()) {
                        Text(
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier.padding(top = dimensions.paddingXSmall, end = dimensions.paddingMediumLarge ),
                                style = typography.caption2Medium,
                                color = if (isAvailable) colors.kds_support_700 else colors.kds_support_400,
                                text = expirationString
                        )
                    }
                }
            }
        }







