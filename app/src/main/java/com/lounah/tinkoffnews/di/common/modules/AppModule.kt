package com.lounah.tinkoffnews.di.common.modules

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.lounah.tinkoffnews.data.prefs.FontSharedPreferences
import com.lounah.tinkoffnews.data.source.local.dao.storypreview.SelectStoryPreviewQueryEngine
import com.lounah.tinkoffnews.data.source.local.dao.storypreview.StoryPreviewsDao
import com.lounah.tinkoffnews.data.source.local.entity.StoryPreviewEntity
import com.lounah.tinkoffnews.data.source.local.sql.DatabaseHelper
import com.lounah.tinkoffnews.data.source.local.sql.queryengine.InsertQueryEngine
import com.lounah.tinkoffnews.data.source.local.sql.queryengine.SelectQueryEngine
import com.lounah.tinkoffnews.di.common.ApplicationContext
import com.lounah.tinkoffnews.domain.bookmarks.BookmarksInteractor
import com.lounah.tinkoffnews.domain.bookmarks.BookmarksRepository
import com.lounah.tinkoffnews.domain.feed.NewsFeedInteractor
import com.lounah.tinkoffnews.domain.feed.NewsFeedMapper
import com.lounah.tinkoffnews.domain.feed.NewsFeedRepository
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun newsFeedInteractor(newsFeedRepository: NewsFeedRepository, mapper: NewsFeedMapper): NewsFeedInteractor {
        return NewsFeedInteractor(newsFeedRepository, mapper)
    }

    @Provides
    @Singleton
    fun bookmarksInteractor(bookmarksRepository: BookmarksRepository, mapper: NewsFeedMapper): BookmarksInteractor {
        return BookmarksInteractor(bookmarksRepository, mapper)
    }

    @Provides
    @Singleton
    fun fontSharedPrefs(@ApplicationContext context: Context): FontSharedPreferences {
        return FontSharedPreferences(context)
    }

    @Provides
    @Singleton
    fun storyPreviewsDao(sqlLiteDb: SQLiteDatabase,
                         selectQueryEngine: SelectStoryPreviewQueryEngine,
                         insertQueryEngine: InsertQueryEngine): StoryPreviewsDao {
        return StoryPreviewsDao(sqlLiteDb, selectQueryEngine, insertQueryEngine)
    }

    @Provides
    @Singleton
    fun appDatabase(@ApplicationContext context: Context): SQLiteDatabase {
        return DatabaseHelper(context).writableDatabase
    }

    @Provides
    @Singleton
    fun selectQueryEngine(): SelectQueryEngine<StoryPreviewEntity> = SelectStoryPreviewQueryEngine()

    @Provides
    @Singleton
    fun insertQueryEngine(): InsertQueryEngine = InsertQueryEngine()
}