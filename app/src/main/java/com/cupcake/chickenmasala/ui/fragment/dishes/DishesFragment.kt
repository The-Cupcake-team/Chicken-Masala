package com.cupcake.chickenmasala.ui.fragment.dishes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cupcake.chickenmasala.R
import com.cupcake.chickenmasala.data.RepositoryImpl
import com.cupcake.chickenmasala.data.model.Recipe
import com.cupcake.chickenmasala.databinding.FragmentDishesBinding
import com.cupcake.chickenmasala.ui.base.BaseFragment
import com.cupcake.chickenmasala.ui.fragment.details.DetailsFragment
import com.cupcake.chickenmasala.ui.fragment.dishes.adapter.DishesAdapter
import com.cupcake.chickenmasala.ui.util.OnItemClickListener
import com.cupcake.chickenmasala.usecase.Repository
import com.cupcake.chickenmasala.usecase.dishes.GetAllRecipesFoodUseCase
import com.cupcake.chickenmasala.utill.DataSourceProvider

class DishesFragment : BaseFragment<FragmentDishesBinding>(), OnItemClickListener<Recipe> {
    override val LOG_TAG = this::class.java.name

    override val bindingInflater: (LayoutInflater, ViewGroup, Boolean) -> FragmentDishesBinding =
        FragmentDishesBinding::inflate

    private val repository: Repository by lazy {
        RepositoryImpl(DataSourceProvider.getDataSource(requireActivity().application))
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupBackButton()
        setUpDishesAdapter()
    }

    private fun setUpDishesAdapter() {
        val dishes = GetAllRecipesFoodUseCase(repository).invoke(DISHES_LIMIT)
        val adapter = DishesAdapter(this)
        adapter.submitList(dishes)
        binding.recyclerViewDishes.adapter = adapter
    }

    override fun onItemClicked(item: Recipe) {
        val detailsFragment = DetailsFragment.newInstance(item.id)
        requireActivity().supportFragmentManager.beginTransaction().apply {
            add(R.id.fragmentContainer, detailsFragment)
            addToBackStack(detailsFragment.javaClass.simpleName)
            commit()
        }
    }

    private fun setupBackButton() {
        binding.toolbar.setNavigationOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
    }

    private companion object {
        const val DISHES_LIMIT = 100
    }
}
