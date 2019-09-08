package com.example.manuel.baseproject.datasource.mapper

import com.example.manuel.baseproject.commons.BaseMapper
import com.example.manuel.baseproject.datasource.model.BeerApi
import com.example.manuel.baseproject.datasource.model.BeerResponse
import com.example.manuel.baseproject.datasource.model.BeersApi

object Mapper : BaseMapper<List<BeerResponse>, BeersApi> {

    override fun mapFrom(type: List<BeerResponse>?): BeersApi {
        return BeersApi(type?.map {
            BeerApi(
                    id = it.id ?: -1,
                    name = it.name ?: "",
                    tagline = it.tagline ?: "",
                    image = it.image ?: "",
                    abv = it.abv ?: -1.0
            )
        } ?: listOf())
    }
}