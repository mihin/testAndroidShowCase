package com.example.manuel.baseproject.vm.mapper

import com.example.manuel.baseproject.commons.BaseMapper
import com.example.manuel.baseproject.commons.utils.dto.Result
import com.example.manuel.baseproject.commons.utils.enums.ResultType
import com.example.manuel.baseproject.domain.model.BeerModel
import com.example.manuel.baseproject.vm.model.BeerUI

object Mapper: BaseMapper<Result<List<BeerModel>>, Result<List<BeerUI>>> {
    override fun mapFrom(type: Result<List<BeerModel>>?): Result<List<BeerUI>> {
        lateinit var result: Result<List<BeerUI>>

        type?.let {
            if (it.resultType == ResultType.SUCCESS) {
                val beersUi = mutableListOf<BeerUI>()
                type.data?.forEach {beerModel ->
                    beersUi.add(
                            BeerUI(
                                    id = beerModel.id,
                                    name = beerModel.name,
                                    tagline = beerModel.tagline,
                                    image = beerModel.image,
                                    abv = beerModel.abv
                            )
                    )
                }
                result = Result.success(beersUi)
            } else {
                result = Result<List<BeerUI>>(type.resultType)
            }
        }

        return result
    }
}
