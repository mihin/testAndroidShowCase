package com.example.manuel.baseproject.domain.usecase

import com.example.manuel.baseproject.commons.datatype.Result
import com.example.manuel.baseproject.commons.datatype.ResultType
import com.example.manuel.baseproject.domain.MealsByBeersRepository
import com.example.manuel.baseproject.domain.model.BeersEntity

class GetBeersUseCase(private val mealsByBeersRepository: MealsByBeersRepository) {

    suspend fun execute(): Result<BeersEntity> {
        var beers: Result<BeersEntity> = Result.success(BeersEntity(listOf()))

        mealsByBeersRepository.getAllBeers()?.let { beersEntity ->
            val resultType = beersEntity.resultType

            if (resultType == ResultType.SUCCESS) {
                beersEntity.data?.let {
                    val sortedBeers = getSortedAscendingBeers(beersEntity.data)
                    beers = Result.success(sortedBeers)
                }
            } else {
                beers = Result.error(beersEntity.error)
            }
        }

        return beers
    }

    private fun getSortedAscendingBeers(beersEntity: BeersEntity): BeersEntity {
        return BeersEntity(
                beersEntity.beers.sortedBy { it.abv }
        )
    }
}