package com.lounah.tinkoffnews.data

import com.lounah.tinkoffnews.data.feed.FeedRepositoryImpl
import com.lounah.tinkoffnews.data.model.ApiResponse
import com.lounah.tinkoffnews.data.model.ResponseDate
import com.lounah.tinkoffnews.data.model.StoryDetails
import com.lounah.tinkoffnews.data.model.StoryPreview
import com.lounah.tinkoffnews.data.source.local.dao.storydetails.StoryDetailsDao
import com.lounah.tinkoffnews.data.source.local.dao.storypreview.StoryPreviewsDao
import com.lounah.tinkoffnews.data.source.local.entity.StoryDetailsEntity
import com.lounah.tinkoffnews.data.source.local.entity.StoryPreviewEntity
import com.lounah.tinkoffnews.data.source.remote.Api
import com.lounah.tinkoffnews.domain.feed.NewsFeedRepository
import com.lounah.tinkoffnews.presentation.TrampolineSchedulerRule
import io.reactivex.Completable
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import java.lang.Exception

import org.mockito.Mockito.`when` as whenever

private const val PAGING_PAGE_SIZE = 15

@RunWith(MockitoJUnitRunner::class)
class FeedRepositoryTest {

    @get:Rule
    val rxSchedulerRule = TrampolineSchedulerRule()

    @Mock
    lateinit var api: Api

    @Mock
    lateinit var storyPreviewsDao: StoryPreviewsDao

    @Mock
    lateinit var storyDetailsDao: StoryDetailsDao

    private lateinit var repository: NewsFeedRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        repository = FeedRepositoryImpl(api, storyPreviewsDao, storyDetailsDao)
    }

    @Test
    fun fetches_feed_from_api_first_if_db_is_empty() {
        whenever(storyPreviewsDao.getAll(PAGING_PAGE_SIZE, 0)).thenReturn(Single.error(Exception())) // empty result
        whenever(api.fetchNews()).thenReturn(Single.just(ApiResponse("result", emptyList(), "")))

        repository.fetchNewsFeed(false)
            .test()

        verify(storyPreviewsDao).getAll(PAGING_PAGE_SIZE, 0)
        verify(api).fetchNews()
        verifyNoMoreInteractions(api)
        verify(storyPreviewsDao).addAll(emptyList())
        verifyNoMoreInteractions(storyPreviewsDao)
    }

    @Test
    fun fetches_feed_db_if_data_is_present() {
        whenever(storyPreviewsDao.getAll(PAGING_PAGE_SIZE, 0))
            .thenReturn(Single.just(mutableListOf(StoryPreviewEntity(1, "", "", true, 1L))))

        repository.fetchNewsFeed(false)
            .test()

        verify(storyPreviewsDao).getAll(PAGING_PAGE_SIZE, 0)
        verifyZeroInteractions(api)
        verifyNoMoreInteractions(storyPreviewsDao)
    }

    @Test
    fun adds_to_bookmarks_both_preview_and_details() {
        whenever(storyPreviewsDao.markAsBookmarked(1)).thenReturn(Completable.complete())
        whenever(storyDetailsDao.markAsBookmarked(1)).thenReturn(Completable.complete())

        repository.markAsBookmarked(1)
            .test()

        verify(storyPreviewsDao).markAsBookmarked(1)
        verifyZeroInteractions(storyPreviewsDao)
        verify(storyDetailsDao).markAsBookmarked(1)
        verifyZeroInteractions(storyDetailsDao)
    }

    @Test
    fun removes_from_bookmarks_both_preview_and_details() {
        whenever(storyPreviewsDao.removeFromBookmarks(1)).thenReturn(Completable.complete())
        whenever(storyDetailsDao.removeFromBookmarks(1)).thenReturn(Completable.complete())

        repository.removeFromBookmarks(1)
            .test()

        verify(storyPreviewsDao).removeFromBookmarks(1)
        verifyZeroInteractions(storyPreviewsDao)
        verify(storyDetailsDao).removeFromBookmarks(1)
        verifyZeroInteractions(storyDetailsDao)
    }

    @Test
    fun fetches_story_details_from_api_first_if_db_is_empty() {
        whenever(storyDetailsDao.getById(1)).thenReturn(Single.error(Exception())) // empty result
        whenever(api.fetchStoryById(1))
            .thenReturn(
                Single.just(
                    ApiResponse(
                        "result",
                        StoryDetails(
                            StoryPreview(1, "name", "text", ResponseDate(1L), 1),
                            ResponseDate(1L),
                            ResponseDate(1L),
                            ""
                        ),
                        ""
                    )
                )
            )

        repository.fetchStoryById(1)
            .test()

        verify(storyDetailsDao).getById(1)
        verify(api).fetchStoryById(1)
        verifyNoMoreInteractions(api)
    }

    @Test
    fun fetches_story_details_from_db_if_data_is_present() {
        whenever(storyDetailsDao.getById(1)).thenReturn(
            Single.just(
                StoryDetailsEntity(
                    1,
                    "title",
                    "name",
                    1L,
                    "content",
                    false
                )
            )
        )

        repository.fetchStoryById(1)
            .test()

        verify(storyDetailsDao).getById(1)
        verifyZeroInteractions(api)
    }
}