package com.lounah.tinkoffnews.presentation.feed.list

import android.text.Html
import android.view.ViewGroup
import com.lounah.tinkoffnews.R
import com.lounah.tinkoffnews.presentation.common.recycler.BaseViewHolder
import com.lounah.tinkoffnews.presentation.extensions.fromHtml
import com.lounah.tinkoffnews.presentation.extensions.getDrawableCompat
import com.lounah.tinkoffnews.presentation.feed.viewobject.StoryViewObject
import kotlinx.android.synthetic.main.item_story_summary.*

class StorySummaryViewHolder(
    val parent: ViewGroup,
    layoutRes: Int = R.layout.item_story_summary,
    val onBookMarkClicked: OnBookmarkClickedCallback
) : BaseViewHolder<StoryViewObject>(parent, layoutRes) {

    interface OnBookmarkClickedCallback {
        fun onBookmarkClicked(storyId: Int)
    }

    override fun bind(obj: StoryViewObject?) {
        obj?.let {
            textViewItemStoryDate.text = obj.date
            textViewItemStoryTitle.text = obj.title.fromHtml()
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

                    onBookMarkClicked.onBookmarkClicked(obj.id)
                }
            }
        }
    }
}