package com.example.manuel.baseproject.repository.mapper

import com.example.manuel.baseproject.commons.BaseMapper
import com.example.manuel.baseproject.datasource.model.BeersApi
import com.example.manuel.baseproject.domain.model.BeerEntity
import com.example.manuel.baseproject.domain.model.BeersEntity

object Mapper : BaseMapper<BeersApi, BeersEntity> {

    override fun mapFrom(type: BeersApi?): BeersEntity {
        return BeersEntity(
                type?.beers?.map {
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

