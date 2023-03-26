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
import com.cupcake.chickenmasala.utill.Constant
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
        initView()

    }

    private fun initView() {
        val adviceID = arguments?.getInt(Constant.Key.ADVICE_ID)
        adviceID?.let {
            getHealthAdviceById(adviceID).apply {
                bindViews(this)
            }
        }
    }

    private fun getHealthAdviceById(adviceID: Int): HealthAdvice {
        return GetHealthyAdviceByIDUseCase(repository).invoke(adviceID)
    }

    private fun bindViews(healthyAdvice: HealthAdvice) {
        with(binding) {
            toolbar.title = healthyAdvice.title
            healthyImage.setImage(healthyAdvice.imageUrl)
            healthyDescription.text = healthyAdvice.description
        }
    }

}