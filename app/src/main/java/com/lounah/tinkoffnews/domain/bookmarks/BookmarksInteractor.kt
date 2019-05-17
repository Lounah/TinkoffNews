package com.lounah.tinkoffnews.domain.bookmarks

import com.lounah.tinkoffnews.domain.feed.NewsFeedMapper
import com.lounah.tinkoffnews.presentation.feed.viewobject.StoryViewObject
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class BookmarksInteractor @Inject constructor(
    private val bookmarksRepository: BookmarksRepository,
    private val mapper: NewsFeedMapper
) {
    fun fetchBookmarkedStories(): Single<List<StoryViewObject>> = bookmarksRepository.fetchBookmarkedStories()
        .map { mapper.map(it) }

    fun removeStoryFromBookmarks(storyId: Int): Completable = bookmarksRepository.removeFromBookmarks(storyId)
}