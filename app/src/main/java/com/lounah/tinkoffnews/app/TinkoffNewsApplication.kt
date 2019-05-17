package com.lounah.tinkoffnews.app

import android.app.Activity
import android.app.Application
import androidx.multidex.MultiDexApplication
import com.lounah.tinkoffnews.di.common.AppInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import timber.log.Timber
import javax.inject.Inject

class TinkoffNewsApplication : MultiDexApplication(), HasActivityInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()
        setUpTimber()
        AppInjector.init(this)
    }

    override fun activityInjector() = dispatchingAndroidInjector

    private fun setUpTimber() {
        Timber.plant(Timber.DebugTree())
    }
}