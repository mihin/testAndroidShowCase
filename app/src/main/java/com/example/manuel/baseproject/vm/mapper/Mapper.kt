package com.example.manuel.baseproject.vm.mapper

import com.example.manuel.baseproject.commons.BaseMapper
import com.example.manuel.baseproject.domain.model.BeerEntity
import com.example.manuel.baseproject.vm.model.BeerUI

object Mapper : BaseMapper<List<BeerEntity>, List<BeerUI>> {
    override fun mapFrom(type: List<BeerEntity>?): List<BeerUI> {
        return type?.map {
            BeerUI(
                    id = it.id,
                    name = it.name,
                    tagline = it.tagline,
                    image = it.image,
                    abv = it.abv,
                    abvType = it.getAbvType(it.abv)
            )
        } ?: listOf()
    }
}