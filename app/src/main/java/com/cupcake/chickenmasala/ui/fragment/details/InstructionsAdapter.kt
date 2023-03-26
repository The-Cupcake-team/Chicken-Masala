package com.cupcake.chickenmasala.ui.fragment.details

import android.view.LayoutInflater
import android.view.ViewGroup
import com.cupcake.chickenmasala.data.model.Recipe
import com.cupcake.chickenmasala.databinding.InstructionsItemBinding
import com.cupcake.chickenmasala.ui.base.BaseAdapter


class InstructionsAdapter:BaseAdapter<String,InstructionsItemBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> InstructionsItemBinding = InstructionsItemBinding::inflate

    override fun <T> areItemsTheSame(
        oldItemPosition: Int,
        newItemPosition: Int,
        newItems: List<T>,
    )= getOldItems()[oldItemPosition] == (newItems[newItemPosition] as Recipe).translatedRecipeName

    override fun bindItem(binding: InstructionsItemBinding, item: String) {
       with(binding){
           instructionsDetails.text = item
       }
    }


}