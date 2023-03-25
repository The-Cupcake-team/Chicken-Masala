package com.cupcake.chickenmasala.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cupcake.chickenmasala.ui.base.BaseFragment
import com.cupcake.chickenmasala.databinding.FragmentHistoryBinding
import com.cupcake.chickenmasala.utill.RepositoryProvider

class HistoryFragment : BaseFragment<FragmentHistoryBinding>() {
    override val LOG_TAG: String
        get() = "HistoryFragment"

    override val bindingInflater: (LayoutInflater, ViewGroup, Boolean) -> FragmentHistoryBinding
        get() = FragmentHistoryBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Don't make this instance of repo as a global variable
        // just pass the repo to the functions that need repo
        // Don't forget delete this comment
        val repository = RepositoryProvider.getInstance(requireActivity().application).getRepo()
    }
}