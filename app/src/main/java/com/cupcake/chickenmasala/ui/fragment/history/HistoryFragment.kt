package com.cupcake.chickenmasala.ui.fragment.history

import android.view.LayoutInflater
import android.view.ViewGroup
import com.cupcake.chickenmasala.ui.base.BaseFragment
import com.cupcake.chickenmasala.databinding.FragmentHistoryBinding

class HistoryFragment : BaseFragment<FragmentHistoryBinding>() {
    override val LOG_TAG: String
        get() = "HistoryFragment"

    override val bindingInflater: (LayoutInflater, ViewGroup, Boolean) -> FragmentHistoryBinding
        get() = FragmentHistoryBinding::inflate


}