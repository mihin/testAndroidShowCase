package com.example.manuel.baseproject.repository.mapper

import com.example.manuel.baseproject.commons.BaseMapper
import com.example.manuel.baseproject.commons.utils.dto.Result
import com.example.manuel.baseproject.commons.utils.enums.ResultType
import com.example.manuel.baseproject.repository.constants.Constants
import com.example.manuel.baseproject.domain.model.BeerModel
import com.example.manuel.baseproject.datasource.model.BeerResponse

object Mapper: BaseMapper<Result<List<BeerResponse>>?, Result<List<BeerModel>>> {
    override fun mapFrom(type: Result<List<BeerResponse>>?): Result<List<BeerModel>> {
        var beersModelResult: Result<List<BeerModel>> = Result.success(listOf())

        type?.let {
            beersModelResult =
                    if (it.resultType == ResultType.SUCCESS) Mapper.mapAux(it)
                    else Result.error(Constants.NETWORK_DATASOURCE_ERROR_MESSAGE)
        }

        return beersModelResult
    }

    private fun mapAux(beersResponseResult: Result<List<BeerResponse>>):
            Result<List<BeerModel>> {

        val beersModel: MutableList<BeerModel> = mutableListOf()
        val beersModelResult: Result<List<BeerModel>> = Result.success(beersModel)

        beersResponseResult.data?.forEach {
            beersModel.add(
                    BeerModel(
                            id = it.id,
                            name = it.name,
                            tagline = it.tagline,
                            image = it.image,
                            abv = it.abv
                    )
            )
        }

        return beersModelResult
    }
}
