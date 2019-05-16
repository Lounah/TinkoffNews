package com.lounah.tinkoffnews.data.source.local.sql

import android.provider.BaseColumns


object DatabaseContract {
    abstract class StoryPreviewEntityTable : BaseColumns {
        companion object {
            const val TABLE_NAME = "story_previews"
            const val COLUMN_NAME_ID = "id"
            const val COLUMN_NAME_NAME = "name"
            const val COLUMN_NAME_TEXT = "text"
            const val COLUMN_NAME_IS_BOOKMARKED = "isBookmarked"
            const val COLUMN_NAME_DATE = "date"

            const val CREATE_DATABASE = (
                    "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
                            + StoryPreviewEntityTable.COLUMN_NAME_ID + " INTEGER PRIMARY KEY, "
                            + StoryPreviewEntityTable.COLUMN_NAME_NAME + " TEXT,"
                            + StoryPreviewEntityTable.COLUMN_NAME_TEXT + " TEXT,"
                            + StoryPreviewEntityTable.COLUMN_NAME_IS_BOOKMARKED + " BOOLEAN,"
                            + StoryPreviewEntityTable.COLUMN_NAME_DATE + " LONG );")

            const val DELETE_DATABASE = "DROP TABLE IF EXIST " + StoryPreviewEntityTable.TABLE_NAME
        }
    }

    abstract class StoryDetailsTable : BaseColumns {
        companion object {
            const val TABLE_NAME = "story_details"
            const val COLUMN_NAME_ID = "id"
            const val COLUMN_NAME_TITLE = "title"
            const val COLUMN_NAME_TEXT = "text"
            const val COLUMN_NAME_CONTENT = "content"
            const val COLUMN_NAME_NAME = "name"
            const val COLUMN_NAME_IS_BOOKMARKED = "isBookmarked"
            const val COLUMN_NAME_DATE = "date"

            const val CREATE_DATABASE = (
                    "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
                            + StoryDetailsTable.COLUMN_NAME_ID + " INTEGER PRIMARY KEY, "
                            + StoryDetailsTable.COLUMN_NAME_NAME + " TEXT,"
                            + StoryDetailsTable.COLUMN_NAME_TEXT + " TEXT,"
                            + StoryDetailsTable.COLUMN_NAME_TITLE + " TEXT,"
                            + StoryDetailsTable.COLUMN_NAME_CONTENT + " TEXT,"
                            + StoryDetailsTable.COLUMN_NAME_IS_BOOKMARKED + " BOOLEAN,"
                            + StoryDetailsTable.COLUMN_NAME_DATE + " TEXT );")

            const val DELETE_DATABASE = "DROP TABLE IF EXIST " + StoryDetailsTable.TABLE_NAME
        }
    }

}