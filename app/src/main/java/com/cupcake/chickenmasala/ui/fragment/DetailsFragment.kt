package com.cupcake.chickenmasala.ui.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.cupcake.chickenmasala.databinding.FragmentDetailsBinding
import com.cupcake.chickenmasala.ui.base.BaseFragment

class DetailsFragment : BaseFragment<FragmentDetailsBinding>(){

    override val LOG_TAG: String = "Details_Fragment"

    override val bindingInflater: (LayoutInflater, ViewGroup, Boolean) ->
    FragmentDetailsBinding = FragmentDetailsBinding::inflate


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadImage("https://lh3.googleusercontent.com/a/AGNmyxbs_jbOwkerX22XlUaYSh65EkcM4KeVPMgF5LJ0qw=s360")
        openWebsite("https://www.google.com")
    }

    private fun openWebsite(url:String){
        binding.moreDetailsButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            activity?.startActivity(intent)
        }
    }

    private fun loadImage(imageUrl:String){
        val includedLayout = binding.p1
        val imageView = includedLayout.detailsImage
        Glide.with(this)
            .load(imageUrl)
            .into(imageView)
    }
}