package com.cupcake.chickenmasala.ui.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cupcake.chickenmasala.R
import com.cupcake.chickenmasala.databinding.InstructionsItemBinding


class InstructionsAdapter(private var list: List<String>): RecyclerView.Adapter<InstructionsAdapter.InstructionsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InstructionsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.instructions_item,parent,false)
        return InstructionsViewHolder(view)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: InstructionsViewHolder, position: Int) {
        val currentInstruction = list[position]
        holder.binding.apply {
            instructionsDetails.text = currentInstruction
        }
    }

    fun setData(newData: List<String>) {
        list = newData
        notifyDataSetChanged()
    }


    class InstructionsViewHolder(viewItem:View):RecyclerView.ViewHolder(viewItem){
        val binding = InstructionsItemBinding.bind(viewItem)
    }
}