package com.lounah.tinkoffnews.domain.feed

import com.lounah.tinkoffnews.presentation.feed.viewobject.StoryViewObject
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class NewsFeedInteractor @Inject constructor(
    private val newsFeedRepository: NewsFeedRepository,
    private val newsFeedMapper: NewsFeedMapper
) {

    fun fetchNewsFeed(forceRefresh: Boolean = false): Single<List<StoryViewObject>> =
            newsFeedRepository.fetchNewsFeed(forceRefresh)
                    .map { newsFeedMapper.map(it) }

    fun markAsBookmarked(storyId: Int): Completable = newsFeedRepository.markAsBookmarked(storyId)

    fun removeFromBookmarks(storyId: Int): Completable = newsFeedRepository.removeFromBookmarks(storyId)
}