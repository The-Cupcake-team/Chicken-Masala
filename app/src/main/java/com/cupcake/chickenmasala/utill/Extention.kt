package com.cupcake.chickenmasala.utill

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.cupcake.chickenmasala.R
import com.cupcake.chickenmasala.data.data_sourse.DataSourceImpl
import com.cupcake.chickenmasala.data.model.Recipe

fun ImageView.setImage(url: String) {

    Glide.with(context).load(url)
        .thumbnail(Glide.with(context).load(R.raw.loading))
        .fitCenter()
        .centerCrop()
        .into(this)

}

fun Int.toFormatTime(): String{
    val hours = this/60
    val min = this % 60
    var formatTime = ""
    if (hours != 0){
        formatTime = "${hours}h "
    }
    if (min != 0){
        formatTime += "${min}m"
    }
    return formatTime
}