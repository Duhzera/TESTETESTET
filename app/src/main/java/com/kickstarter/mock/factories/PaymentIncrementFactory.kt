package com.kickstarter.mock.factories

import com.kickstarter.models.PaymentIncrement
import com.kickstarter.models.PaymentIncrementAmount
import com.kickstarter.type.CurrencyCode
import org.joda.time.DateTime

class PaymentIncrementFactory {
    companion object {

        fun paymentIncrement(
            paymentIncrementAmount: PaymentIncrementAmount,
            paymentIncrementableId: String,
            paymentIncrementableType: String,
            scheduledCollection: DateTime,
            state: PaymentIncrement.State,
            stateReason: String?,
        ): PaymentIncrement {
            return PaymentIncrement.builder()
                .amount(paymentIncrementAmount)
                .paymentIncrementableId(paymentIncrementableId)
                .paymentIncrementableType(paymentIncrementableType)
                .scheduledCollection(scheduledCollection)
                .state(state)
                .stateReason(stateReason)
                .build()
        }

        fun amount(
            formattedAmount: String?,
            formattedAmountWithCode: String?,
            amountAsCents: String?,
            amountAsFloat: String?,
            currencyCode: String?,
        ): PaymentIncrementAmount {
            return PaymentIncrementAmount.builder()
                .formattedAmount(formattedAmount)
                .formattedAmountWithCode(formattedAmountWithCode)
                .amountAsCents(amountAsCents)
                .amountAsFloat(amountAsFloat)
                .currencyCode(currencyCode)
                .build()
        }

        fun incrementUsdUncollected(dateTime: DateTime, formattedAmount: String): PaymentIncrement {
            return paymentIncrement(
                paymentIncrementAmount = PaymentIncrementFactory.amount(formattedAmount = formattedAmount, formattedAmountWithCode = "USD $99.75", amountAsFloat = "99.75", amountAsCents = "9975", currencyCode = CurrencyCode.USD.rawValue),
                scheduledCollection = dateTime,
                paymentIncrementableId = "",
                paymentIncrementableType = "",
                state = PaymentIncrement.State.UNATTEMPTED,
                stateReason = ""
            )
        }

        fun incrementUsdCollected(dateTime: DateTime, formattedAmount: String): PaymentIncrement {
            return paymentIncrement(
                paymentIncrementAmount = PaymentIncrementFactory.amount(formattedAmount = formattedAmount, formattedAmountWithCode = "USD $99.75", amountAsFloat = "99.75", amountAsCents = "9975", currencyCode = CurrencyCode.USD.rawValue),
                scheduledCollection = dateTime,
                paymentIncrementableId = "",
                paymentIncrementableType = "",
                state = PaymentIncrement.State.COLLECTED,
                stateReason = ""
            )
        }

        fun samplePaymentIncrements(): List<PaymentIncrement> {
            val now = DateTime.now()

            return listOf(
                PaymentIncrementFactory.paymentIncrement(
                    paymentIncrementAmount = PaymentIncrementFactory.amount(formattedAmount = "$60.00", formattedAmountWithCode = "USD $99.75", amountAsFloat = "99.75", amountAsCents = "9975", currencyCode = CurrencyCode.USD.rawValue),
                    state = PaymentIncrement.State.UNATTEMPTED,
                    paymentIncrementableId = "1",
                    paymentIncrementableType = "pledge",
                    scheduledCollection = now.plusDays(15),
                    stateReason = ""
                ),
                PaymentIncrementFactory.paymentIncrement(
                    paymentIncrementAmount = PaymentIncrementFactory.amount(formattedAmount = "$60.00", formattedAmountWithCode = "USD $99.75", amountAsFloat = "99.75", amountAsCents = "9975", currencyCode = CurrencyCode.USD.rawValue),
                    state = PaymentIncrement.State.COLLECTED,
                    paymentIncrementableId = "2",
                    paymentIncrementableType = "pledge",
                    scheduledCollection = now.plusDays(30),
                    stateReason = ""
                ),
                PaymentIncrementFactory.paymentIncrement(
                    paymentIncrementAmount = PaymentIncrementFactory.amount(formattedAmount = "$60.00", formattedAmountWithCode = "USD $99.75", amountAsFloat = "99.75", amountAsCents = "9975", currencyCode = CurrencyCode.USD.rawValue),
                    state = PaymentIncrement.State.UNATTEMPTED,
                    paymentIncrementableId = "3",
                    paymentIncrementableType = "pledge",
                    scheduledCollection = now.plusDays(45),
                    stateReason = ""
                ),
                PaymentIncrementFactory.paymentIncrement(
                    paymentIncrementAmount = PaymentIncrementFactory.amount(formattedAmount = "$60.00", formattedAmountWithCode = "USD $99.75", amountAsFloat = "99.75", amountAsCents = "9975", currencyCode = CurrencyCode.USD.rawValue),
                    state = PaymentIncrement.State.COLLECTED,
                    paymentIncrementableId = "4",
                    paymentIncrementableType = "pledge",
                    scheduledCollection = now.plusDays(60),
                    stateReason = ""
                )
            )
        }
    }
}
