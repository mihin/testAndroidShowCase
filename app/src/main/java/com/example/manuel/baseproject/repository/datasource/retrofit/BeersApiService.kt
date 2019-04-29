package com.example.manuel.baseproject.repository.datasource.retrofit

import com.example.manuel.baseproject.repository.datasource.model.BeerResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface BeersApiService {

    @GET("beers?")
    fun getBeersFilteredByMeal(@Query("food") food: String,
                               @Query("page") page: String,
                               @Query("per_page") perPage: String): Deferred<List<BeerResponse>>?

    @GET("beers?")
    fun getBeers(@Query("page") page: String,
                 @Query("per_page") perPage: String): Deferred<List<BeerResponse>>?
}