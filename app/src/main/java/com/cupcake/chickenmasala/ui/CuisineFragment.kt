package com.cupcake.chickenmasala.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cupcake.chickenmasala.BaseFragment
import com.cupcake.chickenmasala.databinding.FragmentCuisineBinding


class CuisineFragment: BaseFragment<FragmentCuisineBinding>() {
    override val LOG_TAG: String
        get() = "CUISINE_FRAGMENT"
    override val bindingInflater: (LayoutInflater, ViewGroup, Boolean) -> FragmentCuisineBinding
        get() = FragmentCuisineBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}