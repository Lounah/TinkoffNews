package com.lounah.tinkoffnews.data.model

data class StoryDetails(
    val title: StoryPreview,
    val creationDate: ResponseDate,
    val lastModificationDate: ResponseDate,
    val content: String
)

data class ResponseDate(
    val milliseconds: Long
)