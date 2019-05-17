package com.lounah.tinkoffnews.data.source.remote.core

import timber.log.Timber
import java.io.InputStream
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL
import javax.inject.Inject

private const val REQUEST_METHOD_GET = "GET"

class StandartNetworkEngine @Inject constructor() : NetworkEngine {
    override fun get(url: String): NetworkEngine.Response {
        var inputStream: InputStream? = null
        val connection: HttpURLConnection

        try {
            val serverUrl = URL(url)
            connection = serverUrl.openConnection() as HttpURLConnection
            connection.apply {
                requestMethod = REQUEST_METHOD_GET
                connect()
                inputStream = getInputStream()
                inputStream?.let {
                    val response = it.readTextAndClose()
                    return NetworkEngine.Response.Data(response)
                }
            }
        } catch (error: Exception) {
            return NetworkEngine.Response.Error()
        } finally {
            try {
                inputStream?.let {
                    connection.disconnect()
                    it.close()
                }
            } catch (error: Exception) {
                Timber.e(error)
                return NetworkEngine.Response.Error()
            }
        }
        return NetworkEngine.Response.Error()
    }

    override fun post(url: String): NetworkEngine.Response {
        return NetworkEngine.Response.Error()
    }
}