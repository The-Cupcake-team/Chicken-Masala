package com.cupcake.chickenmasala.ui.fragment.details.adapter.viewholder

import android.view.LayoutInflater
import com.cupcake.chickenmasala.R
import com.cupcake.chickenmasala.data.model.Recipe
import com.cupcake.chickenmasala.databinding.ItemInfoDetailsBinding
import com.cupcake.chickenmasala.ui.fragment.details.adapter.DetailsAdapter
import com.cupcake.chickenmasala.utill.setImage
import com.google.android.material.chip.Chip

class ItemDetailsViewHolder(val binding: ItemInfoDetailsBinding) :
    DetailsAdapter.BaseDetailsViewHolder(binding) {
    override fun <T> bindItem(items: T) {
        val recipe = items as Recipe
        binding.apply {
            imageViewDetails.setImage(recipe.imageUrl)
            textViewFoodName.text = recipe.translatedRecipeName
            textViewIngredientCount.text = recipe.ingredientCounts.toString()
            textViewCuisine.text = recipe.cuisine
            textViewTimer.text = recipe.totalTimeInMin.toString()
            recipe.cleanedIngredients.forEach { item ->
                chipGroupIngredient.addView(createChipView(this@ItemDetailsViewHolder, item))
            }

        }
    }

    private fun createChipView(holder: ItemDetailsViewHolder, text: String): Chip {
        val chip = LayoutInflater.from(holder.itemView.context)
            .inflate(R.layout.item_chip, holder.binding.root, false) as Chip
        chip.text = text
        return chip
    }
}