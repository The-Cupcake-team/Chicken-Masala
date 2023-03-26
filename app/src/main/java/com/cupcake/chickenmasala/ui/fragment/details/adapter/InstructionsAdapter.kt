package com.cupcake.chickenmasala.ui.fragment.details.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.cupcake.chickenmasala.data.model.Recipe
import com.cupcake.chickenmasala.databinding.ItemInstructionsBinding
import com.cupcake.chickenmasala.ui.base.BaseAdapter


class InstructionsAdapter:BaseAdapter<String,ItemInstructionsBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> ItemInstructionsBinding = ItemInstructionsBinding::inflate

    override fun <T> areItemsTheSame(
        oldItemPosition: Int,
        newItemPosition: Int,
        newItems: List<T>,
    )= getOldItems()[oldItemPosition] == (newItems[newItemPosition] as Recipe).translatedRecipeName

    override fun bindItem(binding: ItemInstructionsBinding, item: String) {
       with(binding){
           instructionsDetails.text = item
       }
    }


}