package com.lounah.tinkoffnews.presentation.feed

import com.lounah.tinkoffnews.domain.feed.NewsFeedInteractor
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class NewsFeedFragmentModule {
    @Provides
    fun presenter(newsFeedInteractor: NewsFeedInteractor): NewsFeedPresenter = NewsFeedPresenter(newsFeedInteractor)
}