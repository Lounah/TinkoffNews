package com.lounah.tinkoffnews.domain.bookmarks

import com.lounah.tinkoffnews.data.model.StoryPreview
import com.lounah.tinkoffnews.data.source.local.entity.StoryPreviewEntity
import com.lounah.tinkoffnews.presentation.feed.viewobject.StoryViewObject
import io.reactivex.Completable
import io.reactivex.Single

interface BookmarksRepository {
    fun fetchBookmarkedStories(): Single<List<StoryPreviewEntity>>
    fun removeFromBookmarks(storyId: Int): Completable
}