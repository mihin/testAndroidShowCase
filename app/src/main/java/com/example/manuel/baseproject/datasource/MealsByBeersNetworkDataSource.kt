package com.example.manuel.baseproject.datasource

import com.example.manuel.baseproject.datasource.retrofit.BeersApiService
import kotlinx.coroutines.*
import java.lang.Exception

import com.example.manuel.baseproject.commons.datatype.Result
import com.example.manuel.baseproject.datasource.mapper.Mapper
import com.example.manuel.baseproject.datasource.model.api.BeersApi
import com.example.manuel.baseproject.repository.constants.NetworkError

@ExperimentalCoroutinesApi
class MealsByBeersNetworkDataSource(private val beersApiService: BeersApiService) {

    companion object {
        const val MAX_RESULTS_PER_PAGE: Int = 80
    }

    suspend fun getAllBeers(page: String): Result<BeersApi> {
        var result: Result<BeersApi> = Result.success(BeersApi(listOf()))

        withContext(Dispatchers.IO) {
            try {
                val request = beersApiService.getAllBeersAsync(
                        page,
                        MAX_RESULTS_PER_PAGE.toString()
                )

                val response = request?.await()

                request?.let {
                    if (it.isCompleted) result = Result.success(Mapper.mapFrom(response))
                    else if (it.isCancelled) result =
                            Result.error(NetworkError.API_ERROR)
                }
            } catch (ex: Exception) {
                result = Result.error(NetworkError.API_ERROR)
            }
        }

        return result
    }
}