package com.lounah.tinkoffnews.presentation.feed.list

import android.view.ViewGroup
import androidx.core.text.PrecomputedTextCompat
import androidx.core.widget.TextViewCompat
import com.lounah.tinkoffnews.R
import com.lounah.tinkoffnews.presentation.common.recycler.BaseViewHolder
import com.lounah.tinkoffnews.presentation.extensions.fromHtml
import com.lounah.tinkoffnews.presentation.extensions.getDrawableCompat
import com.lounah.tinkoffnews.presentation.feed.viewobject.StoryViewObject
import com.lounah.tinkoffnews.presentation.widget.StoryPreview
import kotlinx.android.synthetic.main.item_story_summary.*

class StorySummaryViewHolder(
    parent: ViewGroup,
    val onBookMarkClicked: StoryPreview.OnBookmarkClickedCallback,
    layoutRes: Int = R.layout.item_story_summary
) : BaseViewHolder<StoryViewObject>(parent, layoutRes) {


    override fun bind(obj: StoryViewObject?) {
        viewStoryPreview.apply {
            update(obj)
            onBookmarkClickedCallback = onBookMarkClicked
        }
    }
}