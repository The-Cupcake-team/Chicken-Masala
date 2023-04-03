package com.cupcake.chickenmasala.utill

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.cupcake.chickenmasala.R
import com.cupcake.chickenmasala.data.model.Recipe
import com.cupcake.chickenmasala.data.model.StepInstructions
import com.cupcake.chickenmasala.ui.fragment.details.adapter.DetailsItem
import com.cupcake.chickenmasala.ui.fragment.details.adapter.DetailsItemType
import com.cupcake.chickenmasala.ui.fragment.home.homeModel.HomeItem
import com.cupcake.chickenmasala.ui.fragment.home.homeModel.HomeItemType

fun ImageView.setImage(url: String) {

    Glide.with(context).load(url)
        .thumbnail(Glide.with(context).load(R.raw.loading))
        .fitCenter()
        .centerCrop()
        .into(this)

}

fun Recipe.toHomeItem(): HomeItem<Any>{
    return HomeItem(this,HomeItemType.VERTICAL_RECYCLER)
}

fun String.toIngredientItem(): DetailsItem<Any>{
    return DetailsItem(this,DetailsItemType.STEP_INGREDIENTS)
}

fun StepInstructions.toInstructionItem(): DetailsItem<Any>{
    return DetailsItem(this,DetailsItemType.STEP_INSTRUCTIONS)
}



