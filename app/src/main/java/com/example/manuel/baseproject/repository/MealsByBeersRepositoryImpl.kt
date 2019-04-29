package com.example.manuel.baseproject.repository

import com.example.manuel.baseproject.commons.utils.constants.BaseProjectConstants
import com.example.manuel.baseproject.commons.utils.dto.ResultWrapper
import com.example.manuel.baseproject.commons.utils.enums.MealsType
import com.example.manuel.baseproject.domain.MealsByBeersRepository
import com.example.manuel.baseproject.domain.model.BeerModel
import com.example.manuel.baseproject.repository.mapper.Mapper
import com.example.manuel.baseproject.repository.datasource.MealsByBeersNetworkDatasource
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class MealsByBeersRepositoryImpl constructor(private val mealsByBeersNetworkDatasource: MealsByBeersNetworkDatasource) :
        MealsByBeersRepository {

    private val beers = mutableListOf<BeerModel>()

    @ExperimentalCoroutinesApi
    override suspend fun getBeers(mealType: MealsType): ResultWrapper<List<BeerModel>> {
        return if (mealType == MealsType.ALL) getAllBeers() else getBeersFilteredByMeal(mealType.toString())
    }

    @ExperimentalCoroutinesApi
    private suspend fun getAllBeers(): ResultWrapper<List<BeerModel>> {
        var page = -1

        do {
            page = getPageToCheckBeers(page)

            mealsByBeersNetworkDatasource.getBeers(page.toString())
                    .let {
                        Mapper.map(it).let { wrapperListBeerModel ->
                            wrapperListBeerModel.data?.forEach { beerModel ->
                                beers.add(beerModel)
                            }
                        }
                    }


        } while (page != -1)

        return ResultWrapper.success(beers)
    }

    @ExperimentalCoroutinesApi
    private fun getBeersFilteredByMeal(food: String): ResultWrapper<List<BeerModel>> {
        var page = -1

        do {
            page = getPageToCheckBeers(page)

            mealsByBeersNetworkDatasource.getBeersFilteredByMeal(food, page.toString())
                    .let {
                        Mapper.map(it).let { wrapperListBeerModel ->
                            wrapperListBeerModel.data?.forEach { beerModel ->
                                beers.add(beerModel)
                            }
                        }
                    }
        } while (page != -1)

        return ResultWrapper.success(beers)
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
            (beers.size / page) == BaseProjectConstants.MAX_RESULTS_PER_PAGE
}
