package com.lounah.tinkoffnews.domain.feed

import com.lounah.tinkoffnews.core.Mapper
import com.lounah.tinkoffnews.data.model.StoryPreview
import com.lounah.tinkoffnews.data.source.local.entity.StoryPreviewEntity
import com.lounah.tinkoffnews.presentation.feed.viewobject.StoryViewObject
import javax.inject.Inject

class NewsFeedMapper @Inject constructor() : Mapper<StoryPreviewEntity, StoryViewObject> {
    override fun map(from: StoryPreviewEntity): StoryViewObject {
        return StoryViewObject(from.id, from.text, from.getShortFormattedDate(), from.isBookmarked)
    }
}