package com.example.manuel.baseproject.ui.adapterlist

import androidx.recyclerview.widget.DiffUtil
import com.example.manuel.baseproject.vm.model.BeerUI

class BeersDiffCallback(private val oldBeers: List<BeerUI>,
                        private val newBeers: List<BeerUI>) : DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldBeers[oldItemPosition].id == newBeers[newItemPosition].id

    override fun getOldListSize(): Int = oldBeers.size

    override fun getNewListSize(): Int = newBeers.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldBeers[oldItemPosition] == newBeers[newItemPosition]
}