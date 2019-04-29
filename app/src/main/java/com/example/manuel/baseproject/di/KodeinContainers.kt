package com.example.manuel.baseproject.di

import com.example.manuel.baseproject.domain.usecase.GetBeersUseCase
import com.example.manuel.baseproject.domain.MealsByBeersRepository
import com.example.manuel.baseproject.repository.MealsByBeersRepositoryImpl
import com.example.manuel.baseproject.repository.datasource.MealsByBeersNetworkDatasource
import com.example.manuel.baseproject.repository.datasource.retrofit.RetrofitConfiguration
import com.example.manuel.baseproject.vm.MealsByBeersViewModel
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.provider

class KodeinContainers {

    companion object {
        val diBaseProject = Kodein {
            bind<RetrofitConfiguration>() with provider { RetrofitConfiguration() }
            bind<MealsByBeersNetworkDatasource>() with provider { MealsByBeersNetworkDatasource(instance()) }
            bind<MealsByBeersRepository>() with provider { MealsByBeersRepositoryImpl(instance()) }
            bind<GetBeersUseCase>() with provider { GetBeersUseCase(instance()) }
            bind<MealsByBeersViewModel>() with provider { MealsByBeersViewModel(instance()) }
        }
    }
}