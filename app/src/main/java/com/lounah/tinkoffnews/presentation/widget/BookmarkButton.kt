package com.lounah.tinkoffnews.presentation.widget

import android.content.Context
import android.util.AttributeSet
import com.lounah.tinkoffnews.R
import com.lounah.tinkoffnews.presentation.extensions.getDrawableCompat

class BookmarkButton : ResponsiveImageButton {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        isHapticFeedbackEnabled = true
        applyBaseState(false)
    }

    fun applyBaseState(enableVibration: Boolean) {
        if (enableVibration)
            performHapticFeedback(16) // HapticFeedbackConstants.CONFIRM
        setImageDrawable(context.getDrawableCompat(R.drawable.ic_bookmark_white))
    }

    fun applyBookmarkedState(enableVibration: Boolean) {
        if (enableVibration)
            performHapticFeedback(16) // HapticFeedbackConstants.CONFIRM
        setImageDrawable(context.getDrawableCompat(R.drawable.ic_bookmark_filled))
    }
}