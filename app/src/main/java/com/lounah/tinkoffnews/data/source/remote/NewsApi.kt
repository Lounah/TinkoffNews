package com.lounah.tinkoffnews.data.source.remote

import com.lounah.tinkoffnews.data.model.ApiResponse
import com.lounah.tinkoffnews.data.model.StoryDetails
import com.lounah.tinkoffnews.data.model.StoryPreview
import io.reactivex.Single

interface NewsApi {
    fun fetchNews(): Single<ApiResponse<List<StoryPreview>>>
    fun fetchStoryById(id: Int): Single<ApiResponse<StoryDetails>>
}