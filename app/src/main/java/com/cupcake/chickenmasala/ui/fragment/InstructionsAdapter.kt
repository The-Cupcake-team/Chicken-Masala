package com.cupcake.chickenmasala.ui.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cupcake.chickenmasala.R
import com.cupcake.chickenmasala.databinding.InstructionsItemBinding


class InstructionsAdapter(): RecyclerView.Adapter<InstructionsAdapter.InstructionsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InstructionsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.instructions_item,parent,false)
        return InstructionsViewHolder(view)
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: InstructionsViewHolder, position: Int) {
        TODO("Not yet implemented")
    }


    class InstructionsViewHolder(viewItem:View):RecyclerView.ViewHolder(viewItem){
        val binding = InstructionsItemBinding.bind(viewItem)
    }
}