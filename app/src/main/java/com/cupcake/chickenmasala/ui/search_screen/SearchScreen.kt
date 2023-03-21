package com.cupcake.chickenmasala.ui.search_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.cupcake.chickenmasala.BaseFragment
import com.cupcake.chickenmasala.databinding.FragmentSearchScreenBinding


class SearchScreen : BaseFragment<FragmentSearchScreenBinding>() {
    override val LOG_TAG = "SearchScreen"
    override val bindingInflater: (LayoutInflater, ViewGroup, Boolean) -> FragmentSearchScreenBinding =
        FragmentSearchScreenBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.searchBox.setEndIconOnClickListener {
           showFilterSheet()
        }
    }

    private fun showFilterSheet() {

    }

}