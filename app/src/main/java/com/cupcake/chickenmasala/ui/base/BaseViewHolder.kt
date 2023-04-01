package com.cupcake.chickenmasala.ui.base

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseViewHolder(viewItem: ViewBinding) :
    RecyclerView.ViewHolder(viewItem.root)