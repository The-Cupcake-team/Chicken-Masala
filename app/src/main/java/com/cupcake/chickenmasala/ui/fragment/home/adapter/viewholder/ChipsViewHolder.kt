package com.cupcake.chickenmasala.ui.fragment.home.adapter.viewholder

import com.cupcake.chickenmasala.data.model.Recipe
import com.cupcake.chickenmasala.databinding.ItemChipsRecipeFoodBinding
import com.cupcake.chickenmasala.ui.fragment.home.HomeInteractorListener
import com.cupcake.chickenmasala.ui.fragment.home.adapter.HomeRecyclerAdapter

class ChipsViewHolder(
    private val binding: ItemChipsRecipeFoodBinding,
    private val listener: HomeInteractorListener
) : HomeRecyclerAdapter.BaseHomeViewHolder(binding) {
    override fun <T> bindItem(items: T) {
        val recipe = items as Int
        binding.chipsFilter.setOnCheckedStateChangeListener { _, checkedIds ->
            listener.onChipClicked(checkedIds[0])
        }
    }
}
