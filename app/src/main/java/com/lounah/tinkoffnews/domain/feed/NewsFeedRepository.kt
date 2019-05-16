package com.lounah.tinkoffnews.domain.feed

import com.lounah.tinkoffnews.data.model.StoryDetails
import com.lounah.tinkoffnews.data.model.StoryPreview
import io.reactivex.Completable
import io.reactivex.Single

interface NewsFeedRepository {
    fun fetchNewsFeed(forceRefresh: Boolean = false): Single<List<StoryPreview>>

    fun fetchStoryById(id: Int): Single<StoryDetails>

    fun markAsBookmarked(storyId: Int): Completable

    fun removeFromBookmarks(storyId: Int): Completable
}