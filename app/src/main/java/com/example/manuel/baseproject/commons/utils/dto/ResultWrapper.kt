package com.example.manuel.baseproject.commons.utils.dto

import com.example.manuel.baseproject.commons.utils.enums.ResultType

data class ResultWrapper<out T>(
        var resultType: ResultType,
        val data: T? = null,
        val message: String? = null
) {

    companion object {
        fun <T> success(data: T?): ResultWrapper<T> {
            return ResultWrapper(ResultType.SUCCESS, data)
        }

        fun <T> error(message: String): ResultWrapper<T> {
            return ResultWrapper(ResultType.ERROR,  message = message)
        }

        fun <T> loading(): ResultWrapper<T> {
            return ResultWrapper(ResultType.LOADING)
        }

        fun <T> emptyData(): ResultWrapper<T> {
            return ResultWrapper(ResultType.EMPTY_DATA)
        }
    }
}