package com.example.manuel.baseproject.domain.usecase

import com.example.manuel.baseproject.commons.utils.dto.Result
import com.example.manuel.baseproject.commons.utils.enums.ResultType
import com.example.manuel.baseproject.domain.model.BeerModel
import com.example.manuel.baseproject.domain.MealsByBeersRepository

class GetBeersUseCase(private val mealsByBeersRepository: MealsByBeersRepository) {

    suspend fun execute(): Result<List<BeerModel>> {
        val unSortedBeers: Result<List<BeerModel>> = mealsByBeersRepository.getAllBeers()
        var sortedBeers: Result<List<BeerModel>> = unSortedBeers

        val isResultSuccess = unSortedBeers.resultType == ResultType.SUCCESS

        if (isResultSuccess) {
            val beersModel = getSortedAscendingBeers(unSortedBeers)
            sortedBeers = Result.success(beersModel)
        }

        return sortedBeers
    }

    private fun getSortedAscendingBeers(beers: Result<List<BeerModel>>): List<BeerModel>? {
        return beers.data.let {
            it?.sortedBy { it.abv }
        }
    }
}