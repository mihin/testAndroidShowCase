package com.example.manuel.baseproject.ui

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.example.manuel.baseproject.R
import com.example.manuel.baseproject.commons.ui.BaseActivity
import com.example.manuel.baseproject.commons.utils.dto.ResultWrapper
import com.example.manuel.baseproject.commons.utils.enums.MealsType
import com.example.manuel.baseproject.commons.utils.enums.ResultType
import com.example.manuel.baseproject.di.KodeinContainers
import com.example.manuel.baseproject.domain.model.BeerModel
import com.example.manuel.baseproject.ui.adapterlist.BeersAdapter
import com.example.manuel.baseproject.vm.MealsByBeersViewModel
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.newInstance
import kotlinx.android.synthetic.main.activity_beers_results.*

class BeersResultsActivity : BaseActivity() {

    private lateinit var viewModel: MealsByBeersViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_beers_results)

        initViewModel()
        initObservers()
        onSearchButtonPress()
    }

    private fun initViewModel() {
        viewModel = KodeinContainers.diBaseProject.newInstance {
            MealsByBeersViewModel(instance())
        }
    }

    private fun initObservers() {
        viewModel.getScreenStateLivedata().observe(this, Observer(::onResultWrapperReceived))
    }

    private fun onResultWrapperReceived(result: ResultWrapper<List<BeerModel>>?) {
        when (result?.resultType) {
            ResultType.LOADING -> showSpinner(true)
            ResultType.ERROR -> showError()
            ResultType.EMPTY_DATA -> showEmptyDataResult(result.data?.size)
            ResultType.SUCCESS -> showBeers(result.data)// do nothing
        }
    }

    private fun showError() {
        showSpinner(false)
        // Show error message
    }

    private fun showSpinner(isViewVisible: Boolean) {
        main_activity_spinner.apply {
            visibility = if (isViewVisible) View.VISIBLE else View.GONE
        }
    }

    private fun showEmptyDataResult(beersSize: Int?) {
        // Show the message
    }

    private fun showBeers(beersModel: List<BeerModel>?) {
        showSpinner(false)
        beersModel?.let {
            recycler_view_beers.layoutManager = LinearLayoutManager(this)

            val beersAdapter = BeersAdapter(it.toMutableList(), this)
            recycler_view_beers.adapter = beersAdapter
            beersAdapter.updateAdapter(it.toMutableList())

            recycler_view_beers.setHasFixedSize(true)
        }
    }

    private fun onSearchButtonPress() {
        val mealType = getMealTypeFromBundle()

        viewModel.onSearchButtonPress(mealType)
    }

    private fun getMealTypeFromBundle(): MealsType {
        var mealType: MealsType = MealsType.ALL

        val hasIntentMealTypeExtra = intent.hasExtra(IntentConstants.INTENT_MEAL_TYPE)

        if (hasIntentMealTypeExtra) {
            mealType = intent.getSerializableExtra(IntentConstants.INTENT_MEAL_TYPE) as MealsType
        }

        return mealType
    }
}
