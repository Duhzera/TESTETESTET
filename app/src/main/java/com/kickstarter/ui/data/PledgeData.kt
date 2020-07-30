package com.kickstarter.ui.data

import android.os.Parcelable
import auto.parcel.AutoParcel
import com.kickstarter.models.Reward
import java.util.List

@AutoParcel
abstract class PledgeData : Parcelable {
    abstract fun pledgeFlowContext(): PledgeFlowContext
    abstract fun projectData(): ProjectData
    abstract fun addOns(): List<Reward>?
    abstract fun reward(): Reward

    @AutoParcel.Builder
    abstract class Builder {
        abstract fun pledgeFlowContext(pledgeFlowContext: PledgeFlowContext): Builder
        abstract fun projectData(projectData: ProjectData): Builder
        abstract fun reward(reward: Reward): Builder
        abstract fun addOns(rewards: List<Reward>): Builder
        abstract fun build(): PledgeData
    }

    abstract fun toBuilder(): Builder

    companion object {

        fun builder(): Builder {
            return AutoParcel_PledgeData.Builder()
        }

        fun with(pledgeFlowContext: PledgeFlowContext, projectData: ProjectData, reward: Reward, addOns: List<Reward>? = null) =
                addOns?.let {
                    return@let builder()
                        .pledgeFlowContext(pledgeFlowContext)
                        .projectData(projectData)
                        .reward(reward)
                        .addOns(it)
                        .build()
                }?: builder()
                    .pledgeFlowContext(pledgeFlowContext)
                    .projectData(projectData)
                    .reward(reward)
                    .build()
    }
}
