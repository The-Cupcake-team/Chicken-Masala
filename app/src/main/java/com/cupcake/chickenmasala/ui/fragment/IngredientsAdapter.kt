package com.cupcake.chickenmasala.ui.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cupcake.chickenmasala.R
import com.cupcake.chickenmasala.databinding.IngredientsItemBinding

class IngredientsAdapter():RecyclerView.Adapter<IngredientsAdapter.IngredientsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.ingredients_item,parent,false)
        return IngredientsViewHolder(view)
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: IngredientsViewHolder, position: Int) {
        TODO("Not yet implemented")
    }


    class IngredientsViewHolder(viewItem: View): RecyclerView.ViewHolder(viewItem){
        val binding = IngredientsItemBinding.bind(viewItem)
    }
}