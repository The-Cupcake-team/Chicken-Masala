package com.cupcake.chickenmasala.ui.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cupcake.chickenmasala.databinding.FragmentDetailsBinding
import com.cupcake.chickenmasala.ui.base.BaseFragment

class DetailsFragment : BaseFragment<FragmentDetailsBinding>(){

    override val LOG_TAG: String = "Details_Fragment"

    override val bindingInflater: (LayoutInflater, ViewGroup, Boolean) ->
    FragmentDetailsBinding = FragmentDetailsBinding::inflate


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        openWebsite("https://www.google.com")
    }

    private fun openWebsite(url:String){
        binding.moreDetailsButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            activity?.startActivity(intent)
        }
    }
}