package com.example.manuel.baseproject.commons.datatype

data class Result<out T>(
        var resultType: ResultType,
        val data: T? = null,
        val message: String? = null
) {

    companion object {
        fun <T> success(data: T?): Result<T> {
            return Result(ResultType.SUCCESS, data)
        }

        fun <T> error(message: String): Result<T> {
            return Result(ResultType.ERROR, message = message)
        }
    }
}