package com.lounah.tinkoffnews.presentation.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.PrecomputedTextCompat
import androidx.core.widget.TextViewCompat
import com.lounah.tinkoffnews.R
import com.lounah.tinkoffnews.presentation.extensions.*
import com.lounah.tinkoffnews.presentation.feed.viewobject.StoryViewObject
import kotlinx.android.synthetic.main.view_story_preview.view.*

class StoryPreview : ViewGroup {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    interface OnBookmarkClickedCallback {
        fun onBookmarkClicked(story: StoryViewObject)
    }

    var onBookmarkClickedCallback: OnBookmarkClickedCallback? = null

    init {
        LayoutInflater.from(context).inflate(R.layout.view_story_preview, this, true)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthSize = View.MeasureSpec.getSize(widthMeasureSpec)

        val widthUsed = 0
        var heightUsed = 0

        measureChildWithMargins(textViewTitle,
                widthMeasureSpec, widthUsed,
                heightMeasureSpec, heightUsed)
        heightUsed += textViewTitle.getMeasuredHeightWithMargins()

        measureChildWithMargins(textViewDate,
                widthMeasureSpec, widthUsed,
                heightMeasureSpec, heightUsed)
        heightUsed += textViewDate.getMeasuredHeightWithMargins()

        measureChildWithMargins(buttonBookmark,
                widthMeasureSpec, widthUsed,
                heightMeasureSpec, heightUsed)
        heightUsed += 10.dpToPx()

        val heightSize = heightUsed + paddingTop + paddingBottom
        setMeasuredDimension(widthSize, heightSize)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        val paddingLeft = paddingLeft
        val paddingTop = paddingTop

        var currentTop = paddingTop

        textViewTitle.layoutView(paddingLeft, currentTop,
                textViewTitle.measuredWidth,
                textViewTitle.measuredHeight)

        currentTop += textViewTitle.getMeasuredHeightWithMargins()

        textViewDate.layoutView(paddingLeft, currentTop,
                textViewDate.getMeasuredWidthWithMargins(),
                textViewDate.getHeightWithMargins())

        buttonBookmark.layoutView(
                r - buttonBookmark.getMeasuredWidthWithMargins() - 16.dpToPx(),
                currentTop,
                buttonBookmark.getMeasuredWidthWithMargins(),
                buttonBookmark.getMeasuredHeightWithMargins()
        )
    }

    override fun generateLayoutParams(attrs: AttributeSet): ViewGroup.LayoutParams {
        return ViewGroup.MarginLayoutParams(context, attrs)
    }

    override fun generateDefaultLayoutParams(): ViewGroup.LayoutParams {
        return ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    fun update(obj: StoryViewObject?) {
        obj?.let {
            textViewTitle.setTextFuture(PrecomputedTextCompat.getTextFuture(obj.title.fromHtml(),
                    TextViewCompat.getTextMetricsParams(textViewTitle),
                    null))
            textViewDate.setTextFuture(PrecomputedTextCompat.getTextFuture(obj.date,
                    TextViewCompat.getTextMetricsParams(textViewDate),
                    null))
            if (obj.isBookmarked) {
                buttonBookmark.setImageDrawable(context.getDrawableCompat(R.drawable.ic_bookmark_filled))
            } else {
                buttonBookmark.setImageDrawable(context.getDrawableCompat(R.drawable.ic_bookmark_white))
            }
            buttonBookmark.setOnClickListener {
                buttonBookmark.apply {
                    if (obj.isBookmarked) {
                        buttonBookmark.setImageDrawable(context.getDrawableCompat(R.drawable.ic_bookmark_white))
                    } else {
                        buttonBookmark.setImageDrawable(context.getDrawableCompat(R.drawable.ic_bookmark_filled))
                    }

                    onBookmarkClickedCallback?.onBookmarkClicked(obj)
                }
            }
        }
    }

}