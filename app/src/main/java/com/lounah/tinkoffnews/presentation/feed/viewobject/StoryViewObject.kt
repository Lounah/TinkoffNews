package com.lounah.tinkoffnews.presentation.feed.viewobject

data class StoryViewObject(
    val id: Int,
    val title: String,
    val date: String,
    var isBookmarked: Boolean = false
)