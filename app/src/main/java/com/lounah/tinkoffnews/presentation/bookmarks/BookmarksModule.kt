package com.lounah.tinkoffnews.presentation.bookmarks

import com.lounah.tinkoffnews.domain.bookmarks.BookmarksInteractor
import dagger.Module
import dagger.Provides

@Module
class BookmarksModule {
    @Provides
    fun presenter(interactor: BookmarksInteractor): BookmarksFragmentPresenter = BookmarksFragmentPresenter(interactor)
}