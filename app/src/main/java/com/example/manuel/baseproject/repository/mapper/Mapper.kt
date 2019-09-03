package com.example.manuel.baseproject.repository.mapper

import com.example.manuel.baseproject.commons.BaseMapper
import com.example.manuel.baseproject.commons.utils.dto.Result
import com.example.manuel.baseproject.commons.utils.enums.ResultType
import com.example.manuel.baseproject.domain.model.BeerModel
import com.example.manuel.baseproject.datasource.model.BeerResponse
import com.example.manuel.baseproject.repository.constants.Constants

object Mapper : BaseMapper<Result<List<BeerResponse>>?, Result<List<BeerModel>>> {

    override fun mapFrom(type: Result<List<BeerResponse>>?): Result<List<BeerModel>> = when {
        type?.resultType?.equals(ResultType.SUCCESS)!! -> mapAux(type)
        else -> Result.error(Constants.NETWORK_DATASOURCE_ERROR_MESSAGE)
    }

    private fun mapAux(beersResponseResult: Result<List<BeerResponse>>):
            Result<List<BeerModel>> {

        return Result.success(beersResponseResult.data?.map {
            BeerModel(
                    id = it.id ?: -1,
                    name = it.name ?: "",
                    tagline = it.tagline ?: "",
                    image = it.image ?: "",
                    abv = it.abv ?: -1.0
            )
        } ?: listOf())
    }
}


