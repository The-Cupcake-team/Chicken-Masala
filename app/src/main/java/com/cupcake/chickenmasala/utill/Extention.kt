package com.cupcake.chickenmasala.utill

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.cupcake.chickenmasala.R
import com.cupcake.chickenmasala.data.model.HomeItem
import com.cupcake.chickenmasala.data.model.Recipe


fun ImageView.setImage(url: String) {

    Glide.with(context).load(url)
        .thumbnail(Glide.with(context).load(R.raw.loading))
        .fitCenter()
        .centerCrop()
        .into(this)

}

fun Recipe.toHomeItem(): HomeItem{
    return HomeItem.RecipeFood(this)
}


