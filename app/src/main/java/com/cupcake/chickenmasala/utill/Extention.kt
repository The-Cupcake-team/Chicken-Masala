package com.cupcake.chickenmasala.utill

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import com.airbnb.lottie.LottieAnimationView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

fun ImageView.loadImage(url: String?, lottieView: LottieAnimationView?) {

    lottieView?.visibility = View.VISIBLE

    val glide = Glide
        .with(context)
        .load(url)

    val listener = object : RequestListener<Drawable> {

        override fun onLoadFailed(
            e: GlideException?,
            model: Any?,
            target: Target<Drawable>?,
            isFirstResource: Boolean
        ): Boolean {
            return false
        }

        override fun onResourceReady(
            resource: Drawable?,
            model: Any?,
            target: Target<Drawable>?,
            dataSource: DataSource?,
            isFirstResource: Boolean
        ): Boolean {
            lottieView?.visibility = View.GONE
            lottieView?.cancelAnimation()
            return true
        }
    }

    glide.listener(listener).into(this)
}
