package com.example.manuel.baseproject.domain.usecase

import com.example.manuel.baseproject.commons.utils.dto.Result
import com.example.manuel.baseproject.commons.utils.enums.ResultType
import com.example.manuel.baseproject.domain.model.BeerEntity
import com.example.manuel.baseproject.domain.MealsByBeersRepository

class GetBeersUseCase(private val mealsByBeersRepository: MealsByBeersRepository) {

    suspend fun execute(): Result<List<BeerEntity>> {
        val unSortedBeers: Result<List<BeerEntity>> = mealsByBeersRepository.getAllBeers()
        var sortedBeers: Result<List<BeerEntity>> = unSortedBeers

        val isResultSuccess = unSortedBeers.resultType == ResultType.SUCCESS

        if (isResultSuccess) {
            val beersModel = getSortedAscendingBeers(unSortedBeers)
            sortedBeers = Result.success(beersModel)
        }

        return sortedBeers
    }

    private fun getSortedAscendingBeers(beers: Result<List<BeerEntity>>): List<BeerEntity>? {
        return beers.data.let {
            it?.sortedBy { it.abv }
        }
    }
}