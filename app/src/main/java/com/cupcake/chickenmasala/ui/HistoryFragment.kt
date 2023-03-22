package com.cupcake.chickenmasala.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import com.cupcake.chickenmasala.BaseFragment
import com.cupcake.chickenmasala.databinding.FragmentHistoryBinding

class HistoryFragment : BaseFragment<FragmentHistoryBinding>() {
    override val LOG_TAG: String
        get() = "HistoryFragment"

    override val bindingInflater: (LayoutInflater, ViewGroup, Boolean) -> FragmentHistoryBinding
        get() = FragmentHistoryBinding::inflate


}