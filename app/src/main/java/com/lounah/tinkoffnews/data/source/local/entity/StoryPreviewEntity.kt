package com.lounah.tinkoffnews.data.source.local.entity

import java.text.SimpleDateFormat
import java.util.*

data class StoryPreviewEntity(
    val id: Int,
    val name: String,
    val text: String,
    val isBookmarked: Boolean,
    val date: Long
) {
    fun getShortFormattedDate(): String {
        return SimpleDateFormat("dd MMMM yyyy", Locale.US).format(Date(date))
    }
}