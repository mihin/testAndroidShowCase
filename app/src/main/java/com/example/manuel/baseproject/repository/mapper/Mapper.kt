package com.example.manuel.baseproject.repository.mapper

import android.util.Log
import com.example.manuel.baseproject.commons.utils.dto.ResultWrapper
import com.example.manuel.baseproject.commons.utils.enums.ResultType
import com.example.manuel.baseproject.commons.utils.constants.BaseProjectConstants
import com.example.manuel.baseproject.domain.model.BeerModel
import com.example.manuel.baseproject.repository.datasource.model.BeerResponse

class Mapper private constructor() {

    companion object {
        fun map(beersResponseResultWrapper: ResultWrapper<List<BeerResponse>>?): ResultWrapper<List<BeerModel>> {
            var beersModelResultWrapper: ResultWrapper<List<BeerModel>> = ResultWrapper.success(listOf())

            beersResponseResultWrapper?.let {
                beersModelResultWrapper =
                        if (it.resultType == ResultType.SUCCESS) mapAux(it)
                        else ResultWrapper.error(BaseProjectConstants.NETWORK_DATASOURCE_ERROR_MESSAGE)
            }

            return beersModelResultWrapper
        }

        private fun mapAux(beersResponseResultWrapper: ResultWrapper<List<BeerResponse>>):
                ResultWrapper<List<BeerModel>> {

            val beersModel: MutableList<BeerModel> = mutableListOf()
            val beersModelResultWrapper: ResultWrapper<List<BeerModel>> = ResultWrapper.success(beersModel)

            beersResponseResultWrapper.data?.forEach {
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

            return beersModelResultWrapper
        }
    }
}
