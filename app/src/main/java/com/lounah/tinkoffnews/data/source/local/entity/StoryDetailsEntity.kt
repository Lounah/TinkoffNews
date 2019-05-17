package com.lounah.tinkoffnews.data.source.local.entity

import java.text.SimpleDateFormat
import java.util.*

data class StoryDetailsEntity(
    val id: Int,
    val title: String,
    val name: String,
    val date: Long,
    val content: String,
    val isBookmarked: Boolean
) {
    fun getShortFormattedDate(): String {
        return SimpleDateFormat("dd MMMM yyyy", Locale.US).format(Date(date))
    }
}