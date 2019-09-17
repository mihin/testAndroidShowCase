package com.example.manuel.baseproject.home.commons.datasource

import com.example.manuel.baseproject.home.commons.datasource.NetworkExceptions.apiErrorFromCodeException
import com.example.manuel.baseproject.home.commons.exceptions.BadRequestException
import com.example.manuel.baseproject.home.commons.exceptions.GenericNetworkException
import com.example.manuel.baseproject.home.commons.exceptions.NetworkConnectionException
import retrofit2.HttpException
import java.io.IOException
import java.net.UnknownHostException

fun Exception.handleNetworkException(): Exception {
    return when (this) {
        is IOException -> NetworkConnectionException()
        is UnknownHostException -> NetworkConnectionException()
        is HttpException -> apiErrorFromCodeException(this.code())
        else -> GenericNetworkException()
    }
}

private object NetworkExceptions {
    fun apiErrorFromCodeException(code: Int): Exception {
        return if (code == 400) {
            BadRequestException()
        } else {
            GenericNetworkException()
        }
    }
}