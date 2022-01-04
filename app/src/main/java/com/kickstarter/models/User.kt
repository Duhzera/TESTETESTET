package com.kickstarter.models

import android.content.res.Resources
import com.kickstarter.libs.qualifiers.AutoGson
import auto.parcel.AutoParcel
import android.os.Parcelable
import com.kickstarter.R
import kotlinx.android.parcel.Parcelize

@Parcelize
class User private constructor(
    private val alumniNewsletter: Boolean,
    private val artsCultureNewsletter: Boolean,
    private val avatar: Avatar,
    private val backedProjectsCount: Int,
    private val createdProjectsCount: Int,
    private val draftProjectsCount: Int,
    private val erroredBackingsCount: Int,
    private val facebookConnected: Boolean,
    private val filmNewsletter: Boolean,
    private val gamesNewsletter: Boolean,
    private val happeningNewsletter: Boolean,
    private val id: Long,
    private val inventNewsletter: Boolean,
    private val isAdmin: Boolean,
    private val isEmailVerified: Boolean,
    private val chosenCurrency: String,
    private val location: Location,
    private val memberProjectsCount: Int,
    private val musicNewsletter: Boolean,
    private val name: String,
    private val notifyMobileOfBackings: Boolean,
    private val notifyMobileOfComments: Boolean,
    private val notifyMobileOfCreatorEdu: Boolean,
    private val notifyMobileOfFollower: Boolean,
    private val notifyMobileOfFriendActivity: Boolean,
    private val notifyMobileOfMessages: Boolean,
    private val notifyMobileOfPostLikes: Boolean,
    private val notifyMobileOfUpdates: Boolean,
    private val notifyMobileOfMarketingUpdate: Boolean,
    private val notifyOfBackings: Boolean,
    private val notifyOfComments: Boolean,
    private val notifyOfCommentReplies: Boolean,
    private val notifyOfCreatorDigest: Boolean,
    private val notifyOfCreatorEdu: Boolean,
    private val notifyOfFollower: Boolean,
    private val notifyOfFriendActivity: Boolean,
    private val notifyOfMessages: Boolean,
    private val notifyOfUpdates: Boolean,
    private val optedOutOfRecommendations: Boolean,
    private val promoNewsletter: Boolean,
    private val publishingNewsletter: Boolean,
    private val showPublicProfile: Boolean,
    private val social: Boolean,
    private val starredProjectsCount: Int,
    private val unreadMessagesCount: Int,
    private val unseenActivityCount: Int,
    private val weeklyNewsletter: Boolean
): Parcelable, Relay {

    fun alumniNewsletter() = this.alumniNewsletter
    fun artsCultureNewsletter() = this.artsCultureNewsletter
    fun avatar() = this.avatar
    fun backedProjectsCount() = this.backedProjectsCount
    fun createdProjectsCount() = this.createdProjectsCount
    fun draftProjectsCount() = this.draftProjectsCount
    fun erroredBackingsCount() = this.erroredBackingsCount
    fun facebookConnected() = this.facebookConnected
    fun filmNewsletter() = this.filmNewsletter
    fun gamesNewsletter() = this.gamesNewsletter
    fun happeningNewsletter() = this.happeningNewsletter
    override fun id() = this.id
    fun isAdmin() = this.isAdmin
    fun isEmailVerified() = this.isEmailVerified
    fun chosenCurrency() = this.chosenCurrency
    fun inventNewsletter() = this.inventNewsletter
    fun location() = this.location
    fun memberProjectsCount() = this.memberProjectsCount
    fun musicNewsletter() = this.musicNewsletter
    fun name() = this.name
    fun notifyMobileOfBackings() = this.notifyMobileOfBackings
    fun notifyMobileOfComments() = this.notifyMobileOfComments
    fun notifyMobileOfCreatorEdu() = this.notifyMobileOfCreatorEdu
    fun notifyMobileOfFollower() = this.notifyMobileOfFollower
    fun notifyMobileOfFriendActivity() = this.notifyMobileOfFriendActivity
    fun notifyMobileOfMessages() = this.notifyMobileOfMessages
    fun notifyMobileOfPostLikes() = this.notifyMobileOfPostLikes
    fun notifyMobileOfUpdates() = this.notifyMobileOfUpdates
    fun notifyMobileOfMarketingUpdate() = this.notifyMobileOfMarketingUpdate
    fun notifyOfBackings() = this.notifyOfBackings
    fun notifyOfComments() = this.notifyOfComments
    fun notifyOfCommentReplies() = this.notifyOfCommentReplies
    fun notifyOfCreatorDigest() = this.notifyOfCreatorDigest
    fun notifyOfCreatorEdu() = this.notifyOfCreatorEdu
    fun notifyOfFollower() = this.notifyOfFollower
    fun notifyOfFriendActivity() = this.notifyOfFriendActivity
    fun notifyOfMessages() = this.notifyOfMessages
    fun notifyOfUpdates() = this.notifyOfUpdates
    fun optedOutOfRecommendations() = this.optedOutOfRecommendations
    fun promoNewsletter() = this.promoNewsletter
    fun publishingNewsletter() = this.publishingNewsletter
    fun showPublicProfile() = this.showPublicProfile
    fun social() = this.social
    fun starredProjectsCount() = this.starredProjectsCount
    fun unreadMessagesCount() = this.unreadMessagesCount
    fun unseenActivityCount() = this.unseenActivityCount
    fun weeklyNewsletter() = this.weeklyNewsletter

    @Parcelize
    data class Builder(
        private var alumniNewsletter: Boolean = false,
        private var artsCultureNewsletter: Boolean = false,
        private var avatar: Avatar = Avatar.builder().build(),
        private var backedProjectsCount: Int = 0,
        private var createdProjectsCount: Int = 0,
        private var draftProjectsCount: Int = 0,
        private var erroredBackingsCount: Int = 0,
        private var facebookConnected: Boolean = false,
        private var filmNewsletter: Boolean = false,
        private var gamesNewsletter: Boolean = false,
        private var happeningNewsletter: Boolean = false,
        private var id: Long = 0L,
        private var inventNewsletter: Boolean = false,
        private var isAdmin: Boolean = false,
        private var isEmailVerified: Boolean = false,
        private var chosenCurrency: String = "",
        private var location: Location = Location.Builder().build(),
        private var memberProjectsCount: Int = 0,
        private var musicNewsletter: Boolean = false,
        private var name: String = "",
        private var notifyMobileOfBackings: Boolean = false,
        private var notifyMobileOfComments: Boolean = false,
        private var notifyMobileOfCreatorEdu: Boolean = false,
        private var notifyMobileOfFollower: Boolean = false,
        private var notifyMobileOfFriendActivity: Boolean = false,
        private var notifyMobileOfMessages: Boolean = false,
        private var notifyMobileOfPostLikes: Boolean = false,
        private var notifyMobileOfUpdates: Boolean = false,
        private var notifyMobileOfMarketingUpdate: Boolean = false,
        private var notifyOfBackings: Boolean = false,
        private var notifyOfComments: Boolean = false,
        private var notifyOfCommentReplies: Boolean = false,
        private var notifyOfCreatorDigest: Boolean = false,
        private var notifyOfCreatorEdu: Boolean = false,
        private var notifyOfFollower: Boolean = false,
        private var notifyOfFriendActivity: Boolean = false,
        private var notifyOfMessages: Boolean = false,
        private var notifyOfUpdates: Boolean = false,
        private var optedOutOfRecommendations: Boolean = false,
        private var promoNewsletter: Boolean = false,
        private var publishingNewsletter: Boolean = false,
        private var showPublicProfile: Boolean = false,
        private var social: Boolean = false,
        private var starredProjectsCount: Int = 0,
        private var unreadMessagesCount: Int = 0,
        private var unseenActivityCount: Int = 0,
        private var weeklyNewsletter: Boolean = false
    ) : Parcelable {

        fun alumniNewsletter(alN: Boolean?) = apply { alN?.let { this.alumniNewsletter = it } }
        fun artsCultureNewsletter(arN: Boolean?) = apply { arN?.let { this.artsCultureNewsletter = it } }
        fun avatar(avatar: Avatar?) = apply { avatar?.let { this.avatar = it } }
        fun backedProjectsCount(bPC: Int?) = apply { bPC?.let { this.backedProjectsCount = it } }
        fun draftProjectsCount(dPC: Int?) = apply { dPC?.let { this.draftProjectsCount = it } }
        fun createdProjectsCount(cPC: Int?) = apply { cPC?.let { this.createdProjectsCount = it } }
        fun erroredBackingsCount(eBC: Int?) = apply { eBC?.let { this.erroredBackingsCount = it } }
        fun facebookConnected(facebookConnected: Boolean?) = apply { facebookConnected?.let { this.facebookConnected = it } }
        fun filmNewsletter(filmNewsletter: Boolean?) = apply { filmNewsletter?.let { this.filmNewsletter = it } }
        fun gamesNewsletter(gamesNewsletter: Boolean?) = apply { gamesNewsletter?.let { this.gamesNewsletter = it } }
        fun happeningNewsletter(happeningNewsletter: Boolean?) = apply { happeningNewsletter?.let { this.happeningNewsletter = it } }
        fun id(id:Long?) = apply { id?.let { this.id = it } }
        fun isAdmin(isAdmin: Boolean?) = apply { isAdmin?.let { this.isAdmin = it } }
        fun isEmailVerified(isEmailVerified: Boolean?) = apply { isEmailVerified?.let { this.isEmailVerified = it } }
        fun chosenCurrency(chosenCurrency: String?) = apply { chosenCurrency?.let { this.chosenCurrency = it } }
        fun inventNewsletter() = this.inventNewsletter
        fun location() = this.location
        fun memberProjectsCount() = this.memberProjectsCount
        fun musicNewsletter() = this.musicNewsletter
        fun name() = this.name
        fun notifyMobileOfBackings() = this.notifyMobileOfBackings
        fun notifyMobileOfComments() = this.notifyMobileOfComments
        fun notifyMobileOfCreatorEdu() = this.notifyMobileOfCreatorEdu
        fun notifyMobileOfFollower() = this.notifyMobileOfFollower
        fun notifyMobileOfFriendActivity() = this.notifyMobileOfFriendActivity
        fun notifyMobileOfMessages() = this.notifyMobileOfMessages
        fun notifyMobileOfPostLikes() = this.notifyMobileOfPostLikes
        fun notifyMobileOfUpdates() = this.notifyMobileOfUpdates
        fun notifyMobileOfMarketingUpdate() = this.notifyMobileOfMarketingUpdate
        fun notifyOfBackings() = this.notifyOfBackings
        fun notifyOfComments() = this.notifyOfComments
        fun notifyOfCommentReplies() = this.notifyOfCommentReplies
        fun notifyOfCreatorDigest() = this.notifyOfCreatorDigest
        fun notifyOfCreatorEdu() = this.notifyOfCreatorEdu
        fun notifyOfFollower() = this.notifyOfFollower
        fun notifyOfFriendActivity() = this.notifyOfFriendActivity
        fun notifyOfMessages() = this.notifyOfMessages
        fun notifyOfUpdates() = this.notifyOfUpdates
        fun optedOutOfRecommendations() = this.optedOutOfRecommendations
        fun promoNewsletter() = this.promoNewsletter
        fun publishingNewsletter() = this.publishingNewsletter
        fun showPublicProfile() = this.showPublicProfile
        fun social() = this.social
        fun starredProjectsCount() = this.starredProjectsCount
        fun unreadMessagesCount() = this.unreadMessagesCount
        fun unseenActivityCount() = this.unseenActivityCount
        fun weeklyNewsletter() = this.weeklyNewsletter
        fun build() = User()
    }

    fun param(): String {
        return id().toString()
    }

    companion object {
        @JvmStatic
        fun builder() = Builder()
    }

    fun toBuilder() = Builder()

    enum class EmailFrequency(private val stringResId: Int) {
        TWICE_A_DAY_SUMMARY(R.string.Twice_a_day_summary), DAILY_SUMMARY(R.string.Daily_summary);

        companion object {
            fun getStrings(resources: Resources): Array<String?> {
                val strings = arrayOfNulls<String>(values().size)
                val values = values()
                var i = 0
                val valuesLength = values.size
                while (i < valuesLength) {
                    val emailFrequency = values[i]
                    strings[i] = resources.getString(emailFrequency.stringResId)
                    i++
                }
                return strings
            }
        }
    }

    override fun equals(obj: Any?): Boolean {
        var equals = super.equals(obj)
        if (obj is User) {
            equals = id() == obj.id() &&
                    alumniNewsletter() == obj.alumniNewsletter() &&
                    artsCultureNewsletter() == obj.artsCultureNewsletter() &&
                    backedProjectsCount() == obj.backedProjectsCount() &&
                    createdProjectsCount() == obj.createdProjectsCount() &&
                    draftProjectsCount() == obj.draftProjectsCount() &&
                    name() == obj.name() &&
                    avatar() == obj.avatar() &&
                    createdProjectsCount() == obj.createdProjectsCount() &&
                    facebookConnected() == obj.facebookConnected() &&
                    filmNewsletter() == obj.filmNewsletter() &&
                    facebookConnected() == obj.facebookConnected() &&
                    gamesNewsletter() == obj.gamesNewsletter() &&
                    happeningNewsletter() == obj.happeningNewsletter() &&
                    inventNewsletter() == obj.inventNewsletter() &&
                    isAdmin == obj.isAdmin &&
                    location() == obj.location() &&
                    memberProjectsCount() == obj.memberProjectsCount() &&
                    musicNewsletter() == obj.musicNewsletter() &&
                    notifyMobileOfBackings() == obj.notifyMobileOfBackings() &&
                    notifyMobileOfComments() == obj.notifyMobileOfComments() &&
                    notifyMobileOfCreatorEdu() == obj.notifyMobileOfCreatorEdu() &&
                    notifyMobileOfFollower() == obj.notifyMobileOfFollower() &&
                    notifyMobileOfFriendActivity() == obj.notifyMobileOfFriendActivity() &&
                    notifyMobileOfMessages() == obj.notifyMobileOfMessages() &&
                    notifyMobileOfPostLikes() == obj.notifyMobileOfPostLikes() &&
                    notifyMobileOfUpdates() == obj.notifyMobileOfUpdates() &&
                    notifyMobileOfMarketingUpdate() == obj.notifyMobileOfMarketingUpdate() &&
                    notifyOfBackings() == obj.notifyOfBackings() &&
                    notifyOfComments() == obj.notifyOfComments() &&
                    notifyOfCommentReplies() == obj.notifyOfCommentReplies() &&
                    notifyOfCreatorDigest() == obj.notifyOfCreatorDigest() &&
                    notifyOfCreatorEdu() == obj.notifyOfCreatorEdu() &&
                    notifyOfFollower() == obj.notifyOfFollower() &&
                    notifyOfFriendActivity() == obj.notifyOfFriendActivity() &&
                    notifyOfMessages() == obj.notifyOfMessages() &&
                    optedOutOfRecommendations() == obj.optedOutOfRecommendations() &&
                    promoNewsletter() == obj.promoNewsletter() &&
                    publishingNewsletter() == obj.publishingNewsletter() &&
                    showPublicProfile() == obj.showPublicProfile() &&
                    social() == obj.social() &&
                    starredProjectsCount() == obj.starredProjectsCount() &&
                    unreadMessagesCount() == obj.unreadMessagesCount() &&
                    unseenActivityCount() == obj.unseenActivityCount() &&
                    weeklyNewsletter() == obj.weeklyNewsletter()
        }
        return equals
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }
}