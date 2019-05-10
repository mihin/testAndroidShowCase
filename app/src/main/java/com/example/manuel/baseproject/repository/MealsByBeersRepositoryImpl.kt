package com.example.manuel.baseproject.repository

import com.example.manuel.baseproject.repository.constants.Constants
import com.example.manuel.baseproject.commons.utils.dto.Result
import com.example.manuel.baseproject.domain.MealsByBeersRepository
import com.example.manuel.baseproject.domain.model.BeerModel
import com.example.manuel.baseproject.datasource.MealsByBeersNetworkDatasource
import com.example.manuel.baseproject.repository.mapper.Mapper
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class MealsByBeersRepositoryImpl constructor(private val mealsByBeersNetworkDatasource: MealsByBeersNetworkDatasource) :
        MealsByBeersRepository {

    private val beers = mutableListOf<BeerModel>()

    @ExperimentalCoroutinesApi
    override suspend fun getAllBeers(): Result<List<BeerModel>> {
        var page = -1

        do {
            page = getPageToCheckBeers(page)

            mealsByBeersNetworkDatasource.getAllBeers(page.toString())
                    .let {
                        Mapper.mapFrom(it).let { beerModelList ->
                            beerModelList.data?.forEach { beerModel ->
                                beers.add(beerModel)
                            }
                        }
                    }


        } while (page != -1)

        return Result.success(beers)
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

    private fun isNecessaryFetchMoreBeers(page: Int) =
            (beers.size / page) == Constants.MAX_RESULTS_PER_PAGE
}
