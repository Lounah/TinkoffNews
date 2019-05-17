package com.lounah.tinkoffnews.data.model

import com.google.gson.annotations.SerializedName

data class StoryPreview(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("text") val text: String,
    @SerializedName("publicationDate") val publicationDate: ResponseDate,
    @SerializedName("bankInfoTypeId") val bankInfoTypeId: Int
)