package com.example.manuel.baseproject.datasource

import com.example.manuel.baseproject.repository.constants.Constants
import com.example.manuel.baseproject.datasource.model.BeerResponse
import com.example.manuel.baseproject.datasource.retrofit.BeersApiService
import com.example.manuel.baseproject.datasource.retrofit.RetrofitConfiguration
import kotlinx.coroutines.*
import java.lang.Exception

import com.example.manuel.baseproject.commons.utils.dto.Result

@ExperimentalCoroutinesApi
class MealsByBeersNetworkDatasource(private val retrofitConfiguration: RetrofitConfiguration) {

    suspend fun getAllBeers(page: String): Result<List<BeerResponse>>? {
        var result: Result<List<BeerResponse>>? = Result.success(listOf())

        withContext(Dispatchers.IO) {
            try {
                val retrofitInstance = retrofitConfiguration.getRetrofitInstance()

                val beersService = retrofitInstance.create(BeersApiService::class.java)
                val request = beersService.getAllBeersAsync(
                        page,
                        Constants.MAX_RESULTS_PER_PAGE.toString()
                )

                val response = request?.await()

                request?.let {
                    if (it.isCompleted) result = Result.success(response)
                    else if (it.isCancelled) result =
                            Result.error(Constants.NETWORK_DATASOURCE_ERROR_MESSAGE)
                }
            } catch (ex: Exception) {
                result = Result.error(Constants.NETWORK_DATASOURCE_ERROR_MESSAGE)
            }
        }

        return result
    }
}
