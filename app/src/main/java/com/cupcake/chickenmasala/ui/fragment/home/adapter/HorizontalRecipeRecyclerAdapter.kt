package com.cupcake.chickenmasala.ui.fragment.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.cupcake.chickenmasala.data.model.Recipe
import com.cupcake.chickenmasala.databinding.SearchCardViewBinding
import com.cupcake.chickenmasala.ui.base.BaseAdapter
import com.cupcake.chickenmasala.ui.fragment.home.HomeInteractorListener
import com.cupcake.chickenmasala.utill.setImage


class HorizontalRecipeRecyclerAdapter(private val listener: HomeInteractorListener) :
    BaseAdapter<Recipe, SearchCardViewBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) ->
    SearchCardViewBinding = SearchCardViewBinding::inflate

    override fun <T> areItemsTheSame(
        oldItemPosition: Int,
        newItemPosition: Int,
        newItems: List<T>
    ) = getOldItems()[oldItemPosition].id == (newItems[newItemPosition] as Recipe).id

    override fun bindItem(binding: SearchCardViewBinding, item: Recipe) {
        with(binding) {
            saveCardImage.setImage(item.imageUrl)
            dishName.text = item.translatedRecipeName
            textViewCuisineName.text = item.cuisine
            prepareTime.text = item.formattedTime
            root.setOnClickListener{
                listener.onRecipeCardClicked(item.id)
            }
        }
    }


}