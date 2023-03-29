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
    private val searchAdapter by lazy { SearchAdapter(emptyList(), this) }


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
            checkIngredientsSelected()
            checkTimerRangeSelected()
            buttonApply.setOnClickListener {
                filterRecipes()
                dialog.dismiss()
            }
            buttonClear.setOnClickListener {
                searchQuery = searchQuery.copy(
                    timeRanges = listOf(SearchQuery.DEFAULT_RANGE),
                    ingredients = emptyList()
                )
                chipGroupTime.clearCheck()
                chipGroupIngredients.clearCheck()
            }
        }
        dialog.setCancelable(false)
        dialog.setContentView(bottomSheetBinding.root)
        dialog.show()
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
        if (searchQuery.name.isBlank()) {
            showEmptyRecipes()
            return
        }
        val searchResult = searchUseCase(searchQuery)
        val searchQueryRecipeName = searchQuery.name
        if (searchResult != null) {
            showRecipeResult(searchResult)
        } else {
            showErrorMessage(searchQueryRecipeName)
        }
    }

    private fun showEmptyRecipes() {
        searchAdapter.updateRecipes(emptyList())
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

    private fun FilterBottomSheetBinding.filterRecipes() {
        val selectedTimerRanges = mutableListOf<IntRange>()
        val selectedIngredients = mutableListOf<String>()
        filterIngredients(selectedIngredients)
        filterTimerRange(selectedTimerRanges)
        searchQuery = searchQuery.copy(
            ingredients = selectedIngredients,
            timeRanges = if (selectedTimerRanges.isEmpty())
                listOf(SearchQuery.DEFAULT_RANGE)
            else
                selectedTimerRanges
        )
        applySearch()
    }

    private fun FilterBottomSheetBinding.filterTimerRange(selectedTimerRanges: MutableList<IntRange>) {
        val timerRanges = listOf(
            Pair(chipFastFood, SearchQuery.RANGE_FAST_FOOD),
            Pair(timeRange30To45, SearchQuery.RANGE_30_45),
            Pair(timeRange45To60, SearchQuery.RANGE_45_60),
            Pair(timeMoreThan60, SearchQuery.RANGE_MORE_THAN_HOUR)
        )
        timerRanges.filter { it.first.isChecked }.mapTo(selectedTimerRanges) { it.second }
    }

    private fun FilterBottomSheetBinding.filterIngredients(selectedIngredients: MutableList<String>) {
        val ingredients = listOf(
            Pair(ingredientGreenChillies, SearchQuery.GREEN_CHILLIES),
            Pair(ingredientGinger, SearchQuery.GINGER),
            Pair(ingredientOnion, SearchQuery.ONION),
            Pair(ingredientCorianderPowder, SearchQuery.CORIANDER_POWDER),
            Pair(ingredientEggs, SearchQuery.EGGS),
            Pair(ingredientCloves, SearchQuery.CLOVES_LAUNG),
            Pair(ingredientRedChillPowder, SearchQuery.RED_CHILL_POWDER),
            Pair(ingredientAmchurr, SearchQuery.AMCHUR),
            Pair(ingredientKarela, SearchQuery.KARELA),
            Pair(ingredientSalt, SearchQuery.SALT)
        )
        ingredients.filter { it.first.isChecked }.mapTo(selectedIngredients) { it.second }
    }


    private fun FilterBottomSheetBinding.checkTimerRangeSelected(){
        val ranges = searchQuery.timeRanges
        for (range in ranges) {
            when (range) {
                SearchQuery.RANGE_FAST_FOOD -> {
                    chipFastFood.isChecked = true
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
    }


    private fun FilterBottomSheetBinding.checkIngredientsSelected() {
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
        const val SEARCH_DELAY = 500L
    }
}