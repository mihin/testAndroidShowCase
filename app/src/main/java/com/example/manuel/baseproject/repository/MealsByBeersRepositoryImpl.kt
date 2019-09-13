package com.example.manuel.baseproject.repository

import com.example.manuel.baseproject.commons.datatype.Result
import com.example.manuel.baseproject.commons.datatype.ResultType
import com.example.manuel.baseproject.commons.exceptions.NetworkConnectionException
import com.example.manuel.baseproject.commons.exceptions.BadRequestException
import com.example.manuel.baseproject.domain.MealsByBeersRepository
import com.example.manuel.baseproject.domain.model.BeerEntity
import com.example.manuel.baseproject.datasource.MealsByBeersNetworkDataSource
import com.example.manuel.baseproject.datasource.model.api.BeersApi
import com.example.manuel.baseproject.domain.model.BeersEntity
import com.example.manuel.baseproject.repository.mapper.RepositoryMapper
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class MealsByBeersRepositoryImpl constructor(
        private val mealsByBeersNetworkDataSource: MealsByBeersNetworkDataSource
) : MealsByBeersRepository {

    private val beers = mutableListOf<BeerEntity>()

    @ExperimentalCoroutinesApi
    override suspend fun getAllBeers(): Result<BeersEntity>? {
        var page = -1

        var result: Result<BeersEntity>?

        do {
            page = getPageToCheckBeers(page)

            mealsByBeersNetworkDataSource.getAllBeers(page.toString()).let { resultListBeerResponse ->
                addAllBeersUntilLastPage(resultListBeerResponse)
                result = initResult(resultListBeerResponse)
            }
        } while (result?.resultType != Result.error<Error>().resultType && beers.size == 0)

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
        RepositoryMapper.ApiToEntityMapper.map(beersApiResult.data).let { beersEntity ->
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
                Result.error(NetworkConnectionException())
            }
        }
    }

    private fun hasNotMoreBeers(error: Exception?): Boolean {
        return beers.isNotEmpty() && error == BadRequestException()
    }
}
