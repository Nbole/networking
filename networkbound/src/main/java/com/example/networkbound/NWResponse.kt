package com.example.networkbound

sealed class NWResponse<T> {
    data class Success<T>(val data: T) : NWResponse<T>()
    data class Loading<T>(val data: T? = null) : NWResponse<T>()
    data class Error<T>(val message: String, val data: T? = null) : NWResponse<T>()
}
