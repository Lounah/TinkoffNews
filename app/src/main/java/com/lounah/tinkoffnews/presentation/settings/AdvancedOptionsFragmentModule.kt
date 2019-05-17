package com.lounah.tinkoffnews.presentation.settings

import dagger.Module
import dagger.Provides

@Module
class AdvancedOptionsFragmentModule {
    @Provides
    fun presenter(): AdvancedOptionsFragmentPresenter = AdvancedOptionsFragmentPresenter()
}