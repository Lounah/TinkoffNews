package com.lounah.tinkoffnews.data.source.local.dao.storydetails

import android.database.Cursor
import com.lounah.tinkoffnews.data.source.local.entity.StoryDetailsEntity
import com.lounah.tinkoffnews.data.source.local.sql.queryengine.SelectQueryEngine
import javax.inject.Inject

class SelectStoryDetailsQueryEngine @Inject constructor() : SelectQueryEngine<StoryDetailsEntity>() {
    override fun buildEntityFromCursor(cursor: Cursor): StoryDetailsEntity {
        return StoryDetailsEntity(
                cursor.getInt(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getLong(3),
                cursor.getString(4),
                cursor.getInt(5) > 0
        )
    }
}