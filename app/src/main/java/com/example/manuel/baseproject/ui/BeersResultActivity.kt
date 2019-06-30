package com.example.manuel.baseproject.ui

import androidx.lifecycle.Observer
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.manuel.baseproject.R
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
        observerLiveDatas()
    }

    private fun initViewModel() {
        viewModel = KodeinContainers.diBaseProject.newInstance {
            MealsByBeersViewModel(instance())
        }
    }

    private fun observerLiveDatas() {
        viewModel.beers.observe(this, Observer(::onBeersReceived))
        viewModel.isError.observe(this, Observer { onErrorReceived() })
        viewModel.areEmptyBeers.observe(this, Observer { onEmptyBeersReceived() })
        viewModel.isLoading.observe(this, Observer(::onLoadingStateReceived))
    }

    private fun onBeersReceived(beers: List<BeerUI>) {
        showBeers(beers)
    }

    private fun showBeers(beersUI: List<BeerUI>?) {
        beersUI?.let {
            recycler_view_beers.layoutManager = LinearLayoutManager(this)

            val beersAdapter = BeersAdapter(it.toMutableList(), this)
            recycler_view_beers.adapter = beersAdapter
            beersAdapter.updateAdapter(it.toMutableList())

            recycler_view_beers.setHasFixedSize(true)
        }
    }

    private fun onErrorReceived() {
        // do something
    }

    private fun onEmptyBeersReceived() {
        // do something
    }

    private fun onLoadingStateReceived(isLoading: Boolean) {
        showSpinner(isLoading)
    }

    private fun showSpinner(isLoading: Boolean) {
        main_activity_spinner.apply {
            visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }
}
