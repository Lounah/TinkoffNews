package com.lounah.tinkoffnews.data.bookmarks

import com.lounah.tinkoffnews.data.model.StoryPreview
import com.lounah.tinkoffnews.domain.bookmarks.BookmarksRepository
import com.lounah.tinkoffnews.presentation.feed.viewobject.StoryViewObject
import io.reactivex.Completable
import io.reactivex.Single

class BookmarksRepositoryImpl : BookmarksRepository {
    override fun fetchBookmarkedStories(): Single<List<StoryPreview>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun removeFromBookmarks(storyId: Int): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}