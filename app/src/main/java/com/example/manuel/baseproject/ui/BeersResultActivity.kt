package com.example.manuel.baseproject.ui

import androidx.lifecycle.Observer
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.manuel.baseproject.R
import com.example.manuel.baseproject.commons.utils.dto.Result
import com.example.manuel.baseproject.commons.utils.enums.ResultType
import com.example.manuel.baseproject.di.KodeinContainers
import com.example.manuel.baseproject.ui.adapterlist.BeersAdapter
import com.example.manuel.baseproject.vm.MealsByBeersViewModel
import com.example.manuel.baseproject.vm.model.BeerUI
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.newInstance
import kotlinx.android.synthetic.main.activity_beers_results.*
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class BeersResultActivity : AppCompatActivity() {

    private lateinit var viewModel: MealsByBeersViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_beers_results)

        initViewModel()
        observerScreenState()
    }

    private fun initViewModel() {
        viewModel = KodeinContainers.diBaseProject.newInstance {
            MealsByBeersViewModel(instance())
        }
    }

    private fun observerScreenState() {
        viewModel.getScreenStateLiveData.observe(this, Observer(::onResultReceived))
    }

    private fun onResultReceived(result: Result<List<BeerUI>>?) {
        when (result?.resultType) {
            ResultType.LOADING -> showSpinner(true)
            ResultType.ERROR -> showError()
            ResultType.EMPTY_DATA -> showEmptyDataResult(result.data?.size)
            ResultType.SUCCESS -> showBeers(result.data)
        }
    }

    private fun showError() {
        showSpinner(false)
        // Show error message
    }

    private fun showEmptyDataResult(beersSize: Int?) {
        // Show empty data message
    }

    private fun showBeers(beersUI: List<BeerUI>?) {
        showSpinner(false)

        beersUI?.let {
            recycler_view_beers.layoutManager = LinearLayoutManager(this)

            val beersAdapter = BeersAdapter(it.toMutableList(), this)
            recycler_view_beers.adapter = beersAdapter
            beersAdapter.updateAdapter(it.toMutableList())

            recycler_view_beers.setHasFixedSize(true)
        }
    }

    private fun showSpinner(isViewVisible: Boolean) {
        main_activity_spinner.apply {
            visibility = if (isViewVisible) View.VISIBLE else View.GONE
        }
    }
}
