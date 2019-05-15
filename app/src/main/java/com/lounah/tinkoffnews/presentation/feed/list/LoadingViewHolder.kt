package com.lounah.tinkoffnews.presentation.feed.list

import android.view.ViewGroup
import com.lounah.tinkoffnews.R
import com.lounah.tinkoffnews.presentation.common.recycler.BaseViewHolder
import com.lounah.tinkoffnews.presentation.feed.viewobject.StoryViewObject

class LoadingViewHolder(parent: ViewGroup) : BaseViewHolder<StoryViewObject>(parent, R.layout.item_loading) {
    override fun bind(obj: StoryViewObject) {}
}