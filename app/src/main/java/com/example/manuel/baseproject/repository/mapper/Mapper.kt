package com.example.manuel.baseproject.repository.mapper

import com.example.manuel.baseproject.commons.BaseMapper
import com.example.manuel.baseproject.domain.model.BeerEntity
import com.example.manuel.baseproject.datasource.model.BeerResponse
import com.example.manuel.baseproject.domain.model.BeersEntity

object Mapper : BaseMapper<List<BeerResponse>?, BeersEntity> {
    override fun mapFrom(type: List<BeerResponse>?): BeersEntity {
        return BeersEntity(
                type?.map {
                    BeerEntity(
                            id = it.id ?: -1,
                            name = it.name ?: "",
                            tagline = it.tagline ?: "",
                            image = it.image ?: "",
                            abv = it.abv ?: -1.0
                    )
                } ?: listOf()
        )
    }
}


