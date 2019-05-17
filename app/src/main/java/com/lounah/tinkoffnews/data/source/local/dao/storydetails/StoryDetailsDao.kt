package com.lounah.tinkoffnews.data.source.local.dao.storydetails

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import com.lounah.tinkoffnews.data.source.local.dao.BaseDao
import com.lounah.tinkoffnews.data.source.local.entity.StoryDetailsEntity
import com.lounah.tinkoffnews.data.source.local.sql.DatabaseContract
import com.lounah.tinkoffnews.data.source.local.sql.queryengine.InsertQueryEngine
import com.lounah.tinkoffnews.data.source.local.sql.queryengine.SelectQueryEngine
import io.reactivex.Completable
import io.reactivex.Single
import timber.log.Timber
import javax.inject.Inject

class StoryDetailsDao @Inject constructor(
    private val sqLiteDatabase: SQLiteDatabase,
    private val selectQueryEngine: SelectQueryEngine<StoryDetailsEntity>,
    private val insertQueryEngine: InsertQueryEngine
): BaseDao<StoryDetailsEntity> {

    override fun getAll(): Single<List<StoryDetailsEntity>> {
        return Single.error(Throwable("Not supported"))
    }

    override fun add(item: StoryDetailsEntity): Completable {
        val contentValues = ContentValues().apply {
            put(DatabaseContract.StoryDetailsTable.COLUMN_NAME_ID, item.id)
            put(DatabaseContract.StoryDetailsTable.COLUMN_NAME_TITLE, item.title)
            put(DatabaseContract.StoryDetailsTable.COLUMN_NAME_TEXT, item.name)
            put(DatabaseContract.StoryDetailsTable.COLUMN_NAME_CONTENT, item.date)
            put(DatabaseContract.StoryDetailsTable.COLUMN_NAME_NAME, item.content)
            put(DatabaseContract.StoryDetailsTable.COLUMN_NAME_IS_BOOKMARKED, item.isBookmarked)
            put(DatabaseContract.StoryDetailsTable.COLUMN_NAME_DATE, item.date)
        }

        return Completable.fromAction {
            insertQueryEngine.execute(
                    "",
                    sqLiteDatabase,
                    DatabaseContract.StoryDetailsTable.TABLE_NAME,
                    contentValues,
                    SQLiteDatabase.CONFLICT_REPLACE)
        }
    }

    override fun addAll(items: List<StoryDetailsEntity>): Completable {
        return Completable.error(Throwable("Now supported"))
    }

    fun getById(id: Int): Single<StoryDetailsEntity> {
        return Single.create { emitter ->
            val SELECT_QUERY = "SELECT * FROM ${DatabaseContract.StoryDetailsTable.TABLE_NAME} WHERE ${DatabaseContract.StoryDetailsTable.COLUMN_NAME_ID}=$id"
            val result = selectQueryEngine.executeRawQuery(SELECT_QUERY, sqLiteDatabase, DatabaseContract.StoryDetailsTable.TABLE_NAME,  0)

            if (result.isNotEmpty()) {
                emitter.onSuccess(result[0])
            } else {
                emitter.onError(Throwable("Empty result set"))
            }
        }
    }

    fun markAsBookmarked(itemId: Int): Completable {
        return Completable.fromAction {
            val contentValues = ContentValues().apply {
                put(DatabaseContract.StoryDetailsTable.COLUMN_NAME_IS_BOOKMARKED, true)
            }
            sqLiteDatabase.update(DatabaseContract.StoryDetailsTable.TABLE_NAME, contentValues, "id=$itemId", null)
        }
    }

    fun removeFromBookmarks(itemId: Int): Completable {
        return Completable.fromAction {
            val contentValues = ContentValues().apply {
                put(DatabaseContract.StoryDetailsTable.COLUMN_NAME_IS_BOOKMARKED, false)
            }
            sqLiteDatabase.update(DatabaseContract.StoryDetailsTable.TABLE_NAME, contentValues, "id=$itemId", null)
        }
    }
}