package com.lounah.tinkoffnews.presentation.widget

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.annotation.DrawableRes
import com.lounah.tinkoffnews.R
import com.lounah.tinkoffnews.presentation.extensions.dpToPx
import com.lounah.tinkoffnews.presentation.extensions.hide
import com.lounah.tinkoffnews.presentation.extensions.show
import kotlinx.android.synthetic.main.view_simple_toolbar.view.*

const val TITLE_ANIMATION_DURATION_MS = 700L

class SimpleToolbar : FrameLayout {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        LayoutInflater.from(context).inflate(R.layout.view_simple_toolbar, this, true)
    }

    fun setTitle(title: String) {
        textViewTitle.text = title
        textViewTitle.animate().withLayer()
                .setDuration(TITLE_ANIMATION_DURATION_MS)
                .alpha(1f)
                .start()
    }

    fun setNavigationIcon(@DrawableRes src: Drawable, onClickListener: () -> (Unit)) {
        (textViewTitle.layoutParams as MarginLayoutParams).leftMargin = 64.dpToPx()
        buttonToolbarBack.apply {
            setImageDrawable(src)
            show()
            setOnClickListener {
                onClickListener.invoke()
            }
        }
    }

    fun setMenuIcon(@DrawableRes src: Drawable, onClickListener: () -> (Unit)) {
        buttonMenu.apply {
            show()
            setImageDrawable(src)
            setOnClickListener {
                onClickListener.invoke()
            }
        }
    }

    fun hideMenuIcon() {
        buttonMenu.hide()
    }

    fun hideNavigationIcon() {
        buttonToolbarBack.hide()
    }

    fun setShouldShowNavigationIcon(shouldShowNavigationIcon: Boolean) {

    }

    fun setShouldShowElevation(shouldShowElevation: Boolean) {
        if (shouldShowElevation) {
            viewElevation.show()
        } else {
            viewElevation.hide()
        }
    }
}