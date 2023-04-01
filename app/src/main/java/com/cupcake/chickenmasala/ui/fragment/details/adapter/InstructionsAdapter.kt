package com.cupcake.chickenmasala.ui.fragment.details.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.cupcake.chickenmasala.R
import com.cupcake.chickenmasala.data.model.StepInstructions
import com.cupcake.chickenmasala.databinding.ItemInstructionsBinding
import com.cupcake.chickenmasala.ui.base.BaseAdapter


class InstructionsAdapter : BaseAdapter<StepInstructions, ItemInstructionsBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> ItemInstructionsBinding =
        ItemInstructionsBinding::inflate

    override fun <T> areItemsTheSame(
        oldItemPosition: Int,
        newItemPosition: Int,
        newItems: List<T>,
    ) = getOldItems()[oldItemPosition].step == (newItems[newItemPosition] as StepInstructions).step

    override fun bindItem(binding: ItemInstructionsBinding, item: StepInstructions) {
        with(binding) {
            val step = root.context.resources.getString(R.string.step, item.step);
            instructionsDetails.text = item.description
            textViewStepInstructions.text = step
        }
    }


}