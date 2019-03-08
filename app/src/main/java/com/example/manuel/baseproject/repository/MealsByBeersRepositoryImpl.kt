package com.example.manuel.baseproject.repository

import com.example.manuel.baseproject.commons.utils.constants.BaseProjectConstants
import com.example.manuel.baseproject.commons.utils.dto.ResultWrapper
import com.example.manuel.baseproject.domain.model.BeerModel
import com.example.manuel.baseproject.repository.mapper.Mapper
import com.example.manuel.baseproject.repository.datasource.MealsByBeersNetworkDatasource
import kotlinx.coroutines.ExperimentalCoroutinesApi

class MealsByBeersRepositoryImpl @ExperimentalCoroutinesApi constructor(private val mealsByBeersNetworkDatasource: MealsByBeersNetworkDatasource) :
        MealsByBeersRepository {

    private val beers = mutableListOf<BeerModel>()

    @ExperimentalCoroutinesApi
    override fun getBeers(food: String): ResultWrapper<List<BeerModel>> {
        do {
            val page = getPage()

            mealsByBeersNetworkDatasource.getBeers(food, page.toString())
                    .let {
                        Mapper.map(it).let { wrapperListBeerModel ->
                            wrapperListBeerModel.data?.forEach { beerModel ->
                                beers.add(beerModel)
                            }
                        }
                    }
        } while (hasNextPage())

        return ResultWrapper.success(beers)
    }

    private fun getPage(): Int {
        var page: Int = 1

        beers.size.let {
            if (isNeededToUpdateThePageValue()) page =
                    (beers.size / BaseProjectConstants.MAX_RESULTS_PER_PAGE) + 1
        }

        return page
    }

    private fun isNeededToUpdateThePageValue() = beers.size % BaseProjectConstants.MAX_RESULTS_PER_PAGE == 0
            && beers.size < BaseProjectConstants.MAX_RESULTS_PER_PAGE


    private fun hasNextPage() = beers.size % BaseProjectConstants.MAX_RESULTS_PER_PAGE == 0
}
