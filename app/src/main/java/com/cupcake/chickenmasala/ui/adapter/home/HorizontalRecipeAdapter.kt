package com.cupcake.chickenmasala.ui.adapter.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cupcake.chickenmasala.R
import com.cupcake.chickenmasala.data.model.Recipe
import com.cupcake.chickenmasala.databinding.SearchCardViewBinding

class HorizontalRecipeAdapter(private var recipeList: List<Recipe>): RecyclerView.Adapter<HorizontalRecipeAdapter.HomeViewHolder>() {

    class HomeViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val binding = SearchCardViewBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.search_card_view,parent,false)
        return HomeViewHolder(view)
    }

    override fun getItemCount() = recipeList.size

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.binding.apply {
            dishName.text = recipeList[position].translatedRecipeName
            cuisineName.text = recipeList[position].cuisine
            prepareTime.text = recipeList[position].totalTimeInMin.toString()
            Glide.with(holder.itemView.context).load(recipeList[position].imageUrl).into(saveCardImage)
        }
    }

    fun setData(newRecipeList: List<Recipe>){
        recipeList = newRecipeList
        notifyDataSetChanged()
    }


}