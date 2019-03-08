package com.example.manuel.baseproject.domain.usecase

import com.example.manuel.baseproject.commons.utils.dto.ResultWrapper
import com.example.manuel.baseproject.commons.utils.enums.ResultType
import com.example.manuel.baseproject.domain.model.BeerModel
import com.example.manuel.baseproject.repository.MealsByBeersRepository

class GetBeersUseCase(private val mealsByBeersRepository: MealsByBeersRepository) {

    fun execute(food: String): ResultWrapper<List<BeerModel>> {
        val unSortedBeers: ResultWrapper<List<BeerModel>> = mealsByBeersRepository.getBeers(food)
        var sortedBeers: ResultWrapper<List<BeerModel>> = unSortedBeers

        val isResultSuccess = unSortedBeers.resultType == ResultType.SUCCESS

        if (isResultSuccess) {
            val beersModel = getSortedAscendingBeers(unSortedBeers)
            sortedBeers = ResultWrapper.success(beersModel)
        }

        return sortedBeers
    }

    private fun getSortedAscendingBeers(beers: ResultWrapper<List<BeerModel>>): List<BeerModel>? {
        return beers.data.let {
            it?.sortedBy { it.abv }
        }
    }
}