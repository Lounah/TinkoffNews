package com.lounah.tinkoffnews.di.app

import com.lounah.tinkoffnews.presentation.bookmarks.BookmarksFragment
import com.lounah.tinkoffnews.presentation.bookmarks.BookmarksModule
import com.lounah.tinkoffnews.presentation.feed.NewsFeedFragment
import com.lounah.tinkoffnews.presentation.feed.NewsFeedFragmentModule
import com.lounah.tinkoffnews.presentation.settings.AdvancedOptionsFragment
import com.lounah.tinkoffnews.presentation.settings.AdvancedOptionsFragmentModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface AppFragmentsInjectorBuilders {
    @ContributesAndroidInjector(modules = [NewsFeedFragmentModule::class])
    fun newsFeedFragment(): NewsFeedFragment

    @ContributesAndroidInjector(modules = [BookmarksModule::class])
    fun bookmarksFragment(): BookmarksFragment

    @ContributesAndroidInjector(modules = [AdvancedOptionsFragmentModule::class])
    fun settingsFragment(): AdvancedOptionsFragment
}