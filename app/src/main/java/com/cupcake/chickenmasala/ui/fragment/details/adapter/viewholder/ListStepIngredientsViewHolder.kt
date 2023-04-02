package com.cupcake.chickenmasala.ui.fragment.details.adapter.viewholder

import com.cupcake.chickenmasala.databinding.ListStepIngredientsBinding
import com.cupcake.chickenmasala.ui.fragment.details.adapter.DetailsAdapter
import com.cupcake.chickenmasala.ui.fragment.details.adapter.IngredientsAdapter

class ListStepIngredientsViewHolder(val binding: ListStepIngredientsBinding) :
    DetailsAdapter.BaseDetailsViewHolder(binding) {
    override fun <T> bindItem(items: T) {
        val ingredients = items as List<String>
        val adapter = IngredientsAdapter()
        adapter.submitList(ingredients)
        binding.recyclerIngredients.adapter = adapter
    }
}