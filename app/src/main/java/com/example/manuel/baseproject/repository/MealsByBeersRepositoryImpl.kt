package com.example.manuel.baseproject.repository

import com.example.manuel.baseproject.repository.constants.Constants
import com.example.manuel.baseproject.commons.datatype.Result
import com.example.manuel.baseproject.commons.datatype.ResultType
import com.example.manuel.baseproject.domain.MealsByBeersRepository
import com.example.manuel.baseproject.domain.model.BeerEntity
import com.example.manuel.baseproject.datasource.MealsByBeersNetworkDataSource
import com.example.manuel.baseproject.datasource.model.BeerResponse
import com.example.manuel.baseproject.domain.model.BeersEntity
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
        return (beers.size / page) == Constants.MAX_RESULTS_PER_PAGE
    }

    private fun addAllBeersUntilLastPage(resultListBeerResponse: Result<List<BeerResponse>>) {
        Mapper.mapFrom(resultListBeerResponse.data).let { beersEntity ->
            beersEntity.beers.forEach { beerEntity ->
                beers.add(beerEntity)
            }
        }
    }

    private fun initResult(resultListBeerResponse: Result<List<BeerResponse>>): Result<BeersEntity> {
        return if (resultListBeerResponse.resultType == ResultType.SUCCESS) {
            Result.success(BeersEntity(beers))
        } else {
            if (hasNotMoreBeers(resultListBeerResponse.message)) {
                Result.success(BeersEntity(beers))
            } else {
                Result.error(Constants.NETWORK_DATA_SOURCE_ERROR_MESSAGE)
            }
        }
    }

    private fun hasNotMoreBeers(message: String?): Boolean {
        return beers.isNotEmpty() && message == Constants.NETWORK_DATA_SOURCE_ERROR_MESSAGE
    }
}
