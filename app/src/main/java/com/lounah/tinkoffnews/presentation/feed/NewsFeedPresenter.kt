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
        fetchNewsFeed(forceRefresh = false, initialLoading = true)
    }

    fun fetchNewsFeed(forceRefresh: Boolean, initialLoading: Boolean) {
        commonDisposable.add(newsFeedInteractor.fetchNewsFeed(forceRefresh)
                .async()
                .doOnSubscribe {
                    if (initialLoading) {
                        mvpView?.showFullscreenLoading()
                    } else {
                        mvpView?.showPagingLoading()
                    }
                }
                .doFinally {
                    mvpView?.hideSwipeRefresh()
                }.subscribe({
                    if (it.isEmpty()) {
                        mvpView?.showFullscreenError()
                    } else
                        mvpView?.showData(it)

                }, {
                    Timber.e(it)
                    if (initialLoading) {
                        mvpView?.showFullscreenError()
                    } else {
                        mvpView?.showErrorToast()
                    }
                }))
    }

    fun onRetryClicked() {
        fetchNewsFeed(forceRefresh = true, initialLoading = true)
    }

    fun onPullToRefreshTriggered() {
        fetchNewsFeed(forceRefresh = false, initialLoading = false)
    }
}