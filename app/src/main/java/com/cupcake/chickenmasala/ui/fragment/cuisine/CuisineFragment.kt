package com.cupcake.chickenmasala.ui.fragment.cuisine

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.cupcake.chickenmasala.data.RepositoryImpl
import com.cupcake.chickenmasala.data.model.Recipe
import com.cupcake.chickenmasala.ui.base.BaseFragment
import com.cupcake.chickenmasala.databinding.FragmentCuisineBinding
import com.cupcake.chickenmasala.ui.base.OnItemClickListener
import com.cupcake.chickenmasala.usecase.Repository
import com.cupcake.chickenmasala.utill.DataSourceProvider


class CuisineFragment : BaseFragment<FragmentCuisineBinding>(), OnItemClickListener<Recipe> {
    override val LOG_TAG: String = this::class.java.name
    override val bindingInflater: (LayoutInflater, ViewGroup, Boolean) -> FragmentCuisineBinding get() = FragmentCuisineBinding::inflate

    private val repository: Repository by lazy {
        RepositoryImpl(DataSourceProvider.getDataSource(requireActivity().application))
    }

    private lateinit var cuisineAdapter: CuisineAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cuisineAdapter = CuisineAdapter(this)
        cuisineAdapter.submitList(repository.getRecipes())
        binding.recyclerCuisine.adapter = cuisineAdapter
    }

    override fun onItemClicked(item: Recipe) {
        Toast.makeText(requireContext(), item.translatedRecipeName, Toast.LENGTH_SHORT).show()
    }
}