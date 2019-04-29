package com.example.manuel.baseproject.repository.datasource

import com.example.manuel.baseproject.commons.utils.dto.ResultWrapper
import com.example.manuel.baseproject.commons.utils.constants.BaseProjectConstants
import com.example.manuel.baseproject.repository.datasource.model.BeerResponse
import com.example.manuel.baseproject.repository.datasource.retrofit.BeersApiService
import com.example.manuel.baseproject.repository.datasource.retrofit.RetrofitConfiguration
import kotlinx.coroutines.*
import java.lang.Exception

@ExperimentalCoroutinesApi
class MealsByBeersNetworkDatasource(private val retrofitConfiguration: RetrofitConfiguration) {

    suspend fun getBeersFilteredByMeal(food: String, page: String): ResultWrapper<List<BeerResponse>>? {
        var result: ResultWrapper<List<BeerResponse>>? = ResultWrapper.emptyData()

        try {
            val retrofitInstance = retrofitConfiguration.getRetrofitInstance()

            val beersService = retrofitInstance.create(BeersApiService::class.java)
            val request = beersService.getBeersFilteredByMeal(
                    food,
                    page,
                    BaseProjectConstants.MAX_RESULTS_PER_PAGE.toString()
            )

            val response = request?.await()

            request?.let {
                if (it.isCompleted) result = ResultWrapper.success(response)
                else if (it.isCancelled) result = ResultWrapper.error(BaseProjectConstants.NETWORK_DATASOURCE_ERROR_MESSAGE)
            }
        } catch (ex: Exception) {
            result = ResultWrapper.error(BaseProjectConstants.NETWORK_DATASOURCE_ERROR_MESSAGE)
        }

        return result
    }

    suspend fun getBeers(page: String): ResultWrapper<List<BeerResponse>>? {
        var result: ResultWrapper<List<BeerResponse>>? = ResultWrapper.emptyData()

        try {
            val retrofitInstance = retrofitConfiguration.getRetrofitInstance()

            val beersService = retrofitInstance.create(BeersApiService::class.java)
            val request = beersService.getBeers(
                    page,
                    BaseProjectConstants.MAX_RESULTS_PER_PAGE.toString()
            )

            val response = request?.await()

            request?.let {
                if (it.isCompleted) result = ResultWrapper.success(response)
                else if (it.isCancelled) result = ResultWrapper.error(BaseProjectConstants.NETWORK_DATASOURCE_ERROR_MESSAGE)
            }
        } catch (ex: Exception) {
            result = ResultWrapper.error(BaseProjectConstants.NETWORK_DATASOURCE_ERROR_MESSAGE)
        }

        return result
    }
}
