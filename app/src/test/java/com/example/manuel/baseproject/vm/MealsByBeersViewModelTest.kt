package com.example.manuel.baseproject.vm

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.manuel.baseproject.commons.utils.dto.Result.Companion.success
import com.example.manuel.baseproject.domain.model.BeerEntity
import com.example.manuel.baseproject.domain.usecase.GetBeersUseCase
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test

class MealsByBeersViewModelTest {

    private val getBeersUseCase: GetBeersUseCase = mock()
    private val viewModel = MealsByBeersViewModel(getBeersUseCase)

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Test
    fun verifyLiveDataWhenResultIsSuccess() {
        runBlocking {
            val beers = listOf<BeerEntity>()
            val result = success(beers)

            given(getBeersUseCase.execute()).willReturn(result)

            viewModel.beers

            viewModel.beers.value?.let {
                assert(it.isNotEmpty())
            }
        }
    }
}