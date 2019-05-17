package com.lounah.tinkoffnews.data.source.local.sql

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.lounah.tinkoffnews.BuildConfig
import com.lounah.tinkoffnews.di.common.ApplicationContext
import javax.inject.Inject


class DatabaseHelper @Inject constructor(@ApplicationContext context: Context)
    : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(DatabaseContract.StoryDetailsTable.CREATE_DATABASE)
        db.execSQL(DatabaseContract.StoryPreviewEntityTable.CREATE_DATABASE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(DatabaseContract.StoryDetailsTable.DELETE_DATABASE)
        db.execSQL(DatabaseContract.StoryPreviewEntityTable.DELETE_DATABASE)
    }

    companion object {
        private val DATABASE_NAME = BuildConfig.DB_NAME
        private val DATABASE_VERSION = 1
    }
}