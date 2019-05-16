package com.lounah.tinkoffnews.di.common.modules

import android.content.Context
import com.lounah.tinkoffnews.domain.feed.NewsFeedInteractor
import com.lounah.tinkoffnews.domain.feed.NewsFeedMapper
import com.lounah.tinkoffnews.domain.feed.NewsFeedRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun newsFeedInteractor(newsFeedRepository: NewsFeedRepository, mapper: NewsFeedMapper): NewsFeedInteractor {
        return NewsFeedInteractor(newsFeedRepository, mapper)
    }
//
//    @Provides
//    @Singleton
//    fun permissionsRequesterDelegate(): PermissionsRequesterDelegate {
//        return PermissionsRequesterDelegate()
//    }
//
//    @Provides
//    @Singleton
//    fun galleryImagesProvider(@ApplicationContext context: Context, imageCompressor: ImageCompressorDelegate): GalleryProvider {
//        return GalleryProvider(context, imageCompressor)
//    }
//
//    @Provides
//    @Singleton
//    fun imageCompressorDelegate(@ApplicationContext context: Context, filesTypeHelper: FilesTypeHelper): ImageCompressorDelegate {
//        return ImageCompressorDelegate(filesTypeHelper, context)
//    }
//
//    @Provides
//    @Singleton
//    fun connectionCheckerDelegate(@ApplicationContext context: Context): ConnectionCheckerDelegate {
//        return ConnectionCheckerDelegate(context)
//    }
}