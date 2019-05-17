package com.lounah.tinkoffnews.presentation.bookmarks

import com.lounah.tinkoffnews.domain.bookmarks.BookmarksInteractor
import com.lounah.tinkoffnews.presentation.TrampolineSchedulerRule
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.Mockito.`when` as whenever

@RunWith(MockitoJUnitRunner::class)
class BookmarksPresenterTest {

    @get:Rule
    val rxSchedulerRule = TrampolineSchedulerRule()

    @Mock lateinit var mvpView: BookmarksFragmentView

    @Mock lateinit var interactor: BookmarksInteractor

    private lateinit var presenter: BookmarksFragmentPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = BookmarksFragmentPresenter(interactor)
    }

    @Test
    fun fetches_bookmarked_on_start() {
        whenever(interactor.fetchBookmarkedStories()).thenReturn(Single.just(emptyList()))

        presenter.attachView(mvpView)

        verify(interactor).fetchBookmarkedStories()
        verifyNoMoreInteractions(interactor)
        verify(mvpView).showFullscreenLoading()
        verify(mvpView).showBookmarkedNews(emptyList())
        verifyNoMoreInteractions(mvpView)
    }
}