package com.lounah.tinkoffnews.presentation.main

import dagger.Module
import dagger.Provides

@Module
class MainModule {
    @Provides
    fun presenter(): MainActivityPresenter = MainActivityPresenter()

    @Provides
    fun navigationItemsHelper(): NavigationItemsHelper = NavigationItemsHelper()
}