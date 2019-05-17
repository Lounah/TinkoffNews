package com.lounah.tinkoffnews.di.common

import android.app.Application
import android.content.Context
import com.lounah.tinkoffnews.app.TinkoffNewsApplication
import com.lounah.tinkoffnews.di.app.ImplementationBindingModule
import com.lounah.tinkoffnews.di.common.modules.AppModule
import com.lounah.tinkoffnews.di.common.modules.MappersModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
        modules = [
            AndroidInjectionModule::class,
            ActivitiesBuildersModule::class,
            AppModule::class,
            MappersModule::class,
            ImplementationBindingModule::class
        ]
)
interface AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        @BindsInstance
        fun appContext(@ApplicationContext context: Context): Builder

        fun build(): AppComponent
    }

    fun inject(app: TinkoffNewsApplication)
}