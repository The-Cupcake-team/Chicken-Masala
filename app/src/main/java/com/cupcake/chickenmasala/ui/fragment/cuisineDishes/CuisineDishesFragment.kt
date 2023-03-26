package com.cupcake.chickenmasala.ui.fragment.cuisineDishes

import android.view.LayoutInflater
import android.view.ViewGroup
import com.cupcake.chickenmasala.databinding.FragmentCuisineDishesBinding
import com.cupcake.chickenmasala.ui.base.BaseFragment

class CuisineDishesFragment: BaseFragment<FragmentCuisineDishesBinding>() {
    override val LOG_TAG: String
        get() = "CUISINE_DISHES"
    override val bindingInflater: (LayoutInflater, ViewGroup, Boolean) -> FragmentCuisineDishesBinding
        get() = FragmentCuisineDishesBinding::inflate
}