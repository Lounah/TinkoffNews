package com.lounah.tinkoffnews.data.bookmarks

import com.lounah.tinkoffnews.data.source.local.entity.StoryPreviewEntity
import com.lounah.tinkoffnews.domain.bookmarks.BookmarksRepository
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class BookmarksRepositoryImpl @Inject constructor() : BookmarksRepository {
    override fun fetchBookmarkedStories(): Single<List<StoryPreviewEntity>> {
        return Single.just(emptyList())
    }

    override fun removeFromBookmarks(storyId: Int): Completable {
        return Completable.fromAction {

        }
    }
}