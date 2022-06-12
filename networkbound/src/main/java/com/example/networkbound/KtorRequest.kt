package com.example.networkbound

import io.ktor.client.features.ClientRequestException
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.url
import java.io.IOException

suspend inline fun <reified T> get(
    baseUrl: String,
    endPoint: String,
    apiKey: String? = null
): SerialResponse<T> {
    return try {
        SerialResponse.Success(
            if (apiKey != null) {
                KtorClient.httpClient.get {
                    url(baseUrl + endPoint)
                    parameter("api_key", apiKey)

                }
            } else {
                KtorClient.httpClient.get {
                    url(baseUrl + endPoint)
                }
            }
        )
    } catch (e: ClientRequestException) {
        SerialResponse.Error(data = null, message = e.message)
    } catch (e: IOException) {
        SerialResponse.Error(data = null, message = e.message.orEmpty())
    }
}