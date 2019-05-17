package com.lounah.tinkoffnews.data.source.remote.core

import java.io.InputStream
import java.nio.charset.Charset

interface NetworkEngine {
    sealed class Response(val data: String? = null) {
        class Error(message: String? = null) : Response(message)
        class Data(response: String) : Response(response)
    }

    fun get(url: String): Response

    fun post(url: String): Response
}

fun InputStream.readTextAndClose(charset: Charset = Charsets.UTF_8): String {
    return this.bufferedReader(charset).use { it.readText() }
}