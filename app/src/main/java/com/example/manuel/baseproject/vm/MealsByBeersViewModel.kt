package com.example.manuel.baseproject.vm

import android.arch.lifecycle.MutableLiveData
import com.example.manuel.baseproject.commons.utils.dto.Result
import com.example.manuel.baseproject.commons.vm.BaseViewModel
import com.example.manuel.baseproject.domain.model.BeerModel
import com.example.manuel.baseproject.domain.usecase.GetBeersUseCase
import kotlinx.coroutines.*

class MealsByBeersViewModel(private val getMealsByBeersUseCase: GetBeersUseCase) : BaseViewModel() {

    private val screenStateLiveData: MutableLiveData<Result<List<BeerModel>>> =
            MutableLiveData()

    init {
        handleBeersResultLoad()
    }

    fun getScreenStateLiveData() = screenStateLiveData

    private fun handleBeersResultLoad() {
        setScreenStateLiveDataToLoadingState()

        job = GlobalScope.launch(Dispatchers.IO) {
            getMealsByBeersUseCase.execute().let {
                screenStateLiveData.postValue(it)
            }
        }
    }

    private fun setScreenStateLiveDataToLoadingState() {
        screenStateLiveData.value = Result.loading()
    }
}
