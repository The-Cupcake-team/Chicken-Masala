package com.cupcake.chickenmasala.ui.fragment.cuisineDishes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cupcake.chickenmasala.R
import com.cupcake.chickenmasala.data.RepositoryImpl
import com.cupcake.chickenmasala.data.model.Recipe
import com.cupcake.chickenmasala.databinding.FragmentCuisineDishesBinding
import com.cupcake.chickenmasala.ui.base.BaseFragment
import com.cupcake.chickenmasala.ui.fragment.cuisineDishes.adapter.CuisineDishesAdapter
import com.cupcake.chickenmasala.ui.fragment.details.DetailsFragment
import com.cupcake.chickenmasala.usecase.Repository
import com.cupcake.chickenmasala.usecase.cuisine.GetAllRecipesInCuisineUseCase
import com.cupcake.chickenmasala.utill.DataSourceProvider

class CuisineDishesFragment : BaseFragment<FragmentCuisineDishesBinding>(),
    CuisineDishesAdapter.CuisineDishesInteractionListener {

    override val LOG_TAG: String = "CUISINE_DISHES"
    override val bindingInflater: (LayoutInflater, ViewGroup, Boolean) -> FragmentCuisineDishesBinding =
        FragmentCuisineDishesBinding::inflate

    private val repository: Repository by lazy {
        RepositoryImpl(DataSourceProvider.getDataSource(requireActivity().application))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        setupBackButton()
    }

    private fun initView() {
        val cuisineDishes = arguments.let { it?.getString(CUISINE_DISHES) }
        binding.toolbar.title = cuisineDishes
        cuisineDishes?.let {
            initCuisineDishesAdapter(cuisineDishes)
        }
    }

    private fun setupBackButton() {
        binding.toolbar.setNavigationOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
    }

    private fun initCuisineDishesAdapter(cuisineName: String) {
        val cuisineDishes = getCuisineDishes(cuisineName)
        val adapter = CuisineDishesAdapter(this)
        adapter.submitList(cuisineDishes)
        binding.recyclerView.adapter = adapter
    }

    private fun getCuisineDishes(cuisineName: String): List<Recipe> {
        return GetAllRecipesInCuisineUseCase(repository).invoke(cuisineName)
    }

    override fun onClickCuisineDishes(recipe: Recipe) {
        val detailsFragment = DetailsFragment.newInstance(recipe.id)
        val transition = requireActivity().supportFragmentManager.beginTransaction()
        transition.replace(R.id.fragmentContainer, detailsFragment)
        transition.addToBackStack(DetailsFragment().javaClass.simpleName)
        transition.commit()
    }

    companion object {
        fun newInstance(cuisineName: String) = CuisineDishesFragment().apply {
            arguments = Bundle().apply {
                putString(CUISINE_DISHES, cuisineName)
            }
        }

        private const val CUISINE_DISHES = "cuisineName"
    }
}