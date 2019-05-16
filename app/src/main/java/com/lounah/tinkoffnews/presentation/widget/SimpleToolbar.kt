package com.lounah.tinkoffnews.presentation.widget

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.annotation.DrawableRes
import com.lounah.tinkoffnews.R
import com.lounah.tinkoffnews.presentation.extensions.hide
import com.lounah.tinkoffnews.presentation.extensions.show
import kotlinx.android.synthetic.main.view_simple_toolbar.view.*

class SimpleToolbar : FrameLayout {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        LayoutInflater.from(context).inflate(R.layout.view_simple_toolbar, this, true)
    }

    fun setTitle(title: String) {
        textViewTitle.text = title
    }

    fun setNavigationIcon(@DrawableRes src: Drawable, onClickListener: () -> (Unit)) {

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