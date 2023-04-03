package com.cupcake.chickenmasala.ui.fragment.details.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.cupcake.chickenmasala.R
import com.cupcake.chickenmasala.data.model.Recipe
import com.cupcake.chickenmasala.data.model.StepInstructions
import com.cupcake.chickenmasala.databinding.ItemInfoDetailsBinding
import com.cupcake.chickenmasala.databinding.ItemIngredientsBinding
import com.cupcake.chickenmasala.databinding.ItemInstructionsBinding
import com.cupcake.chickenmasala.ui.base.BaseViewHolder
import com.cupcake.chickenmasala.ui.fragment.details.DetailsInteractorListener
import com.cupcake.chickenmasala.utill.setImage
import com.google.android.material.chip.Chip


class DetailsAdapter(
    private val listener: DetailsInteractorListener,
    private var detailsItem: List<DetailsItem<Any>>
) :
    RecyclerView.Adapter<BaseViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            VIEW_INFO -> {
                val view = ItemInfoDetailsBinding.inflate(inflater, parent, false)
                ItemDetailsViewHolder(view)
            }
            VIEW_STEP_INGREDIENTS -> {
                val view = ItemIngredientsBinding.inflate(inflater, parent, false)
                IngredientsViewHolder(view)
            }
            VIEW_STEP_INSTRUCTIONS -> {
                val view = ItemInstructionsBinding.inflate(inflater, parent, false)
                InstructionsViewHolder(view)
            }
            else -> throw Exception("Unknown view type")

        }
    }

    override fun getItemCount(): Int = detailsItem.size

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        when (holder) {
            is ItemDetailsViewHolder -> bindInfoDetails(holder, position)
            is IngredientsViewHolder -> bindIngredients(holder, position)
            is InstructionsViewHolder -> bindInstructions(holder, position)
        }
    }

    private fun bindInfoDetails(holder: ItemDetailsViewHolder, position: Int) {
        val recipe: Recipe = detailsItem[position].item as Recipe
        holder.binding.apply {
            imageViewDetails.setImage(recipe.imageUrl)
            textViewFoodName.text = recipe.translatedRecipeName
            textViewIngredientCount.text = recipe.ingredientCounts.toString()
            textViewTimer.text = recipe.totalTimeInMin.toString()
            chipGroupIngredient.removeAllViews()
            recipe.cleanedIngredients.forEach { item ->
                chipGroupIngredient.addView(createChipView(holder, item))
            }
            toggleIngredients.setOnClickListener {
                enableIngredients(this)
                listener.onIngredientToggleClicked()
            }
            toggleInstructions.setOnClickListener {
                enableInstructions(this)
                listener.onInstructionToggleClicked()
            }
        }
    }

    private fun enableInstructions(itemInfoDetailsBinding: ItemInfoDetailsBinding) {
        with(itemInfoDetailsBinding) {
            toggleInstructions.isChecked = true
            toggleIngredients.isChecked = false
        }
    }

    private fun enableIngredients(itemInfoDetailsBinding: ItemInfoDetailsBinding) {
        with(itemInfoDetailsBinding) {
            toggleIngredients.isChecked = true
            toggleInstructions.isChecked = false
        }
    }

    private fun createChipView(holder: ItemDetailsViewHolder, text: String): Chip {
        val chip = LayoutInflater.from(holder.itemView.context)
            .inflate(R.layout.item_chip, holder.binding.root, false) as Chip
        chip.text = text
        return chip
    }

    private fun bindIngredients(
        holder: IngredientsViewHolder,
        position: Int
    ) {
        val currentIngredient = detailsItem[position].item as String
        holder.binding.apply {
            ingredientsName.text = currentIngredient
        }
    }


    private fun bindInstructions(
        holder: InstructionsViewHolder,
        position: Int
    ) {
        val currentStep = detailsItem[position].item as StepInstructions
        holder.binding.apply {
            textViewStepNumber.text = currentStep.step.toString()
            instructionsDetails.text = currentStep.description
        }
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

    class IngredientsViewHolder(val binding: ItemIngredientsBinding) :
        BaseViewHolder(binding)

    class InstructionsViewHolder(val binding: ItemInstructionsBinding) :
        BaseViewHolder(binding)

    fun submitList(newItems: List<DetailsItem<Any>>) {
        val diffResult = DiffUtil.calculateDiff(
            AppDiffUtil(
                detailsItem,
                newItems,
            )
        )
        detailsItem = newItems
        diffResult.dispatchUpdatesTo(this)
    }

    private class AppDiffUtil(
        private val oldList: List<DetailsItem<Any>>,
        private val newList: List<DetailsItem<Any>>,
    ) : DiffUtil.Callback() {

        override fun getOldListSize() = oldList.size

        override fun getNewListSize() = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            oldList[oldItemPosition].item == newList[newItemPosition].item

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            oldList[oldItemPosition] == newList[newItemPosition]

    }

    companion object {
        const val VIEW_INFO = 0
        const val VIEW_STEP_INGREDIENTS = 1
        const val VIEW_STEP_INSTRUCTIONS = 2
    }
}