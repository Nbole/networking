package com.example.networkbound

internal fun <I, O> NWResponse<I>.mapResponse(transformAction: (I) -> O) =
    when (this) {
        is NWResponse.Error -> {
            DomainResponse.Error(message)
        }
        is NWResponse.Loading -> {
            DomainResponse.Loading(data?.let(transformAction::invoke))
        }
        is NWResponse.Success -> {
            DomainResponse.Success(transformAction.invoke(data))
        }
    }
