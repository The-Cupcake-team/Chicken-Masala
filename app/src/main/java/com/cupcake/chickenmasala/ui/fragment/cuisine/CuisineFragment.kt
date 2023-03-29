package com.cupcake.chickenmasala.ui.fragment.cuisine

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cupcake.chickenmasala.R
import com.cupcake.chickenmasala.data.RepositoryImpl
import com.cupcake.chickenmasala.data.model.Cuisine
import com.cupcake.chickenmasala.databinding.FragmentCuisineBinding
import com.cupcake.chickenmasala.ui.base.BaseFragment
import com.cupcake.chickenmasala.ui.fragment.cuisine.adapter.CuisineAdapter
import com.cupcake.chickenmasala.ui.fragment.cuisineDishes.CuisineDishesFragment
import com.cupcake.chickenmasala.usecase.Repository
import com.cupcake.chickenmasala.usecase.cuisine.GetCuisineUseCase
import com.cupcake.chickenmasala.utill.DataSourceProvider

class CuisineFragment: BaseFragment<FragmentCuisineBinding>(), CuisineAdapter.CuisineInteractionListener {
    override val LOG_TAG: String = "CUISINE_FRAGMENT"
    override val bindingInflater: (LayoutInflater, ViewGroup, Boolean) -> FragmentCuisineBinding
            = FragmentCuisineBinding::inflate

    private val repository: Repository by lazy {
        RepositoryImpl(DataSourceProvider.getDataSource(requireActivity().application))
    }

    private lateinit var cuisines:List<Cuisine>
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpCuisineAdapter()
    }

    private fun setUpCuisineAdapter(){
        cuisines = GetCuisineUseCase(repository.getRecipes()).invoke()
        val adapter = CuisineAdapter(this)
        adapter.submitList(cuisines)
        binding.recyclerCuisine.adapter = adapter
    }

    override fun onClickCuisine(cuisine: Cuisine) {
        val cuisineDishesFragment = CuisineDishesFragment.newInstance(cuisine.key)
        val transition = requireActivity().supportFragmentManager.beginTransaction()
        transition.replace(R.id.fragmentContainer, cuisineDishesFragment)
        transition.addToBackStack(CuisineFragment().javaClass.simpleName)
        transition.commit()
    }
}
