package com.kickstarter.ui.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isGone
import com.kickstarter.R
import com.kickstarter.databinding.MediaHeaderBinding
import com.kickstarter.libs.utils.ViewUtils.getScreenDensity
import com.kickstarter.libs.utils.ViewUtils.getScreenWidthDp
import com.kickstarter.libs.utils.extensions.photoHeightFromWidthRatio
import com.kickstarter.libs.utils.extensions.toVisibility
import com.kickstarter.ui.data.MediaElement
import com.squareup.picasso.Picasso
import rx.Observable
import rx.subjects.PublishSubject

class MediaHeader @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private var binding: MediaHeaderBinding =
        MediaHeaderBinding.inflate(LayoutInflater.from(context), this, true)
    private var playButtonClicks = PublishSubject.create<Void>()
    private var onFullScreenClicked = PublishSubject.create<Pair<String, Long>>()

    interface Inputs {
        /**
         * Sets the visibility of the play button
         */
        fun setProjectMedia(photo: MediaElement?)

        /**
         * Sets the visibility of the play button
         */
        fun setPlayButtonVisibility(isVisible: Boolean)

        /**
         * set media player video start
         */

        fun initializePlayer()

        /**
         * set media player video pause
         */

        fun pausePlayer()

        /**
         * set media player video release
         */
        fun releasePlayer()

        /**
         * set seek postion
         */
        fun setPlayerSeekPosition(seekPosition: Long)
    }

    interface Outputs {

        fun playButtonClicks(): Observable<Void>
        fun onFullScreenClicked(): Observable<Pair<String, Long>>
    }

    val inputs: Inputs = object : Inputs {

        override fun setPlayButtonVisibility(isVisible: Boolean) {
            binding.videoPlayButtonOverlay.visibility = (isVisible).toVisibility()
        }

        override fun setProjectMedia(element: MediaElement?) {
            val targetImageWidth =
                (getScreenWidthDp(context) * getScreenDensity(context)).toInt() - context.resources.getDimension(
                    R.dimen.grid_2
                ).toInt() * 2
            val targetImageHeight = photoHeightFromWidthRatio(targetImageWidth)

            binding.videoProjectPhoto.maxHeight =
                photoHeightFromWidthRatio(targetImageWidth)

            binding.videoProjectVideo.maxHeight =
                photoHeightFromWidthRatio(targetImageWidth)

            if (element?.videoModelElement?.sourceUrl?.isNotEmpty() == true) {
                binding.videoProjectVideo.setVideoModelElement(element.videoModelElement)
            }

            if (element?.thumbnailUrl != null) {
                ResourcesCompat.getDrawable(context.resources, R.drawable.gray_gradient, null)
                    ?.let {
                        Picasso.get()
                            .load(element.thumbnailUrl)
                            .resize(targetImageWidth, targetImageHeight)
                            .centerCrop()
                            .placeholder(it)
                            .into(binding.videoProjectPhoto)
                    }
            }
        }

        override fun initializePlayer() {
            binding.videoProjectVideo.initializePlayer()
        }

        override fun releasePlayer() {
            binding.videoProjectVideo.releasePlayer()
        }

        override fun setPlayerSeekPosition(seekPosition: Long) {
            binding.videoProjectVideo.setPlayerSeekPosition(seekPosition)
        }

        override fun pausePlayer() {
            binding.videoProjectVideo.pausePlayer()
        }
    }
    val outputs: Outputs = object : Outputs {
        override fun playButtonClicks(): Observable<Void> = playButtonClicks
        override fun onFullScreenClicked(): Observable<Pair<String, Long>> = onFullScreenClicked
    }

    init {
        binding.videoPlayButtonOverlay.setOnClickListener {
            binding.videoProjectPhoto.isGone = true
            binding.videoPlayButtonOverlay.isGone = true
        }

        binding.videoProjectVideo.setOnFullScreenClickedListener(object : OnFullScreenClickedListener {
            override fun onFullScreenOpenedViewClicked(
                view: View,
                url: String,
                seekPosition: Long
            ) {
                onFullScreenClicked.onNext(Pair(url, seekPosition))
            }
        })
    }
}
