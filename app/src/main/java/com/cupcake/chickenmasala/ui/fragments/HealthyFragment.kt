package com.cupcake.chickenmasala.ui.fragments

import android.view.LayoutInflater
import android.view.ViewGroup
import com.cupcake.chickenmasala.BaseFragment
import com.cupcake.chickenmasala.databinding.FragmentHealthyBinding

class HealthyFragment : BaseFragment<FragmentHealthyBinding>() {
    override val LOG_TAG = "HealthFragment"

    override val bindingInflater: (LayoutInflater, ViewGroup, Boolean) -> FragmentHealthyBinding =
        FragmentHealthyBinding::inflate
}