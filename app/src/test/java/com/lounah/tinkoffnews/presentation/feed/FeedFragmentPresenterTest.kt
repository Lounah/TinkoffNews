package com.lounah.tinkoffnews.presentation.feed

import com.lounah.tinkoffnews.domain.feed.NewsFeedInteractor
import com.lounah.tinkoffnews.presentation.TrampolineSchedulerRule
import com.lounah.tinkoffnews.presentation.feed.viewobject.StoryViewObject
import io.reactivex.Completable
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.Mockito.`when` as whenever

@RunWith(MockitoJUnitRunner::class)
class FeedFragmentPresenterTest {

    @get:Rule val rxScheduler = TrampolineSchedulerRule()

    @Mock lateinit var mvpView: NewsFeedView

    @Mock lateinit var interactor: NewsFeedInteractor

    private lateinit var presenter: NewsFeedPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = NewsFeedPresenter(interactor)
        presenter.attachView(mvpView)
    }

    @Test
    fun fetches_news_on_start() {
        whenever(interactor.fetchNewsFeed(false)).thenReturn(Single.just(emptyList()))

        presenter.onCreate()

        verify(mvpView).showFullscreenLoading()
        verify(mvpView).showFullscreenError() // shows error on empty list
        verify(mvpView).hideSwipeRefresh()
        verifyNoMoreInteractions(mvpView)
    }

    @Test
    fun adds_post_to_bookmarks_if_its_not_there() {
        val mockStory = StoryViewObject(1, "title", "date", false)
        whenever(interactor.markAsBookmarked(ArgumentMatchers.anyInt())).thenReturn(Completable.complete())

        presenter.applyBookmarkClick(mockStory)

        verify(interactor).markAsBookmarked(mockStory.id)
        verifyNoMoreInteractions(interactor)
        verify(mvpView).onItemBookmarkedStatusChanged(mockStory)
        verifyNoMoreInteractions(mvpView)
    }

    @Test
    fun removes_post_from_bookmarks_if_its_there() {
        val mockStory = StoryViewObject(1, "title", "date", true)
        whenever(interactor.removeFromBookmarks(ArgumentMatchers.anyInt())).thenReturn(Completable.complete())

        presenter.applyBookmarkClick(mockStory)

        verify(interactor).removeFromBookmarks(mockStory.id)
        verifyNoMoreInteractions(interactor)
        verify(mvpView).onItemBookmarkedStatusChanged(mockStory)
        verifyNoMoreInteractions(mvpView)
    }

    @Test
    fun shows_fullscreen_error_when_error_occurred() {
        whenever(interactor.fetchNewsFeed(ArgumentMatchers.anyBoolean())).thenReturn(Single.error(Exception()))

        presenter.fetchNewsFeed(true, true)

        verify(interactor).fetchNewsFeed(true)
        verifyNoMoreInteractions(interactor)
        verify(mvpView).showFullscreenLoading()
        verify(mvpView).showFullscreenError()
        verify(mvpView).hideSwipeRefresh()
        verifyNoMoreInteractions(mvpView)
    }

}
