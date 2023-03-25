package com.cupcake.chickenmasala.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cupcake.chickenmasala.ui.base.BaseFragment
import com.cupcake.chickenmasala.databinding.FragmentCuisineBinding
import com.cupcake.chickenmasala.utill.RepositoryProvider


class CuisineFragment: BaseFragment<FragmentCuisineBinding>() {
    override val LOG_TAG: String
        get() = "CUISINE_FRAGMENT"
    override val bindingInflater: (LayoutInflater, ViewGroup, Boolean) -> FragmentCuisineBinding
        get() = FragmentCuisineBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Don't make this instance of repo as a global variable
        // just pass the repo to the functions that need repo
        // Don't forget delete this comment
        val repository = RepositoryProvider.getInstance(requireActivity().application).getRepo()
    }
}