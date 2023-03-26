package com.cupcake.chickenmasala.utill

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.cupcake.chickenmasala.R

fun ImageView.setImage(url: String) {

    Glide.with(context).load(url)
        .thumbnail(Glide.with(context).load(R.raw.loading))
        .fitCenter()
        .centerCrop()
        .into(this);

}
