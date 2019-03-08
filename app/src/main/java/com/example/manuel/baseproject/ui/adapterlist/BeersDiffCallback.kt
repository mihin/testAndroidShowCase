package com.example.manuel.baseproject.ui.adapterlist

import android.support.v7.util.DiffUtil
import com.example.manuel.baseproject.domain.model.BeerModel

class BeersDiffCallback(private val oldBeers: List<BeerModel>,
                        private val newBeers: List<BeerModel>) : DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldBeers[oldItemPosition].id == newBeers[newItemPosition].id

    override fun getOldListSize(): Int = oldBeers.size

    override fun getNewListSize(): Int = newBeers.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldBeers[oldItemPosition] == newBeers[newItemPosition]
}