package com.cupcake.chickenmasala.ui.fragment.details.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.cupcake.chickenmasala.R
import com.cupcake.chickenmasala.data.model.DetailsItem
import com.cupcake.chickenmasala.databinding.ItemInfoDetailsBinding
import com.cupcake.chickenmasala.databinding.ListStepIngredientsBinding
import com.cupcake.chickenmasala.databinding.ListStepInstructionsBinding
import com.cupcake.chickenmasala.ui.fragment.details.adapter.viewholder.ItemDetailsViewHolder
import com.cupcake.chickenmasala.ui.fragment.details.adapter.viewholder.ListStepIngredientsViewHolder
import com.cupcake.chickenmasala.ui.fragment.details.adapter.viewholder.ListStepInstructionsViewHolder


class DetailsAdapter(private val items: List<DetailsItem>) :
    RecyclerView.Adapter<DetailsAdapter.BaseDetailsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseDetailsViewHolder {
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
            else -> {
                val view = ListStepInstructionsBinding.inflate(inflater, parent, false)
                ListStepInstructionsViewHolder(view)
            }
        }
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: BaseDetailsViewHolder, position: Int) {
        when (val item = items[position]) {
            is DetailsItem.DetailsRecipe -> (holder as ItemDetailsViewHolder).bindItem(item.recipe)
            is DetailsItem.StepIngredient -> (holder as ListStepIngredientsViewHolder).bindItem(item.ingredients)
            is DetailsItem.StepInstruction -> (holder as ListStepInstructionsViewHolder).bindItem(
                item.instructions
            )
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is DetailsItem.DetailsRecipe -> VIEW_INFO
            is DetailsItem.StepIngredient -> VIEW_STEP_INGREDIENTS
            is DetailsItem.StepInstruction -> VIEW_STEP_INSTRUCTIONS
        }
    }

    abstract class BaseDetailsViewHolder(viewItem: ViewBinding) :
        RecyclerView.ViewHolder(viewItem.root) {
        abstract fun <T> bindItem(items: T)
    }

    companion object {
        const val VIEW_INFO = R.layout.item_info_details
        const val VIEW_STEP_INGREDIENTS = R.layout.list_step_ingredients
        const val VIEW_STEP_INSTRUCTIONS = R.layout.list_step_instructions
    }
}