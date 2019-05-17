package com.lounah.tinkoffnews.data.source.remote

import com.lounah.tinkoffnews.data.model.ApiResponse
import com.lounah.tinkoffnews.data.model.StoryDetails
import com.lounah.tinkoffnews.data.model.StoryPreview
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    @GET("v1/news")
    fun fetchNews(): Single<ApiResponse<List<StoryPreview>>>

    @GET("v1/news_content/")
    fun fetchStoryById(@Query("id") id: Int): Single<ApiResponse<StoryDetails>>
}