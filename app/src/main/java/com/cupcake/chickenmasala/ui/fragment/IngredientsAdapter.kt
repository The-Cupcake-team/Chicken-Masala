package com.cupcake.chickenmasala.ui.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cupcake.chickenmasala.R
import com.cupcake.chickenmasala.data.model.Recipe
import com.cupcake.chickenmasala.databinding.IngredientsItemBinding

    class IngredientsAdapter(private var list: List<String>):RecyclerView.Adapter<IngredientsAdapter.IngredientsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.ingredients_item,parent,false)
        return IngredientsViewHolder(view)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: IngredientsViewHolder, position: Int) {
        val currentIngredient = list[position]
        holder.binding.apply {
               ingredientsName.text = currentIngredient
        }
    }

    fun setData(newData: List<String>) {
        list = newData
        notifyDataSetChanged()
    }


    class IngredientsViewHolder(viewItem: View): RecyclerView.ViewHolder(viewItem){
        val binding = IngredientsItemBinding.bind(viewItem)
    }
}