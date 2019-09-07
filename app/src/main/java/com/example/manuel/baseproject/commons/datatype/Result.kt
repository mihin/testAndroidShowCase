package com.example.manuel.baseproject.commons.datatype

import com.example.manuel.baseproject.commons.Error

data class Result<out T>(
        var resultType: ResultType,
        val data: T? = null,
        val error: Error? = null
) {

    companion object {
        fun <T> success(data: T?): Result<T> {
            return Result(ResultType.SUCCESS, data)
        }

        fun <T> error(error: Error): Result<T> {
            return Result(ResultType.ERROR, error = error)
        }
    }
}