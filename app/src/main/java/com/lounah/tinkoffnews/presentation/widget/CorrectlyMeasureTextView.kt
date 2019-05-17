package com.lounah.tinkoffnews.presentation.widget

import android.content.Context
import android.text.Layout
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

/**
 *  This one supposed to be a TextView for a story content, but recently i've decided to use WebView for
 *  this purpose. So [CorrectlyMeasureTextView] is unused.
 */
class CorrectlyMeasureTextView : AppCompatTextView {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val layout = layout
        if (layout != null) {
            val maxLineWidth = getMaxLineWidth(layout)
            var width = (Math.ceil(maxLineWidth.toDouble()).toInt() + compoundPaddingLeft + compoundPaddingRight)
            width = Math.min(width, measuredWidth)

            setMeasuredDimension(width, measuredHeight)
        }
    }

    private fun getMaxLineWidth(layout: Layout): Float {
        var maxWidth = 0.0f
        val lines = layout.lineCount
        for (i in 0 until lines) {
            if (layout.getLineWidth(i) > maxWidth) {
                maxWidth = layout.getLineWidth(i)
            }
        }

        return maxWidth
    }
}