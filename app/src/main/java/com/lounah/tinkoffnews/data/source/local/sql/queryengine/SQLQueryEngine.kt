package com.lounah.tinkoffnews.data.source.local.sql.queryengine

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase


interface SQLQueryEngine<T> {
    fun execute(
        query: String,
        db: SQLiteDatabase,
        tableName: String,
        contentValues: ContentValues,
        onConflictStrategy: Int
    ): T?

    fun executeRawQuery(
        query: String,
        db: SQLiteDatabase,
        tableName: String,
        onConflictStrategy: Int
    ): List<T>?
}