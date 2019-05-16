package com.lounah.tinkoffnews.presentation.feed

import com.lounah.tinkoffnews.domain.feed.NewsFeedInteractor
import com.lounah.tinkoffnews.presentation.common.arch.BasePresenter
import com.lounah.tinkoffnews.presentation.extensions.async
import timber.log.Timber
import javax.inject.Inject

class NewsFeedPresenter @Inject constructor(
    private var newsFeedInteractor: NewsFeedInteractor
) : BasePresenter<NewsFeedView>() {

    fun onCreate() {
        fetchNewsFeed(forceRefresh = true)
    }

    fun fetchNewsFeed(forceRefresh: Boolean) {
        commonDisposable.add(newsFeedInteractor.fetchNewsFeed(forceRefresh)
                .async()
                .doOnSubscribe {
                    if (forceRefresh) {
                        mvpView.showFullscreenLoading()
                    } else {
                        mvpView.showPagingLoading()
                    }
                }
                .doFinally {
                    mvpView.hideSwipeRefresh()
                }.subscribe({
                    mvpView.showData(it.take(20))

                }, {
                    Timber.e(it)
                    if (forceRefresh) {
                        mvpView.showFullscreenError()
                    } else {
                        mvpView.showErrorToast()
                    }
                }))
    }

    fun onRetryClicked() {
        fetchNewsFeed(forceRefresh = true)
    }

    fun onPullToRefreshTriggered() {
        fetchNewsFeed(forceRefresh = false)
    }
}