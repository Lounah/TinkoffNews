package com.lounah.tinkoffnews.presentation.newsdetails

import com.lounah.tinkoffnews.data.prefs.FontSharedPreferences
import com.lounah.tinkoffnews.domain.feed.NewsFeedInteractor
import dagger.Module
import dagger.Provides

@Module
class StoryDetailsModule {
    @Provides
    fun presenter(
        newsFeedInteractor: NewsFeedInteractor,
        fontSharedPreferences: FontSharedPreferences
    ): StoryDetailsPresenter = StoryDetailsPresenter(newsFeedInteractor, fontSharedPreferences)
}