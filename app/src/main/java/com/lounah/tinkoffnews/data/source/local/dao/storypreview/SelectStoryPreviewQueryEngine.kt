package com.lounah.tinkoffnews.data.source.local.dao.storypreview

import android.database.Cursor
import com.lounah.tinkoffnews.data.source.local.entity.StoryPreviewEntity
import com.lounah.tinkoffnews.data.source.local.sql.queryengine.SelectQueryEngine
import javax.inject.Inject

class SelectStoryPreviewQueryEngine @Inject constructor() : SelectQueryEngine<StoryPreviewEntity>() {
    override fun buildEntityFromCursor(cursor: Cursor): StoryPreviewEntity {
        return StoryPreviewEntity(
                cursor.getInt(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getInt(3) > 0,
                cursor.getLong(4)
        )
    }
}