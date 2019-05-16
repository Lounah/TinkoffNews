package com.lounah.tinkoffnews.data.feed

import com.lounah.tinkoffnews.data.model.StoryDetails
import com.lounah.tinkoffnews.data.model.StoryPreview
import com.lounah.tinkoffnews.data.source.local.dao.storypreview.StoryPreviewsDao
import com.lounah.tinkoffnews.data.source.local.entity.StoryPreviewEntity
import com.lounah.tinkoffnews.data.source.remote.Api
import com.lounah.tinkoffnews.domain.feed.NewsFeedRepository
import com.lounah.tinkoffnews.presentation.extensions.async
import io.reactivex.Completable
import io.reactivex.Single
import java.util.*
import javax.inject.Inject

class FeedRepositoryImpl @Inject constructor(
    private val api: Api,
    private val storyPreviewsDao: StoryPreviewsDao
) : NewsFeedRepository {

    private companion object {
        private const val PAGING_PAGE_SIZE = 15
    }

    private val inMemoryCachedStoryPreviews = mutableListOf<StoryPreviewEntity>()

    private var page: Int = 0

    override fun fetchNewsFeed(forceRefresh: Boolean): Single<List<StoryPreviewEntity>> {
        return if (inMemoryCachedStoryPreviews.isNotEmpty()) {
            if (forceRefresh) {
                page = 1
                Single.just(inMemoryCachedStoryPreviews.take(PAGING_PAGE_SIZE))
            } else {
                val startIndex = 0
                val endIndex = (page + 1) * PAGING_PAGE_SIZE
                page++
                Single.just(inMemoryCachedStoryPreviews.subList(startIndex, endIndex).toList())
            }
        } else {
            return fetchNewsFeedFromApi()
                    .map { it.map { StoryPreviewEntity(it.id, it.name, it.text, isBookmarked = false, date = it.publicationDate.milliseconds) } }
                    .doOnSuccess { previews ->
                        storyPreviewsDao.addAll(previews.sortedBy { Date(it.date) })
                                .async()
                                .doOnSubscribe {
                                    inMemoryCachedStoryPreviews.addAll(previews.filter { storyPreviewEntity ->
                                        inMemoryCachedStoryPreviews.contains(storyPreviewEntity).not()
                                    })
                                }
                                .subscribe()
                    }
                    .map { it.subList(0, PAGING_PAGE_SIZE) }
                    .onErrorResumeNext {
                        storyPreviewsDao.getAll(PAGING_PAGE_SIZE, page++ * PAGING_PAGE_SIZE)
                    }
        }
    }

    override fun fetchStoryById(id: Int): Single<StoryDetails> = api.fetchStoryById(id).map { it.payload }

    override fun markAsBookmarked(storyId: Int): Completable {
        return Completable.fromAction {

        }
    }

    override fun removeFromBookmarks(storyId: Int): Completable {
        return Completable.fromAction {

        }
    }

    private fun fetchNewsFeedFromApi(): Single<List<StoryPreview>> = api.fetchNews().map { it.payload }
}