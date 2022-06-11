package com.example.networkbound

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

inline fun <ResultType, RequestType> networkBoundResource(
    crossinline loadFromDb: () -> Flow<ResultType>,
    crossinline netWorkRequest: suspend () -> SerialResponse<RequestType>,
    crossinline saveCall: suspend (SerialResponse<RequestType>) -> Unit
): Flow<NWResponse<ResultType>> = flow {
    emit(NWResponse.Loading(loadFromDb().firstOrNull()))
    val netWorkSerialResponse: SerialResponse<RequestType> = netWorkRequest()
    emitAll(
        if (netWorkSerialResponse is SerialResponse.Success) {
            saveCall(netWorkSerialResponse)
            loadFromDb().map { NWResponse.Success(it) }
        } else {
            val error = netWorkSerialResponse as SerialResponse.Error
            loadFromDb().map { NWResponse.Error(error.message, it) }
        }
    )
}
