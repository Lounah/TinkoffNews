package com.lounah.tinkoffnews.domain.feed

import com.lounah.tinkoffnews.data.model.StoryDetails
import com.lounah.tinkoffnews.data.model.StoryPreview
import com.lounah.tinkoffnews.data.source.local.entity.StoryDetailsEntity
import com.lounah.tinkoffnews.data.source.local.entity.StoryPreviewEntity
import io.reactivex.Completable
import io.reactivex.Single

interface NewsFeedRepository {
    fun fetchNewsFeed(forceRefresh: Boolean = false): Single<List<StoryPreviewEntity>>

    fun fetchStoryById(id: Int): Single<StoryDetailsEntity>

    fun markAsBookmarked(storyId: Int): Completable

    fun removeFromBookmarks(storyId: Int): Completable
}