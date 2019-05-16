package com.lounah.tinkoffnews.data.model

import java.text.SimpleDateFormat
import java.util.*

data class StoryDetails(
    val title: StoryPreview,
    val creationDate: ResponseDate,
    val lastModificationDate: ResponseDate,
    val content: String
) {
    fun getShortFormattedDate(): String {
        return SimpleDateFormat("dd MMMM yyyy", Locale.getDefault()).format(Date(creationDate.milliseconds))
    }
}

data class ResponseDate(
    val milliseconds: Long
)