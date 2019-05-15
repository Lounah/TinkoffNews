package com.lounah.tinkoffnews.presentation.feed

import com.lounah.tinkoffnews.presentation.common.arch.MvpView
import com.lounah.tinkoffnews.presentation.feed.viewobject.StoryViewObject

interface NewsFeedView : MvpView {
    fun showErrorToast(msg: String)
    fun showFullscreenError()
    fun showFullscreenLoading()
    fun showPagingLoading()
    fun hideSwipeRefresh()
    fun showData(feed: List<StoryViewObject>)
}