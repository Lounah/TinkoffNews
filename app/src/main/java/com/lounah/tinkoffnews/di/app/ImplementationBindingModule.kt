package com.lounah.tinkoffnews.di.app

import com.lounah.tinkoffnews.data.feed.FeedRepositoryImpl
import com.lounah.tinkoffnews.domain.feed.NewsFeedRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class ImplementationBindingModule {
    @Singleton
    @Binds
    abstract fun bindNewsFeedRepository(impl: FeedRepositoryImpl): NewsFeedRepository
}