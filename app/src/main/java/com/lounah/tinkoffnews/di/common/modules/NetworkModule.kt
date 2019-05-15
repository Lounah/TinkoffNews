package com.lounah.tinkoffnews.di.common.modules

import dagger.Module

@Module
class NetworkModule {

//    private val BASE_API_URL = "http://95.213.236.54:3000/"
//
//    @Singleton
//    @Provides
//    fun provideHttpClient() = OkHttpClient.Builder()
//        .readTimeout(30, TimeUnit.SECONDS)
//        .build()
//
//    @Singleton
//    @Provides
//    fun provideGson() = GsonBuilder().create()
//
//    @Singleton
//    @Provides
//    fun provideRetrofit(httpClient: OkHttpClient, gson: Gson) = Retrofit.Builder()
//        .baseUrl(BASE_API_URL)
//        .client(httpClient)
//        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//        .addConverterFactory(GsonConverterFactory.create(gson))
//        .build()
//
//    @Singleton
//    @Provides
//    fun provideApi(retrofit: Retrofit) = retrofit.create(DocsApi::class.java)

}