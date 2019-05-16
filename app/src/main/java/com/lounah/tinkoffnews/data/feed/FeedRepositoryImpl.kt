package com.lounah.tinkoffnews.data.feed

import com.lounah.tinkoffnews.data.model.StoryDetails
import com.lounah.tinkoffnews.data.model.StoryPreview
import com.lounah.tinkoffnews.data.source.local.dao.storypreview.StoryPreviewsDao
import com.lounah.tinkoffnews.data.source.local.entity.StoryPreviewEntity
import com.lounah.tinkoffnews.data.source.remote.Api
import com.lounah.tinkoffnews.domain.feed.NewsFeedRepository
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import timber.log.Timber
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
        Timber.i("fetch news force = %s, page = %s, empty = %s", forceRefresh, page, inMemoryCachedStoryPreviews.isEmpty())
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
                    .doOnSuccess {
                        inMemoryCachedStoryPreviews.addAll(it.filter { storyPreviewEntity ->
                            inMemoryCachedStoryPreviews.contains(storyPreviewEntity).not()
                        })
                        storyPreviewsDao.addAll(it.sortedBy { Date(it.date) }).subscribe()
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

    private fun fetchNewsFromDb(): Single<List<StoryPreviewEntity>> = storyPreviewsDao.getAll()
}