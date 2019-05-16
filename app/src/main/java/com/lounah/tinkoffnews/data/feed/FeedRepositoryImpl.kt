package com.lounah.tinkoffnews.data.feed

import com.lounah.tinkoffnews.data.model.StoryPreview
import com.lounah.tinkoffnews.data.source.remote.Api
import com.lounah.tinkoffnews.domain.feed.NewsFeedRepository
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class FeedRepositoryImpl @Inject constructor(
    private val api: Api
) : NewsFeedRepository {
    override fun fetchNewsFeed(forceRefresh: Boolean): Single<List<StoryPreview>> = api.fetchNews().map { it.payload }

    override fun markAsBookmarked(storyId: Int): Completable {
        return Completable.fromAction {

        }
    }

    override fun removeFromBookmarks(storyId: Int): Completable {
        return Completable.fromAction {

        }
    }
}