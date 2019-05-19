package com.lounah.tinkoffnews.presentation.feed

import com.lounah.tinkoffnews.domain.feed.NewsFeedInteractor
import com.lounah.tinkoffnews.presentation.common.arch.BasePresenter
import com.lounah.tinkoffnews.presentation.extensions.async
import com.lounah.tinkoffnews.presentation.feed.viewobject.StoryViewObject
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

    fun applyBookmarkClick(story: StoryViewObject) {
        if (story.isBookmarked) {
            commonDisposable.add(newsFeedInteractor.removeFromBookmarks(story.id)
                    .async()
                    .subscribe({
                        mvpView?.onItemBookmarkedStatusChanged(story.apply { isBookmarked = false })
                    }, {
                        Timber.e(it)
                    }))
        } else {
            commonDisposable.add(newsFeedInteractor.markAsBookmarked(story.id)
                    .async()
                    .subscribe({
                        mvpView?.onItemBookmarkedStatusChanged(story.apply { isBookmarked = true })
                    }, {
                        Timber.e(it)
                    }))
        }
    }

    fun onRetryClicked() {
        fetchNewsFeed(forceRefresh = false, initialLoading = true)
    }

    fun onPullToRefreshTriggered() {
        fetchNewsFeed(forceRefresh = false, initialLoading = false)
    }
}