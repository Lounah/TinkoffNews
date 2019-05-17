package com.lounah.tinkoffnews.data.source.remote

import com.google.gson.reflect.TypeToken
import com.lounah.tinkoffnews.BuildConfig
import com.lounah.tinkoffnews.data.model.ApiResponse
import com.lounah.tinkoffnews.data.model.StoryDetails
import com.lounah.tinkoffnews.data.model.StoryPreview
import com.lounah.tinkoffnews.data.source.remote.core.NetworkEngine
import io.reactivex.Single
import com.google.gson.GsonBuilder
import javax.inject.Inject


class NewsApiImpl @Inject constructor(
    private val networkEngine: NetworkEngine
) : NewsApi {

    val FETCH_NEWS_URL = BuildConfig.BASE_URL + "v1/news"
    val FETCH_STORY_DETAILS_BY_ID_URL = BuildConfig.BASE_URL + "v1/news_content"

    override fun fetchNews(): Single<ApiResponse<List<StoryPreview>>> {
        return Single.create { source ->
            val response = networkEngine.get(FETCH_NEWS_URL)
            if (response is NetworkEngine.Response.Error) {
                if (source.isDisposed.not()) source.onError(Throwable())
            } else {
                val jsonResponse = (response as NetworkEngine.Response.Data).data

                jsonResponse?.let {
                    val typeToken = object : TypeToken<ApiResponse<List<StoryPreview>>>() {}
                    val parsedResponse = parseApiResponse(it, typeToken)
                    source.onSuccess(parsedResponse)
                }
            }
        }
    }

    override fun fetchStoryById(id: Int): Single<ApiResponse<StoryDetails>> {
        return Single.create { source ->
            val response = networkEngine.get("$FETCH_STORY_DETAILS_BY_ID_URL?id=$id")
            if (response is NetworkEngine.Response.Error) {
                if (source.isDisposed.not()) source.onError(Throwable())
            } else {

                val jsonResponse = (response as NetworkEngine.Response.Data).data

                jsonResponse?.let {
                    val typeToken = object : TypeToken<ApiResponse<StoryDetails>>() {}
                    val parsedResponse = parseApiResponse(it, typeToken)
                    source.onSuccess(parsedResponse)
                }
            }
        }
    }

    private fun <T> parseApiResponse(json: String, typeToken: TypeToken<T>): T {
        return GsonBuilder()
                .create()
                .fromJson(json, typeToken.type)
    }
}