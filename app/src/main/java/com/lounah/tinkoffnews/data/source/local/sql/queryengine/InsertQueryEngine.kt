package com.lounah.tinkoffnews.data.source.local.sql.queryengine

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import javax.inject.Inject


class InsertQueryEngine @Inject constructor() : SQLQueryEngine<Long> {
    override fun execute(query: String, db: SQLiteDatabase, tableName: String, contentValues: ContentValues, onConflictStrategy: Int): Long? {
        db.insertWithOnConflict(tableName, null,
                contentValues,
                onConflictStrategy
        )
        return 0L
    }

    override fun executeRawQuery(query: String, db: SQLiteDatabase, tableName: String, onConflictStrategy: Int): List<Long>? {
        return null
    }
}