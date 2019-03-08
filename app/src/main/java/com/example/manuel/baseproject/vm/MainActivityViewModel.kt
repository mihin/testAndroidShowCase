package com.example.manuel.baseproject.vm

import android.arch.lifecycle.MutableLiveData
import com.example.manuel.baseproject.commons.utils.dto.ResultWrapper
import com.example.manuel.baseproject.commons.vm.BaseViewModel
import com.example.manuel.baseproject.domain.model.BeerModel
import com.example.manuel.baseproject.domain.usecase.GetBeersUseCase
import kotlinx.coroutines.*

class MainActivityViewModel(private val getMealsByBeersUseCase: GetBeersUseCase) : BaseViewModel() {

    private val screenStateLiveData: MutableLiveData<ResultWrapper<List<BeerModel>>> =
            MutableLiveData()

    fun getScreenStateLivedata():
            MutableLiveData<ResultWrapper<List<BeerModel>>> {
        return screenStateLiveData
    }

    fun onSearchButtonPress(food: String) {
        setScreenStateLiveDataToLoadingState()

        job = GlobalScope.launch(Dispatchers.Default) {
            getMealsByBeersUseCase.execute(food).let {
                screenStateLiveData.postValue(it)
            }
        }
    }

    fun getSortedDesdcendingBeers(beers: ResultWrapper<List<BeerModel>>): List<BeerModel>? =
            beers.data.let {
                it?.sortedByDescending { it.abv }
            }

    private fun setScreenStateLiveDataToLoadingState() {
        screenStateLiveData.value = ResultWrapper.loading()
    }
}
