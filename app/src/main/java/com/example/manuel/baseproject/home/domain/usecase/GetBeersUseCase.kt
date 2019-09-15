package com.example.manuel.baseproject.home.domain.usecase

import com.example.manuel.baseproject.home.commons.datatype.Result
import com.example.manuel.baseproject.home.commons.datatype.ResultType
import com.example.manuel.baseproject.home.domain.HomeRepository
import com.example.manuel.baseproject.home.domain.model.BeersEntity

class GetBeersUseCase(private val homeRepository: HomeRepository) {

    suspend fun execute(): Result<BeersEntity> {
        var beers: Result<BeersEntity> = Result.success(BeersEntity(listOf()))

        homeRepository.getAllBeers()?.let { beersEntity ->
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