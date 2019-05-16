package com.lounah.tinkoffnews.presentation.bookmarks

import dagger.Module
import dagger.Provides

@Module
class BookmarksModule {
    @Provides
    fun presenter(): BookmarksFragmentPresenter = BookmarksFragmentPresenter()
}