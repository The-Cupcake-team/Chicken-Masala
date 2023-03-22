package com.cupcake.chickenmasala.ui.details_screen

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cupcake.chickenmasala.BaseFragment
import com.cupcake.chickenmasala.R
import com.cupcake.chickenmasala.databinding.FragmentDetailsBinding

class DetailsFragment :BaseFragment<FragmentDetailsBinding>(){

    override val LOG_TAG: String = "Details_Fragment"

    override val bindingInflater: (LayoutInflater, ViewGroup, Boolean) ->
    FragmentDetailsBinding = FragmentDetailsBinding::inflate


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        openWebsite("www.google.com")
    }

    private fun openWebsite(url:String){
        binding.moreDetailsButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)
        }
    }
}