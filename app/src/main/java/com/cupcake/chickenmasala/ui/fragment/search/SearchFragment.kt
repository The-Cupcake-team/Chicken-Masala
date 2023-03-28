package com.cupcake.chickenmasala.ui.fragment.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cupcake.chickenmasala.R
import com.cupcake.chickenmasala.data.RepositoryImpl
import com.cupcake.chickenmasala.data.dataSource.DataSource
import com.cupcake.chickenmasala.data.model.Recipe
import com.cupcake.chickenmasala.databinding.FilterBottomSheetBinding
import com.cupcake.chickenmasala.databinding.FragmentSearchBinding
import com.cupcake.chickenmasala.ui.fragment.search.adapter.RecipeClickListener
import com.cupcake.chickenmasala.ui.fragment.search.adapter.SearchAdapter
import com.cupcake.chickenmasala.ui.base.BaseFragment
import com.cupcake.chickenmasala.usecase.Repository
import com.cupcake.chickenmasala.ui.fragment.details.DetailsFragment
import com.cupcake.chickenmasala.usecase.core.GetRandomRecipesUseCase
import com.cupcake.chickenmasala.usecase.search.SearchQuery
import com.cupcake.chickenmasala.usecase.search.SearchUseCase
import com.cupcake.chickenmasala.utill.DataSourceProvider
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.util.Timer
import java.util.TimerTask

class SearchFragment : BaseFragment<FragmentSearchBinding>(), TextWatcher, RecipeClickListener {
    override val LOG_TAG = "SearchScreen"
    override val bindingInflater: (LayoutInflater, ViewGroup, Boolean) -> FragmentSearchBinding =
        FragmentSearchBinding::inflate

    private var timerForDelaySearch: Timer? = null
    private var searchQuery: SearchQuery = SearchQuery()

    private val dataSource: DataSource by lazy {
        DataSourceProvider.getDataSource(requireActivity().application)
    }
    private val repository: Repository by lazy { RepositoryImpl(dataSource) }
    private val searchUseCase by lazy { SearchUseCase(repository) }
    private val getRandomRecipesUseCase by lazy { GetRandomRecipesUseCase(repository) }
    private val searchAdapter by lazy {
        SearchAdapter(getRandomRecipesUseCase(INITIAL_RECIPE_SIZE), this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecycleAdapter()
        addSearchCallBack()
    }

    private fun setupRecycleAdapter() {
        binding.recyclerViewSearch.adapter = searchAdapter
    }

    private fun addSearchCallBack() {
        binding.searchBox.setEndIconOnClickListener { showFilterSheet() }
        binding.editTextName.addTextChangedListener(this)
    }

    override fun onRecipeClick(id: Int) {
        navigateToDetailsFragment(id)
    }

    private fun showFilterSheet() {
        val bottomSheetBinding = FilterBottomSheetBinding.inflate(layoutInflater)
        val dialog = BottomSheetDialog(requireContext())
        with(bottomSheetBinding) {
            checkSelectedSelections()
            btnApply.setOnClickListener {
                filterRecipes()
                dialog.dismiss()
            }
            btnClear.setOnClickListener {
                searchQuery = searchQuery.copy(
                    timeRanges = listOf(SearchQuery.DEFAULT_RANGE),
                    ingredients = emptyList()
                )
                timeGroup.clearCheck()
                ingredientsGroup.clearCheck()
            }
        }
        dialog.setCancelable(false)
        dialog.setContentView(bottomSheetBinding.root)
        dialog.show()
    }

    private fun FilterBottomSheetBinding.filterRecipes() {
        val selectedRanges = mutableListOf<IntRange>()
        val selectedIngredients = mutableListOf<String>()
        fillSelectedSelections(selectedRanges, selectedIngredients)
        searchQuery = searchQuery.copy(
            ingredients = selectedIngredients,
            timeRanges = if (selectedRanges.isEmpty()) listOf(SearchQuery.DEFAULT_RANGE) else selectedRanges,
        )
        applySearch()
    }

    override fun afterTextChanged(recipeName: Editable?) {
        timerForDelaySearch = Timer()
        timerForDelaySearch!!.schedule(object : TimerTask() {
            override fun run() {
                requireActivity().runOnUiThread {
                    searchQuery = searchQuery.copy(name = recipeName.toString())
                    applySearch()
                }
            }
        }, SEARCH_DELAY)
    }

    private fun applySearch() {
        val searchResult = searchUseCase(searchQuery)
        val searchQueryRecipeName = searchQuery.name
        if (searchResult != null) {
            showRecipeResult(searchResult)
        } else {
            showErrorMessage(searchQueryRecipeName)
        }
    }

    private fun showRecipeResult(searchResult: List<Recipe>) {
        searchAdapter.updateRecipes(searchResult)
        with(binding) {
            recyclerViewSearch.show()
            textViewSearchError.hide()
        }
    }

    private fun showErrorMessage(searchQueryRecipeName: String) {
        with(binding) {
            textViewSearchError.text =
                getString(R.string.theres_no_result_for, searchQueryRecipeName)
            recyclerViewSearch.hide()
            textViewSearchError.show()
        }
    }

    private fun View.hide() {
        visibility = View.INVISIBLE
    }

    private fun View.show() {
        visibility = View.VISIBLE
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        timerForDelaySearch?.cancel()
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
    private fun navigateToDetailsFragment(id: Int) {
        val detailsFragment = DetailsFragment.newInstance(id)
        requireActivity().supportFragmentManager.beginTransaction().apply {
            add(R.id.fragmentContainer, detailsFragment)
            addToBackStack(detailsFragment.javaClass.simpleName)
            commit()
        }
    }

    private fun FilterBottomSheetBinding.fillSelectedSelections(
        selectedRanges: MutableList<IntRange>,
        selectedIngredients: MutableList<String>
    ) {
        if (fastFoodChip.isChecked) selectedRanges.add(SearchQuery.RANGE_FAST_FOOD)
        if (timeRange30To45.isChecked) selectedRanges.add(SearchQuery.RANGE_30_45)
        if (timeRange45To60.isChecked) selectedRanges.add(SearchQuery.RANGE_45_60)
        if (timeMoreThan60.isChecked) selectedRanges.add(SearchQuery.RANGE_MORE_THAN_HOUR)

        if (ingredientGreenChillies.isChecked) selectedIngredients.add(SearchQuery.GREEN_CHILLIES)
        if (ingredientGinger.isChecked) selectedIngredients.add(SearchQuery.GINGER)
        if (ingredientOnion.isChecked) selectedIngredients.add(SearchQuery.ONION)
        if (ingredientCorianderPowder.isChecked) selectedIngredients.add(SearchQuery.CORIANDER_POWDER)
        if (ingredientEggs.isChecked) selectedIngredients.add(SearchQuery.EGGS)
        if (ingredientCloves.isChecked) selectedIngredients.add(SearchQuery.CLOVES_LAUNG)
        if (ingredientRedChillPowder.isChecked) selectedIngredients.add(SearchQuery.RED_CHILL_POWDER)
        if (ingredientAmchurr.isChecked) selectedIngredients.add(SearchQuery.AMCHUR)
        if (ingredientKarela.isChecked) selectedIngredients.add(SearchQuery.KARELA)
        if (ingredientSalt.isChecked) selectedIngredients.add(SearchQuery.SALT)
    }

    private fun FilterBottomSheetBinding.checkSelectedSelections() {
        val ranges = searchQuery.timeRanges
        log(ranges.toString())
        for (range in ranges) {
            when (range) {
                SearchQuery.RANGE_FAST_FOOD -> {
                    fastFoodChip.isChecked = true
                }
                SearchQuery.RANGE_30_45 -> {
                    timeRange30To45.isChecked = true
                }
                SearchQuery.RANGE_45_60 -> {
                    timeRange45To60.isChecked = true
                }
                SearchQuery.RANGE_MORE_THAN_HOUR -> {
                    timeMoreThan60.isChecked = true
                }
            }
        }
        val ingredients = searchQuery.ingredients
        for (ingredient in ingredients) {
            when (ingredient) {
                SearchQuery.GREEN_CHILLIES -> {
                    ingredientGreenChillies.isChecked = true
                }
                SearchQuery.GINGER -> {
                    ingredientGinger.isChecked = true
                }
                SearchQuery.CORIANDER_POWDER -> {
                    ingredientCorianderPowder.isChecked = true
                }
                SearchQuery.EGGS -> {
                    ingredientEggs.isChecked = true
                }
                SearchQuery.CLOVES_LAUNG -> {
                    ingredientCloves.isChecked = true
                }
                SearchQuery.RED_CHILL_POWDER -> {
                    ingredientRedChillPowder.isChecked = true
                }
                SearchQuery.AMCHUR -> {
                    ingredientAmchurr.isChecked = true
                }
                SearchQuery.KARELA -> {
                    ingredientKarela.isChecked = true
                }
                SearchQuery.SALT -> {
                    ingredientSalt.isChecked = true
                }
                SearchQuery.ONION -> {
                    ingredientOnion.isChecked = true
                }
            }
        }
    }

    private companion object {
        const val INITIAL_RECIPE_SIZE = 20
        const val SEARCH_DELAY = 500L
    }
}