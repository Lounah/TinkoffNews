package com.lounah.tinkoffnews.di.common.modules

import com.lounah.tinkoffnews.core.Mapper
import com.lounah.tinkoffnews.data.source.local.entity.StoryPreviewEntity
import com.lounah.tinkoffnews.domain.feed.NewsFeedMapper
import com.lounah.tinkoffnews.presentation.feed.viewobject.StoryViewObject
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class MappersModule {
    @Provides
    @Singleton
    fun previewToViewObject(): Mapper<StoryPreviewEntity, StoryViewObject> {
        return NewsFeedMapper()
    }
}