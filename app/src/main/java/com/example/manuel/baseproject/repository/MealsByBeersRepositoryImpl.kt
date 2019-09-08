package com.example.manuel.baseproject.repository

import com.example.manuel.baseproject.commons.Error
import com.example.manuel.baseproject.commons.datatype.Result
import com.example.manuel.baseproject.commons.datatype.ResultType
import com.example.manuel.baseproject.domain.MealsByBeersRepository
import com.example.manuel.baseproject.domain.model.BeerEntity
import com.example.manuel.baseproject.datasource.MealsByBeersNetworkDataSource
import com.example.manuel.baseproject.datasource.model.BeersApi
import com.example.manuel.baseproject.domain.model.BeersEntity
import com.example.manuel.baseproject.domain.model.BusinessErrorType
import com.example.manuel.baseproject.repository.constants.NetworkError
import com.example.manuel.baseproject.repository.mapper.Mapper
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class MealsByBeersRepositoryImpl constructor(
        private val mealsByBeersNetworkDataSource: MealsByBeersNetworkDataSource
) : MealsByBeersRepository {

    private val beers = mutableListOf<BeerEntity>()

    @ExperimentalCoroutinesApi
    override suspend fun getAllBeers(): Result<BeersEntity>? {
        var page = -1

        var result: Result<BeersEntity>? = null

        do {
            page = getPageToCheckBeers(page)

            mealsByBeersNetworkDataSource.getAllBeers(page.toString())?.let { resultListBeerResponse ->
                addAllBeersUntilLastPage(resultListBeerResponse)
                result = initResult(resultListBeerResponse)
            }

        } while (page != -1)

        return result
    }

    private fun getPageToCheckBeers(currentPage: Int): Int {
        var page: Int = currentPage

        if (hasBeers()) {
            if (isNecessaryFetchMoreBeers(currentPage)) page++ else page = -1
        } else {
            page = 1
        }

        return page
    }

    private fun hasBeers() = beers.size > 0

    private fun isNecessaryFetchMoreBeers(page: Int): Boolean {
        return (beers.size / page) == MealsByBeersNetworkDataSource.MAX_RESULTS_PER_PAGE
    }

    private fun addAllBeersUntilLastPage(beersApiResult: Result<BeersApi>) {
        Mapper.mapFrom(beersApiResult.data).let { beersEntity ->
            beersEntity.beers.forEach { beerEntity ->
                beers.add(beerEntity)
            }
        }
    }

    private fun initResult(beersApiResult: Result<BeersApi>): Result<BeersEntity> {
        return if (beersApiResult.resultType == ResultType.SUCCESS) {
            Result.success(BeersEntity(beers))
        } else {
            if (hasNotMoreBeers(beersApiResult.error)) {
                Result.success(BeersEntity(beers))
            } else {
                Result.error(BusinessErrorType.NETWORK_ERROR)
            }
        }
    }

    private fun hasNotMoreBeers(error: Error?): Boolean {
        return beers.isNotEmpty() && error == NetworkError.API_ERROR
    }
}
