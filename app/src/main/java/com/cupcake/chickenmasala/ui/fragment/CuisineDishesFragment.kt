package com.cupcake.chickenmasala.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cupcake.chickenmasala.databinding.FragmentCuisineDishesBinding
import com.cupcake.chickenmasala.ui.base.BaseFragment
import com.cupcake.chickenmasala.utill.RepositoryProvider

class CuisineDishesFragment: BaseFragment<FragmentCuisineDishesBinding>() {
    override val LOG_TAG: String
        get() = "CUISINE_DISHES"
    override val bindingInflater: (LayoutInflater, ViewGroup, Boolean) -> FragmentCuisineDishesBinding
        get() = FragmentCuisineDishesBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Don't make this instance of repo as a global variable
        // just pass the repo to the functions that need repo
        // Don't forget delete this comment
        val repository = RepositoryProvider.getInstance(requireActivity().application).getRepo()
    }
}