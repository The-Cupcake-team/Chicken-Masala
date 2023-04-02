package com.cupcake.chickenmasala.ui.fragment.details.adapter.viewholder

import com.cupcake.chickenmasala.data.model.StepInstructions
import com.cupcake.chickenmasala.databinding.ListStepInstructionsBinding
import com.cupcake.chickenmasala.ui.fragment.details.adapter.DetailsAdapter
import com.cupcake.chickenmasala.ui.fragment.details.adapter.InstructionsAdapter

class ListStepInstructionsViewHolder(val binding: ListStepInstructionsBinding) :
    DetailsAdapter.BaseDetailsViewHolder(binding) {
    override fun <T> bindItem(items: T) {
        val instructions = items as List<StepInstructions>
        val adapter = InstructionsAdapter()
        adapter.submitList(instructions)
        binding.recyclerInstructions.adapter = adapter
    }
}