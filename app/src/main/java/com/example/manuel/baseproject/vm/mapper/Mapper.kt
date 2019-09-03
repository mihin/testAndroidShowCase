package com.example.manuel.baseproject.vm.mapper

import com.example.manuel.baseproject.commons.BaseMapper
import com.example.manuel.baseproject.domain.model.BeerModel
import com.example.manuel.baseproject.vm.model.AbvType
import com.example.manuel.baseproject.vm.model.BeerUI

object Mapper : BaseMapper<List<BeerModel>, List<BeerUI>> {
    override fun mapFrom(type: List<BeerModel>?): List<BeerUI> {
        return type?.map {
            BeerUI(
                    id = it.id,
                    name = it.name,
                    tagline = it.tagline,
                    image = it.image,
                    abv = it.abv,
                    abvType = Mapper.getAbvType(it.abv)
            )
        } ?: listOf()
    }

    private fun getAbvType(abv: Double) =
            when {
                abv < 5 -> AbvType.GREEN
                abv >= 5 && abv < 8 -> AbvType.ORANGE
                else -> AbvType.RED
            }
}