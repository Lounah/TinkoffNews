package com.lounah.tinkoffnews.di.common

import com.lounah.tinkoffnews.di.app.AppFragmentsInjectorBuilders
import com.lounah.tinkoffnews.presentation.main.MainActivity
import com.lounah.tinkoffnews.presentation.main.MainModule
import com.lounah.tinkoffnews.presentation.newsdetails.StoryDetailsActivity
import com.lounah.tinkoffnews.presentation.newsdetails.StoryDetailsModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface ActivitiesBuildersModule {
    @ContributesAndroidInjector(modules = [
        AppFragmentsInjectorBuilders::class,
        MainModule::class]
    )
    fun provideMainActivity(): MainActivity

    @ContributesAndroidInjector(modules = [AppFragmentsInjectorBuilders::class, StoryDetailsModule::class])
    fun provideStoryDetailsActivity(): StoryDetailsActivity
}