package com.cupcake.chickenmasala.ui.fragment

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cupcake.chickenmasala.ui.base.BaseFragment
import com.cupcake.chickenmasala.databinding.FragmentCuisineBinding
import com.cupcake.chickenmasala.ui.CuisineAdapter
import com.cupcake.chickenmasala.ui.activity.HomeActivity
import com.cupcake.chickenmasala.usecase.GetCuisineUseCase


class CuisineFragment: BaseFragment<FragmentCuisineBinding>() {
    override val LOG_TAG: String
        get() = "CUISINE_FRAGMENT"
    override val bindingInflater: (LayoutInflater, ViewGroup, Boolean) -> FragmentCuisineBinding
        get() = FragmentCuisineBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val dataManager = (activity as HomeActivity).dataManager

        val cuisines = GetCuisineUseCase(dataManager.getRecipes()).invoke()
        val adapter = CuisineAdapter(cuisines)
        binding.recyclerCuisine.adapter = adapter
    }
}