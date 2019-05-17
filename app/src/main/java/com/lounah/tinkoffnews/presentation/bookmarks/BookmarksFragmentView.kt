package com.lounah.tinkoffnews.presentation.bookmarks

import com.lounah.tinkoffnews.presentation.common.arch.MvpView
import com.lounah.tinkoffnews.presentation.feed.viewobject.StoryViewObject

interface BookmarksFragmentView : MvpView {
    fun showFullscreenLoading()
    fun showBookmarkedNews(news: List<StoryViewObject>)
    fun onStoryRemovedFromBookmarks(story: StoryViewObject)
}