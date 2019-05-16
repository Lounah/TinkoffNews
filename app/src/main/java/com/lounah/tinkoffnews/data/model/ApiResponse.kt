package com.lounah.tinkoffnews.data.model

data class ApiResponse<T>(
    val resultCode: String,
    val payload: T,
    val trackingId: String
)