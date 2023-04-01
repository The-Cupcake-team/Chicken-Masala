package com.cupcake.chickenmasala.ui.fragment.details.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cupcake.chickenmasala.R
import com.cupcake.chickenmasala.data.model.Recipe
import com.cupcake.chickenmasala.databinding.ItemInfoDetailsBinding
import com.cupcake.chickenmasala.databinding.ListStepIngredientsBinding
import com.cupcake.chickenmasala.databinding.ListStepInstructionsBinding
import com.cupcake.chickenmasala.ui.base.BaseViewHolder
import com.cupcake.chickenmasala.utill.setImage
import com.google.android.material.chip.Chip


class DetailsAdapter(private val detailsItem: List<DetailsItem<Any>>) :
    RecyclerView.Adapter<BaseViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            
            VIEW_INFO -> {
                val view = ItemInfoDetailsBinding.inflate(inflater, parent, false)
                ItemDetailsViewHolder(view)
            }

            VIEW_STEP_INGREDIENTS -> {
                val view = ListStepIngredientsBinding.inflate(inflater, parent, false)
                ListStepIngredientsViewHolder(view)
            }

            VIEW_STEP_INSTRUCTIONS -> {
                val view = ListStepInstructionsBinding.inflate(inflater, parent, false)
                ListStepInstructionsViewHolder(view)
            }

            else -> throw Exception("Unknown view type")

        }
    }

    override fun getItemCount(): Int = detailsItem.size

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        when (holder) {
            is ItemDetailsViewHolder -> bindInfoDetails(holder, position)
            is ListStepIngredientsViewHolder -> bindListStepIngredients(
                holder,
                position
            )
            is ListStepInstructionsViewHolder -> bindListStepInstructions(
                holder,
                position
            )
        }
    }

    private fun bindInfoDetails(holder: ItemDetailsViewHolder, position: Int) {
        val recipe: Recipe = detailsItem[position].item as Recipe
        holder.binding.apply {
            imageViewDetails.setImage(recipe.imageUrl)
            textViewFoodName.text = recipe.translatedRecipeName
            textViewIngredientCount.text = recipe.ingredientCounts.toString()
            textViewCuisine.text = recipe.cuisine
            textViewTimer.text = recipe.totalTimeInMin.toString()
            recipe.cleanedIngredients.forEach { item ->
                chipGroupIngredient.addView(createChipView(holder, item))
            }

        }
    }

    private fun createChipView(holder: ItemDetailsViewHolder, text: String): Chip {
        val chip = LayoutInflater.from(holder.itemView.context)
            .inflate(R.layout.item_chip, holder.binding.root, false) as Chip
        chip.text = text
        return chip
    }
    
    private fun bindListStepIngredients(
        holder: ListStepIngredientsViewHolder,
        position: Int
    ) {
        val currentStep: List<String> = detailsItem[position].item as List<String>
        val adapter = IngredientsAdapter()
        adapter.submitList(currentStep)
        holder.binding.recyclerIngredients.adapter = adapter
    }


    private fun bindListStepInstructions(
        holder: ListStepInstructionsViewHolder,
        position: Int
    ) {
        val currentStep: List<String> = detailsItem[position].item as List<String>
        val adapter = InstructionsAdapter()
        adapter.submitList(currentStep)
        holder.binding.recyclerInstructions.adapter = adapter
    }

    override fun getItemViewType(position: Int): Int {
        return when (detailsItem[position].type) {
            DetailsItemType.INFO -> VIEW_INFO
            DetailsItemType.STEP_INGREDIENTS -> VIEW_STEP_INGREDIENTS
            DetailsItemType.STEP_INSTRUCTIONS -> VIEW_STEP_INSTRUCTIONS
        }
    }


    class ItemDetailsViewHolder(val binding: ItemInfoDetailsBinding) :
        BaseViewHolder(binding)

    class ListStepIngredientsViewHolder(val binding: ListStepIngredientsBinding) :
        BaseViewHolder(binding)

    class ListStepInstructionsViewHolder(val binding: ListStepInstructionsBinding) :
        BaseViewHolder(binding)

    companion object {
        const val VIEW_INFO = 0
        const val VIEW_STEP_INGREDIENTS = 1
        const val VIEW_STEP_INSTRUCTIONS = 2
    }
}