package com.lounah.tinkoffnews.di.app

import com.lounah.tinkoffnews.data.bookmarks.BookmarksRepositoryImpl
import com.lounah.tinkoffnews.data.feed.FeedRepositoryImpl
import com.lounah.tinkoffnews.data.source.remote.NewsApi
import com.lounah.tinkoffnews.data.source.remote.NewsApiImpl
import com.lounah.tinkoffnews.data.source.remote.core.NetworkEngine
import com.lounah.tinkoffnews.data.source.remote.core.StandartNetworkEngine
import com.lounah.tinkoffnews.domain.bookmarks.BookmarksRepository
import com.lounah.tinkoffnews.domain.feed.NewsFeedRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class ImplementationBindingModule {
    @Singleton
    @Binds
    abstract fun bindNewsFeedRepository(impl: FeedRepositoryImpl): NewsFeedRepository

    @Singleton
    @Binds
    abstract fun bookmarksRepository(impl: BookmarksRepositoryImpl): BookmarksRepository

    @Singleton
    @Binds
    abstract fun networkEngine(impl: StandartNetworkEngine): NetworkEngine

    @Singleton
    @Binds
    abstract fun newsApi(impl: NewsApiImpl): NewsApi
}