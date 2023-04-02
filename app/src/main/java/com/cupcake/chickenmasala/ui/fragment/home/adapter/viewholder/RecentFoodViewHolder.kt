package com.cupcake.chickenmasala.ui.fragment.home.adapter.viewholder

import com.cupcake.chickenmasala.data.model.Recipe
import com.cupcake.chickenmasala.databinding.ItemRecyclerRecentFoodBinding
import com.cupcake.chickenmasala.ui.fragment.home.HomeInteractorListener
import com.cupcake.chickenmasala.ui.fragment.home.adapter.HomeRecyclerAdapter
import com.cupcake.chickenmasala.ui.fragment.home.adapter.HorizontalRecipeRecyclerAdapter

class RecentFoodViewHolder(
    private val binding: ItemRecyclerRecentFoodBinding,
    private val listener: HomeInteractorListener
) : HomeRecyclerAdapter.BaseHomeViewHolder(binding) {
    override fun <T> bindItem(items: T) {
        val recentRecipes = items as List<Recipe>
        val horizontalAdapter = HorizontalRecipeRecyclerAdapter(listener)
        horizontalAdapter.submitList(recentRecipes)
        binding.apply {
            recyclerViewRecentFood.adapter = horizontalAdapter
        }
    }
}
