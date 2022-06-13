package com.example.networkbound

import io.ktor.client.features.ClientRequestException
import java.io.IOException

inline fun <reified T> get(
    action: () -> T
): SerialResponse<T> {
    return try {
        SerialResponse.Success(action.invoke())
    } catch (e: ClientRequestException) {
        SerialResponse.Error(data = null, message = e.message)
    } catch (e: IOException) {
        SerialResponse.Error(data = null, message = e.message.orEmpty())
    }
}
