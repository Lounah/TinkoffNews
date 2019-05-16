package com.lounah.tinkoffnews.di.app

import com.lounah.tinkoffnews.presentation.bookmarks.BookmarksFragment
import com.lounah.tinkoffnews.presentation.feed.NewsFeedFragment
import com.lounah.tinkoffnews.presentation.feed.NewsFeedFragmentModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface AppFragmentsInjectorBuilders {
    @ContributesAndroidInjector(modules = [NewsFeedFragmentModule::class])
    fun newsFeedFragment(): NewsFeedFragment

    @ContributesAndroidInjector()
    fun bookmarksFragment(): BookmarksFragment
}