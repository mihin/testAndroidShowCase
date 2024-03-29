package com.example.manuel.baseproject.home.datasource.model.api

/**
 *  This data model is to avoid the Repository layer knows the DataSource layer data model to
 *  fetch data from Api.
 * */
data class BeersApi(
        val beers: List<BeerApi>
)