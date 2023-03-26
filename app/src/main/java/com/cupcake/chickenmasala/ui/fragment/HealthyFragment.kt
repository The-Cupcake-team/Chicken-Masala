package com.cupcake.chickenmasala.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cupcake.chickenmasala.data.RepositoryImpl
import com.cupcake.chickenmasala.data.model.HealthAdvice
import com.cupcake.chickenmasala.databinding.FragmentHealthyBinding
import com.cupcake.chickenmasala.ui.base.BaseFragment
import com.cupcake.chickenmasala.usecase.Repository
import com.cupcake.chickenmasala.usecase.healthy.GetHealthyAdviceByIDUseCase
import com.cupcake.chickenmasala.utill.DataSourceProvider
import com.cupcake.chickenmasala.utill.setImage

class HealthyFragment : BaseFragment<FragmentHealthyBinding>() {
    override val LOG_TAG = "HealthFragment"

    override val bindingInflater: (LayoutInflater, ViewGroup, Boolean) -> FragmentHealthyBinding =
        FragmentHealthyBinding::inflate

    private val repository: Repository by lazy {
        RepositoryImpl(DataSourceProvider.getDataSource(requireActivity().application))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adviceID = arguments?.getInt("adviceID")
        adviceID?.let {

        }
        initialData(1)

    }

    private fun initialData(adviceID: Int) {
        val advice = GetHealthyAdviceByIDUseCase(repository).invoke(adviceID)
        bindViews(advice)
    }

    private fun bindViews(healthyAdvice: HealthAdvice) {
        binding.toolbar.title = healthyAdvice.title
        binding.healthyImage.setImage(healthyAdvice.imageUrl)
        binding.healthyDescription.text = healthyAdvice.description
    }
}