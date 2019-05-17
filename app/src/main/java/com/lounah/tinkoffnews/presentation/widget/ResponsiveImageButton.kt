package com.lounah.tinkoffnews.presentation.widget

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatImageButton

/**
 *  An [AppCompatImageButton], which reacts on user touch events
 *  If u put a finger on it, it will decrease self scale
 */
class ResponsiveImageButton : AppCompatImageButton {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        setOnTouchListener { _, event ->
            when (event.actionMasked) {
                MotionEvent.ACTION_DOWN -> {
                    animate()
                            .scaleX(0.7f)
                            .scaleY(0.7f)
                            .start()
                }
                MotionEvent.ACTION_UP -> {
                    releaseScale()
                }
                MotionEvent.ACTION_OUTSIDE -> {
                    releaseScale()
                }
                MotionEvent.ACTION_MOVE -> {
                    releaseScale()
                }
            }
            return@setOnTouchListener false
        }
    }

    override fun performClick(): Boolean {
        animate()
                .scaleX(0.7f)
                .scaleY(0.7f)
                .start()

        releaseScale()
        return super.performClick()
    }

    override fun onWindowVisibilityChanged(visibility: Int) {
        super.onWindowVisibilityChanged(visibility)
        scaleX = 1f
        scaleY = 1f
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        scaleX = 1f
        scaleY = 1f
    }

    private fun releaseScale() {
        animate()
                .scaleX(1f)
                .scaleY(1f)
                .start()
    }
}