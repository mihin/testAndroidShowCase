package com.example.manuel.baseproject.domain

import com.example.manuel.baseproject.commons.datatype.Result
import com.example.manuel.baseproject.domain.model.BeerEntity
import com.example.manuel.baseproject.domain.usecase.GetBeersUseCase
import com.example.manuel.baseproject.domain.utils.BeersGenerator
import com.example.manuel.baseproject.domain.utils.MessagesGenerator
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test

class GetBeersUseCaseTest {

    private val mockMealsByBeersRepository: MealsByBeersRepository = mock()
    private val getBeersUseCase = GetBeersUseCase(mockMealsByBeersRepository)
/*
    @Test
    fun <T> verifyResultWhenRepoMockReturnErrorState() {
        runBlocking {
            given(mockMealsByBeersRepository.getAllBeers()).willReturn(Result.error(MessagesGenerator.NETWORK_DATASOURCE_ERROR_MESSAGE))

            val expectedResult = Result.error<T>(MessagesGenerator.NETWORK_DATASOURCE_ERROR_MESSAGE)
            val realResult = getBeersUseCase.execute()

            Assert.assertEquals(expectedResult, realResult)
        }
    }

    @Test
    fun <T> verifyMessageResultWhenRepoMockReturnErrorState() {
        runBlocking {
            given(mockMealsByBeersRepository.getAllBeers())
                    .willReturn(Result.error(MessagesGenerator.NETWORK_DATASOURCE_ERROR_MESSAGE))

            val expectedResult = Result.error<T>(MessagesGenerator.NETWORK_DATASOURCE_ERROR_MESSAGE)
            val realResult = getBeersUseCase.execute()

            Assert.assertEquals(expectedResult, realResult)
        }
    }

    @Test
    fun verifyResultWhenRepoMockReturnSuccessState() {
        runBlocking {
            val result = Result.success(listOf<BeerEntity>())
            given(mockMealsByBeersRepository.getAllBeers()).willReturn(result)

            val expectedResult = Result.success(listOf<BeerEntity>())
            val realResult = getBeersUseCase.execute()

            Assert.assertEquals(expectedResult, realResult)
        }
    }

    @Test
    fun verifySortedAbvWhenRepoMockReturnUnsortedList() {
        runBlocking {
            val result = Result.success(BeersGenerator.getUnsortedBeers())
            given(mockMealsByBeersRepository.getAllBeers()).willReturn(result)

            val expectedResultBeers =
                    Result.success(BeersGenerator.getSortedBeers()).data
            val realResultBeers = getBeersUseCase.execute().data

            realResultBeers?.forEachIndexed { index, currentRealResult ->
                val currentExpectedResult = expectedResultBeers?.get(index)?.abv
                val realResult = currentRealResult.abv

                Assert.assertEquals(realResult, currentExpectedResult)
            }
        }
    }

    @Test
    fun verifyUseCaseCallRepository() {
        runBlocking {
            given(mockMealsByBeersRepository.getAllBeers()).willReturn(Result.success(listOf()))

            getBeersUseCase.execute()

            verify(mockMealsByBeersRepository, times(1)).getAllBeers()
        }
    }
    */
}