package com.lounah.tinkoffnews.presentation.feed.list

import android.view.ViewGroup
import androidx.core.text.PrecomputedTextCompat
import androidx.core.widget.TextViewCompat
import com.lounah.tinkoffnews.R
import com.lounah.tinkoffnews.presentation.common.recycler.BaseViewHolder
import com.lounah.tinkoffnews.presentation.extensions.fromHtml
import com.lounah.tinkoffnews.presentation.extensions.getDrawableCompat
import com.lounah.tinkoffnews.presentation.feed.viewobject.StoryViewObject
import kotlinx.android.synthetic.main.item_story_summary.*

class StorySummaryViewHolder(
    val parent: ViewGroup,
    val onBookMarkClicked: OnBookmarkClickedCallback,
    layoutRes: Int = R.layout.item_story_summary
) : BaseViewHolder<StoryViewObject>(parent, layoutRes) {

    interface OnBookmarkClickedCallback {
        fun onBookmarkClicked(story: StoryViewObject)
    }

    override fun bind(obj: StoryViewObject?) {
        obj?.let {
            textViewItemStoryTitle.setTextFuture(PrecomputedTextCompat.getTextFuture(obj.title.fromHtml(),
                    TextViewCompat.getTextMetricsParams(textViewItemStoryTitle),
                    null))
            textViewItemStoryDate.setTextFuture(PrecomputedTextCompat.getTextFuture(obj.date,
                    TextViewCompat.getTextMetricsParams(textViewItemStoryDate),
                    null))
            if (obj.isBookmarked) {
                buttonStorySummaryAddToBookmarks.setImageDrawable(parent.context.getDrawableCompat(R.drawable.ic_bookmark_filled))
            } else {
                buttonStorySummaryAddToBookmarks.setImageDrawable(parent.context.getDrawableCompat(R.drawable.ic_bookmark_white))
            }
            buttonStorySummaryAddToBookmarks.setOnClickListener {
                buttonStorySummaryAddToBookmarks.apply {
                    if (obj.isBookmarked) {
                        buttonStorySummaryAddToBookmarks.setImageDrawable(context.getDrawableCompat(R.drawable.ic_bookmark_white))
                    } else {
                        buttonStorySummaryAddToBookmarks.setImageDrawable(context.getDrawableCompat(R.drawable.ic_bookmark_filled))
                    }

                    onBookMarkClicked.onBookmarkClicked(obj)
                }
            }
        }
    }
}