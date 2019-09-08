package com.example.manuel.baseproject.vm

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.manuel.baseproject.commons.datatype.Result
import com.example.manuel.baseproject.domain.model.BeersEntity
import com.example.manuel.baseproject.domain.model.BusinessErrorType
import com.example.manuel.baseproject.domain.usecase.GetBeersUseCase
import com.example.manuel.baseproject.domain.utils.DomainBeersGenerator
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.*
import kotlinx.coroutines.test.runBlockingTest
import org.junit.*
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/**
 *  https://codelabs.developers.google.com/codelabs/android-testing/#7
 * */
@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class MealsByBeersViewModelTest {

    companion object {
        private const val EXPECTED_IS_LOADING_TRUE = true
        private const val EXPECTED_IS_LOADING_FALSE = false
    }

    @Rule
    @JvmField
    val instantTaskExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Rule
    @JvmField
    val mainCoroutineRule = MainCoroutineRule()

    private var mockGetBeersUseCase: GetBeersUseCase = mock()
    private lateinit var viewModel: MealsByBeersViewModel

    @Test
    fun verifyBeersLiveDataIsNotEmptyWhenResultIsSuccess() {
        mainCoroutineRule.runBlockingTest {
            givenSuccessResult()
            whenViewModelHandleLoadBeers()
            thenAssertLiveData(beersExpected = true)
        }
    }

    @Test
    fun verifyAreEmptyBeersLiveDataIsTrueWhenResultIsSuccess() {
        mainCoroutineRule.runBlockingTest {
            givenSuccessResult(areNecessaryEmptyBeers = true)
            whenViewModelHandleLoadBeers()
            thenAssertLiveData(areEmptyBeersExpected = true)
        }
    }

    @Test
    fun verifyIsErrorLiveDataIsTrueWhenResultIsError() {
        mainCoroutineRule.runBlockingTest {
            givenErrorResult()
            whenViewModelHandleLoadBeers()
            thenAssertLiveData(isErrorExpected = true)
        }
    }

    @Test
    fun verifyIsLoadingLiveDataWhenResultIsSuccess() {
        mainCoroutineRule.runBlockingTest {
            givenSuccessResult()
            assertIsLoadingLiveDataTrueWhenViewModelFetchData()
            assertIsLoadingLiveData(EXPECTED_IS_LOADING_FALSE)
        }
    }

    private fun givenSuccessResult(areNecessaryEmptyBeers: Boolean = false) {
        runBlockingTest {
            val result: Result<BeersEntity> = if (areNecessaryEmptyBeers) {
                Result.success(BeersEntity(listOf()))
            } else {
                Result.success(DomainBeersGenerator.getSortedBeers())
            }

            given(mockGetBeersUseCase.execute()).willReturn(result)
        }
    }

    private fun whenViewModelHandleLoadBeers() {
        viewModel = MealsByBeersViewModel(mockGetBeersUseCase)
    }

    private fun thenAssertLiveData(
            beersExpected: Boolean? = null,
            isErrorExpected: Boolean? = null,
            areEmptyBeersExpected: Boolean? = null
    ) {
        Assert.assertEquals(beersExpected, viewModel.beers.value?.isNotEmpty())
        Assert.assertEquals(isErrorExpected, viewModel.isError.value)
        Assert.assertEquals(areEmptyBeersExpected, viewModel.areEmptyBeers.value)
        Assert.assertEquals(EXPECTED_IS_LOADING_FALSE, viewModel.isLoading.value)
    }

    @Test
    fun verifyIsLoadingLiveDataWhenResultIsError() {
        mainCoroutineRule.runBlockingTest {
            givenErrorResult()
            assertIsLoadingLiveDataTrueWhenViewModelFetchData()
            assertIsLoadingLiveData(EXPECTED_IS_LOADING_FALSE)
        }
    }

    private fun givenErrorResult() {
        runBlockingTest {
            val result: Result<BeersEntity> = Result.error(BusinessErrorType.NETWORK_ERROR)
            given(mockGetBeersUseCase.execute()).willReturn(result)
        }
    }

    private fun assertIsLoadingLiveDataTrueWhenViewModelFetchData() {
        mainCoroutineRule.pauseDispatcher()
        whenViewModelHandleLoadBeers()
        assertIsLoadingLiveData(EXPECTED_IS_LOADING_TRUE)
        mainCoroutineRule.resumeDispatcher()
    }

    private fun assertIsLoadingLiveData(expectedResult: Boolean) {
        val realResult = viewModel.isLoading.value
        Assert.assertEquals(expectedResult, realResult)
    }
}
