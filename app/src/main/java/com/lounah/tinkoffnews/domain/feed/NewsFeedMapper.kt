package com.lounah.tinkoffnews.domain.feed

import com.lounah.tinkoffnews.core.Mapper
import com.lounah.tinkoffnews.data.model.StoryPreview
import com.lounah.tinkoffnews.presentation.feed.viewobject.StoryViewObject
import javax.inject.Inject

class NewsFeedMapper @Inject constructor() : Mapper<StoryPreview, StoryViewObject> {
    override fun map(from: StoryPreview): StoryViewObject {
        return StoryViewObject(from.id, from.text, from.getShortFormattedDate())
    }
}