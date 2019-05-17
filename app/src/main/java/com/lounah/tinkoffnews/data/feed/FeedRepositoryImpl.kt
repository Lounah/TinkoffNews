package com.lounah.tinkoffnews.data.feed

import com.lounah.tinkoffnews.data.model.StoryDetails
import com.lounah.tinkoffnews.data.model.StoryPreview
import com.lounah.tinkoffnews.data.source.local.dao.storydetails.StoryDetailsDao
import com.lounah.tinkoffnews.data.source.local.dao.storypreview.StoryPreviewsDao
import com.lounah.tinkoffnews.data.source.local.entity.StoryDetailsEntity
import com.lounah.tinkoffnews.data.source.local.entity.StoryPreviewEntity
import com.lounah.tinkoffnews.data.source.remote.Api
import com.lounah.tinkoffnews.domain.feed.NewsFeedRepository
import com.lounah.tinkoffnews.presentation.extensions.async
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import timber.log.Timber
import java.util.*
import javax.inject.Inject

class FeedRepositoryImpl @Inject constructor(
    private val api: Api,
    private val storyPreviewsDao: StoryPreviewsDao,
    private val storyDetailsDao: StoryDetailsDao
) : NewsFeedRepository {

    private companion object {
        private const val PAGING_PAGE_SIZE = 15
    }

    private var page: Int = 0

    override fun fetchNewsFeed(forceRefresh: Boolean): Single<List<StoryPreviewEntity>> {
        return when {
            forceRefresh -> {
                page = 1
                storyPreviewsDao.getAll(PAGING_PAGE_SIZE, PAGING_PAGE_SIZE)
            }
            page != 0 -> {
                val limit = (page + 1) * PAGING_PAGE_SIZE
                page++
                storyPreviewsDao.getAll(limit, offset = 0)
            }
            else -> storyPreviewsDao.getAll(PAGING_PAGE_SIZE, 0)
                    .doOnSuccess { page++ }
                    // can throw empty result set exception
                    .onErrorResumeNext {
                        fetchNewsFeedFromApi()
                                .map { it.map { StoryPreviewEntity(it.id, it.name, it.text, isBookmarked = false, date = it.publicationDate.milliseconds) } }
                                .doOnSuccess { previews ->
                                    storyPreviewsDao.addAll(previews.sortedBy { Date(it.date) })
                                            .async()
                                            .subscribe()
                                    page++
                                }
                                .map { it.subList(0, PAGING_PAGE_SIZE) }
                    }
        }
    }

    override fun fetchStoryById(id: Int): Single<StoryDetailsEntity> = storyDetailsDao.getById(id)
            .onErrorResumeNext {
                Single.zip(api.fetchStoryById(id).map { it.payload }, storyPreviewsDao.getById(id),
                        BiFunction { storyDetails: StoryDetails, storyPreview: StoryPreviewEntity ->
                            Pair(storyDetails, storyPreview.isBookmarked)
                })
                        .map {
                            StoryDetailsEntity(
                                    it.first.title.id,
                                    it.first.title.text,
                                    it.first.title.name,
                                    it.first.title.publicationDate.milliseconds,
                                    it.first.content,
                                    it.second
                            )
                        }
                        .doOnSuccess {
                            storyDetailsDao.add(it)
                                    .async()
                                    .subscribe()
                        }
            }

    override fun markAsBookmarked(storyId: Int): Completable {
        return storyPreviewsDao.markAsBookmarked(storyId).andThen(storyDetailsDao.markAsBookmarked(storyId))
    }

    override fun removeFromBookmarks(storyId: Int): Completable {
        return storyPreviewsDao.removeFromBookmarks(storyId).andThen(storyDetailsDao.removeFromBookmarks(storyId))
    }

    private fun fetchNewsFeedFromApi(): Single<List<StoryPreview>> = api.fetchNews().map { it.payload }
}