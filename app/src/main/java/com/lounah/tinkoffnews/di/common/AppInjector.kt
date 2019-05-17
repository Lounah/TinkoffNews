package com.lounah.tinkoffnews.di.common

import com.lounah.tinkoffnews.app.TinkoffNewsApplication

object AppInjector {
    fun init(app: TinkoffNewsApplication) {
        DaggerAppComponent
                .builder()
                .application(app)
                .appContext(app)
                .build()
                .inject(app)
    }
}