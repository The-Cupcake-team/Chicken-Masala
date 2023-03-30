package com.cupcake.chickenmasala.ui.fragment.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.cupcake.chickenmasala.R
import com.cupcake.chickenmasala.data.RepositoryImpl
import com.cupcake.chickenmasala.databinding.FragmentHomeBinding
import com.cupcake.chickenmasala.ui.base.BaseFragment
import com.cupcake.chickenmasala.ui.fragment.details.DetailsFragment
import com.cupcake.chickenmasala.ui.fragment.dishes.DishesFragment
import com.cupcake.chickenmasala.ui.fragment.health.HealthyFragment
import com.cupcake.chickenmasala.ui.fragment.home.adapter.HomeRecyclerAdapter
import com.cupcake.chickenmasala.ui.fragment.home.enums.HomeItem
import com.cupcake.chickenmasala.ui.fragment.home.enums.HomeItemType
import com.cupcake.chickenmasala.usecase.Repository
import com.cupcake.chickenmasala.usecase.home.GetFilteredFoodUseCase
import com.cupcake.chickenmasala.usecase.home.GetHealthAdvicesUseCase
import com.cupcake.chickenmasala.usecase.home.GetRecentFoodUseCase
import com.cupcake.chickenmasala.usecase.home.HomeInteractorListener
import com.cupcake.chickenmasala.utill.DataSourceProvider
import com.cupcake.chickenmasala.utill.toHomeItem
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup


class HomeFragment : BaseFragment<FragmentHomeBinding>(), HomeInteractorListener {
    override val LOG_TAG: String = this::class.java.name

    override val bindingInflater: (LayoutInflater, ViewGroup, Boolean) -> FragmentHomeBinding
        get() = FragmentHomeBinding::inflate

    private lateinit var homeRecyclerAdapter: HomeRecyclerAdapter
    private var itemsList: MutableList<HomeItem<Any>> = mutableListOf()

    private val repository: Repository by lazy {
        RepositoryImpl(DataSourceProvider.getDataSource(requireActivity().application))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup()
    }

    private fun setup(){
        val advices = GetHealthAdvicesUseCase(repository)(ADVICES_LIMIT)
        itemsList.add(HomeItem(advices,HomeItemType.HEALTHY_VIEW_PAGER))

        val recentFood = GetRecentFoodUseCase(repository)(RECENT_FOOD_LIMIT)
        itemsList.add(HomeItem(recentFood,HomeItemType.HORIZONTAL_RECYCLER))

        itemsList.add(HomeItem(emptyList<String>(),HomeItemType.CHIPS_FILTERED))

        val allRecipes = repository.getRecipes().shuffled()
        itemsList.addAll(allRecipes.map { it.toHomeItem() })

        homeRecyclerAdapter = HomeRecyclerAdapter(this,itemsList)
        binding.recyclerViewHome.adapter = homeRecyclerAdapter
    }
    override fun onViewPagerClicked(id: Int) {
        val healthyFragment = HealthyFragment.newInstance(id)
        navigateToFragment(healthyFragment)
    }

    override fun onViewAllButtonClicked() {
        val dishesFragment = DishesFragment()
        navigateToFragment(dishesFragment)
    }

    override fun onCardClicked(id: Int) {
        val detailsFragment = DetailsFragment.newInstance(id)
        navigateToFragment(detailsFragment)
    }

    override fun onChipClicked(group: ChipGroup, chipId: Int) {
        val chip = group.findViewById<Chip>(chipId)
        if(chip.text.toString() == ALL_RECIPES){
            val allRecipes = repository.getRecipes().shuffled()
            itemsList = itemsList.take(3).toMutableList()
            itemsList.addAll(
                allRecipes.map { it.toHomeItem() }
            )
            homeRecyclerAdapter.submitList(itemsList)
        }else{
            val data = GetFilteredFoodUseCase(repository)(chip.text.toString())
            itemsList = itemsList.take(3).toMutableList()
            itemsList.addAll(
                data.map { it.toHomeItem() }
            )
            homeRecyclerAdapter.submitList(itemsList)
        }
    }

    private fun navigateToFragment(fragment: Fragment) {
        requireActivity().supportFragmentManager.beginTransaction().apply {
            add(R.id.fragmentContainer, fragment)
            addToBackStack(fragment.javaClass.simpleName)
            commit()
        }
    }
    companion object {
        const val RECENT_FOOD_LIMIT = 10
        const val ADVICES_LIMIT = 7
        const val ALL_RECIPES = "All recipes"
    }
}


