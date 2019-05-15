package com.lounah.tinkoffnews.di.common

import com.lounah.tinkoffnews.di.app.AppFragmentsInjectorBuilders
import com.lounah.tinkoffnews.presentation.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface ActivitiesBuildersModule {
    @ContributesAndroidInjector(modules = [
        AppFragmentsInjectorBuilders::class]
    )
    fun provideMainActivity(): MainActivity
}