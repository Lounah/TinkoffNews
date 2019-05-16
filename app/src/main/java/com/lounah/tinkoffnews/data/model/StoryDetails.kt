package com.lounah.tinkoffnews.data.model

import java.text.SimpleDateFormat
import java.util.*

data class StoryDetails(
    val title: StoryPreview,
    val creationDate: ResponseDate,
    val lastModificationDate: ResponseDate,
    val content: String
) {
    private val dateFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())

    fun getShortFormattedDate(): String {
        return dateFormat.format(Date(creationDate.milliseconds))
    }
}

data class ResponseDate(
    val milliseconds: Long
)