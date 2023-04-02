package com.cupcake.chickenmasala.ui.fragment.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.cupcake.chickenmasala.R
import com.cupcake.chickenmasala.data.RepositoryImpl
import com.cupcake.chickenmasala.data.model.Recipe
import com.cupcake.chickenmasala.databinding.FragmentHomeBinding
import com.cupcake.chickenmasala.ui.base.BaseFragment
import com.cupcake.chickenmasala.ui.fragment.details.DetailsFragment
import com.cupcake.chickenmasala.ui.fragment.dishes.DishesFragment
import com.cupcake.chickenmasala.ui.fragment.health.HealthyFragment
import com.cupcake.chickenmasala.ui.fragment.home.adapter.HomeRecyclerAdapter
import com.cupcake.chickenmasala.ui.fragment.home.homeModel.HomeItem
import com.cupcake.chickenmasala.ui.fragment.home.homeModel.HomeItemType
import com.cupcake.chickenmasala.usecase.Repository
import com.cupcake.chickenmasala.usecase.home.GetAllFoodUseCase
import com.cupcake.chickenmasala.usecase.home.GetFilteredFoodUseCase
import com.cupcake.chickenmasala.usecase.home.GetHealthAdvicesUseCase
import com.cupcake.chickenmasala.usecase.home.GetRecentFoodUseCase
import com.cupcake.chickenmasala.utill.DataSourceProvider
import com.cupcake.chickenmasala.utill.toHomeItem


class HomeFragment : BaseFragment<FragmentHomeBinding>(), HomeInteractorListener {
    override val LOG_TAG: String = this::class.java.name

    override val bindingInflater: (LayoutInflater, ViewGroup, Boolean) -> FragmentHomeBinding
      = FragmentHomeBinding::inflate

    private lateinit var homeRecyclerAdapter: HomeRecyclerAdapter
    private var itemsList: MutableList<HomeItem<Any>> = mutableListOf()

    private val repository: Repository by lazy {
        RepositoryImpl(DataSourceProvider.getDataSource(requireActivity().application))
    }
    private val allRecipesUseCase : GetAllFoodUseCase by  lazy {
        GetAllFoodUseCase(repository)
    }
    private val filteredRecipesUseCase : GetFilteredFoodUseCase by  lazy {
        GetFilteredFoodUseCase(repository)
    }
    private val recentRecipesUseCase : GetRecentFoodUseCase by  lazy {
        GetRecentFoodUseCase(repository)
    }
    private val healthyAdvicesUseCase : GetHealthAdvicesUseCase by  lazy {
        GetHealthAdvicesUseCase(repository)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup()
    }
    private fun setup(){
        setupViewPager()
        setupRecentFoodRecycler()
        setupChips()
        setupFilteredFoodRecycler()
        homeRecyclerAdapter = HomeRecyclerAdapter(this,itemsList)
        binding.recyclerViewHome.adapter = homeRecyclerAdapter
    }

    private fun setupViewPager(){
        val advices = healthyAdvicesUseCase(ADVICES_LIMIT)
        itemsList.add(HomeItem(advices, HomeItemType.HEALTHY_VIEW_PAGER))
    }
    private fun setupRecentFoodRecycler(){
        val recentFood = recentRecipesUseCase(RECENT_FOOD_LIMIT)
        itemsList.add(HomeItem(recentFood, HomeItemType.HORIZONTAL_RECYCLER))
    }
    private fun setupFilteredFoodRecycler(){
        val data = allRecipesUseCase()
        itemsList.addAll(data.map { it.toHomeItem() })
    }
    private fun setupChips(){
        itemsList.add(HomeItem(emptyList<String>(), HomeItemType.CHIPS_FILTERED))
    }

    override fun onViewPagerClicked(id: Int) {
        val healthyFragment = HealthyFragment.newInstance(id)
        navigateToFragment(healthyFragment)
    }
    override fun onViewAllButtonClicked() {
        val dishesFragment = DishesFragment()
        navigateToFragment(dishesFragment)
    }
    override fun onRecipeCardClicked(id: Int) {
        val detailsFragment = DetailsFragment.newInstance(id)
        navigateToFragment(detailsFragment)
    }
    override fun onChipClicked(chipId: Int) {
        val data: List<Recipe> = if(chipId == ALL_RECIPES_ID){
            allRecipesUseCase()
        }else{
            val cuisine = getCuisineAccordingToChipId(chipId)
            filteredRecipesUseCase(cuisine)
        }
        updateList(data)
    }

    private fun updateList(data:List<Recipe>){
        itemsList = itemsList.take(3).toMutableList()
        itemsList.addAll(data.map { it.toHomeItem() })
        homeRecyclerAdapter.submitList(itemsList)
    }
    private fun getCuisineAccordingToChipId(chipId: Int): String{
        return when(chipId){
            WORLD_BREAKFAST_ID -> WORLD_BREAKFAST_TEXT
            DESSERT_ID ->  DESSERT_TEXT
            DINNER_ID ->  DINNER_TEXT
            SNACKS_ID -> SNACKS_TEXT
            else -> ALL_RECIPES_TEXT
        }
    }
    private fun navigateToFragment(fragment: Fragment) {
        requireActivity().supportFragmentManager.beginTransaction().apply {
            add(R.id.fragmentContainer, fragment)
            addToBackStack(fragment.javaClass.simpleName)
            commit()
        }
    }

    private companion object {
        const val RECENT_FOOD_LIMIT = 10

        const val ADVICES_LIMIT = 7

        const val ALL_RECIPES_ID = R.id.chip_all_recipes
        const val WORLD_BREAKFAST_ID=  R.id.chip_breakfast
        const val DESSERT_ID = R.id.chip_desert
        const val DINNER_ID = R.id.chip_dinner
        const val SNACKS_ID = R.id.chip_snack

        const val ALL_RECIPES_TEXT = "All Recipes"
        const val WORLD_BREAKFAST_TEXT=  "World Breakfast"
        const val DESSERT_TEXT = "Dessert"
        const val DINNER_TEXT = "Dinner"
        const val SNACKS_TEXT = "Snack"
    }
}


