package com.cupcake.chickenmasala.ui.fragment.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cupcake.chickenmasala.data.RepositoryImpl
import com.cupcake.chickenmasala.databinding.FragmentHistoryBinding
import com.cupcake.chickenmasala.ui.base.BaseFragment
import com.cupcake.chickenmasala.usecase.Repository
import com.cupcake.chickenmasala.utill.DataSourceProvider
import com.cupcake.chickenmasala.utill.setImage

class HistoryFragment : BaseFragment<FragmentHistoryBinding>() {
    override val LOG_TAG: String = "HistoryFragment"

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHistoryBinding
        get() = FragmentHistoryBinding::inflate

    private val repository: Repository by lazy {
        RepositoryImpl(DataSourceProvider.getDataSource(requireActivity().application))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()

    }

    private fun initView() {
        val imagesUrl = repository.getHistoryImagesUrl()
        with(binding) {
            imageHistoryOne.setImage(imagesUrl[0])
            imageHistoryTwo.setImage(imagesUrl[1])
            imageHistoryThree.setImage(imagesUrl[2])
        }
    }

}