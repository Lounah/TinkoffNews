package com.lounah.tinkoffnews.presentation.feed

import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class NewsFeedFragmentModule {
    @Provides
    fun presenter(): NewsFeedPresenter = NewsFeedPresenter()
}