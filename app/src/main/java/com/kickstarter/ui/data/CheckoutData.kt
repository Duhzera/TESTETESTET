package com.kickstarter.ui.data

import android.os.Parcelable
import auto.parcel.AutoParcel
import type.CreditCardPaymentType

@AutoParcel
abstract class CheckoutData : Parcelable {
    abstract fun amount(): Double
    abstract fun id(): Long
    abstract fun paymentType(): ProjectData
    abstract fun revenueInUSDCents(): Int
    abstract fun shippingAmount(): Double?

    @AutoParcel.Builder
    abstract class Builder {
        abstract fun amount(amount: Double): Builder
        abstract fun id(id: Long): Builder
        abstract fun paymentType(paymentType: CreditCardPaymentType): Builder
        abstract fun revenueInUSDCents(revenueInCents: Int): Builder
        abstract fun shippingAmount(shippingAmount: Double): Builder
        abstract fun build(): CheckoutData
    }

    abstract fun toBuilder(): Builder

    companion object {

        fun builder(): Builder {
            return AutoParcel_CheckoutData.Builder()
        }
    }
}
