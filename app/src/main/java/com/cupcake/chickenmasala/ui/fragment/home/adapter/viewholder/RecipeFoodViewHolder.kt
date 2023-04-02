package com.cupcake.chickenmasala.ui.fragment.home.adapter.viewholder

import com.cupcake.chickenmasala.data.model.Recipe
import com.cupcake.chickenmasala.databinding.ItemCardRecipesFoodBinding
import com.cupcake.chickenmasala.ui.fragment.home.HomeInteractorListener
import com.cupcake.chickenmasala.ui.fragment.home.adapter.HomeRecyclerAdapter
import com.cupcake.chickenmasala.utill.setImage

class RecipeFoodViewHolder(
    private val binding: ItemCardRecipesFoodBinding,
    private val listener: HomeInteractorListener,
    ) :
    HomeRecyclerAdapter.BaseHomeViewHolder(binding) {
    override fun <T> bindItem(items: T) {
        val recipe = items as Recipe
        binding.apply {
            textViewCuisineName.text = recipe.cuisine
            textViewRecipeName.text = recipe.translatedRecipeName
            textViewRecipeTime.text = recipe.totalTimeInMin.toString()
            imageViewCardImage.setImage(recipe.imageUrl)
            root.setOnClickListener {
                listener.onRecipeCardClicked(recipe.id)
            }
        }
    }
}
