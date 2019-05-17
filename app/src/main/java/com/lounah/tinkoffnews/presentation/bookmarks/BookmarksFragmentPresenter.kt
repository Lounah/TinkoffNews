package com.lounah.tinkoffnews.presentation.bookmarks

import com.lounah.tinkoffnews.domain.bookmarks.BookmarksInteractor
import com.lounah.tinkoffnews.presentation.common.arch.BasePresenter
import com.lounah.tinkoffnews.presentation.extensions.async
import com.lounah.tinkoffnews.presentation.feed.viewobject.StoryViewObject
import timber.log.Timber
import javax.inject.Inject

class BookmarksFragmentPresenter @Inject constructor(
    private val bookmarksInteractor: BookmarksInteractor
) : BasePresenter<BookmarksFragmentView>() {

    override fun attachView(mvpView: BookmarksFragmentView) {
        super.attachView(mvpView)
        fetchBookmarkedNews()
    }

    fun removeStoryFromBookmarks(story: StoryViewObject) {
        commonDisposable.add(bookmarksInteractor.removeStoryFromBookmarks(story.id)
                .async()
                .subscribe({
                    mvpView?.onStoryRemovedFromBookmarks(story)
                }, {
                    Timber.e(it)
                }))
    }

    private fun fetchBookmarkedNews() {
        commonDisposable.add(bookmarksInteractor.fetchBookmarkedStories()
                .async()
                .doOnSubscribe { mvpView?.showFullscreenLoading() }
                .subscribe({
                    mvpView?.showBookmarkedNews(it)
                }, {
                    Timber.e(it)
                }))
    }
}