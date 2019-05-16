package com.lounah.tinkoffnews.presentation.feed.list

import android.text.Html
import android.view.ViewGroup
import com.lounah.tinkoffnews.R
import com.lounah.tinkoffnews.presentation.common.recycler.BaseViewHolder
import com.lounah.tinkoffnews.presentation.extensions.fromHtml
import com.lounah.tinkoffnews.presentation.feed.viewobject.StoryViewObject
import kotlinx.android.synthetic.main.item_story_summary.*

class StorySummaryViewHolder(
    parent: ViewGroup,
    layoutRes: Int = R.layout.item_story_summary
) : BaseViewHolder<StoryViewObject>(parent, layoutRes) {

    override fun bind(obj: StoryViewObject?) {
        obj?.let {
            textViewItemStoryDate.text = obj.date
            textViewItemStoryTitle.text = obj.title.fromHtml()
        }
    }
}