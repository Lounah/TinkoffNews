package com.lounah.tinkoffnews.presentation.feed

import com.lounah.tinkoffnews.presentation.common.arch.BasePresenter
import javax.inject.Inject

class NewsFeedPresenter @Inject constructor() : BasePresenter<NewsFeedView>() {

    fun onCreate() {
        fetchNewsFeed()
    }

    fun fetchNewsFeed() {
        mvpView.showFullscreenError()
    }

    fun onRetryClicked() {

    }

    fun onPullToRefreshTriggered() {

    }
}