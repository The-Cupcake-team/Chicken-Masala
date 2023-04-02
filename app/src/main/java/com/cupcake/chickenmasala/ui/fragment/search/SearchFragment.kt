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
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentSearchBinding =
        FragmentSearchBinding::inflate

    private val dataSource: DataSource by lazy {
        DataSourceProvider.getDataSource(requireActivity().application)
    }
    private val repository: Repository by lazy { RepositoryImpl(dataSource) }
    private val searchAdapter by lazy { SearchAdapter(emptyList(), this) }
    private var timerForDelaySearch: Timer? = null
    private var searchQuery: SearchQuery = SearchQuery()


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

    private fun showFilterSheet() {
        val bottomSheetBinding = FilterBottomSheetBinding.inflate(layoutInflater)
        val dialog = BottomSheetDialog(requireContext())

        bottomSheetBinding.apply {
            checkSelectedTimerRange(this)
            checkSelectedIngredients(this)

            buttonApply.setOnClickListener {
                filterRecipes(this)
                dialog.dismiss()
            }

            buttonClear.setOnClickListener {
                clearFiltration(this)
            }
        }

        dialog.apply {
            setCancelable(false)
            setContentView(bottomSheetBinding.root)
            show()
        }
    }

    private fun checkSelectedTimerRange(bottomSheetBinding: FilterBottomSheetBinding) {
        with(bottomSheetBinding) {
            searchQuery.timeRanges.forEach { range ->
                when (range) {
                    SearchQuery.RANGE_FAST_FOOD -> chipFastFood
                    SearchQuery.RANGE_30_45 -> timeRange30To45
                    SearchQuery.RANGE_45_60 -> timeRange45To60
                    SearchQuery.RANGE_MORE_THAN_HOUR -> timeMoreThan60
                    else -> null
                }?.isChecked = true
            }
        }
    }

    private fun checkSelectedIngredients(bottomSheetBinding: FilterBottomSheetBinding) {
        with(bottomSheetBinding) {
            searchQuery.ingredients.forEach { ingredient ->
                when (ingredient) {
                    SearchQuery.GREEN_CHILLIES -> ingredientGreenChillies
                    SearchQuery.GINGER -> ingredientGinger
                    SearchQuery.CORIANDER_POWDER -> ingredientCorianderPowder
                    SearchQuery.EGGS -> ingredientEggs
                    SearchQuery.CLOVES_LAUNG -> ingredientCloves
                    SearchQuery.RED_CHILL_POWDER -> ingredientRedChillPowder
                    SearchQuery.AMCHUR -> ingredientAmchurr
                    SearchQuery.KARELA -> ingredientKarela
                    SearchQuery.SALT -> ingredientSalt
                    SearchQuery.ONION -> ingredientOnion
                    else -> null
                }?.isChecked = true
            }
        }
    }

    private fun clearFiltration(bottomSheetBinding: FilterBottomSheetBinding) {
        searchQuery = searchQuery.copy(
            timeRanges = listOf(SearchQuery.DEFAULT_RANGE),
            ingredients = emptyList()
        )
        with(bottomSheetBinding) {
            chipGroupTime.clearCheck()
            chipGroupIngredients.clearCheck()
        }
    }

    private fun filterRecipes(bottomSheetBinding: FilterBottomSheetBinding) {
        with(bottomSheetBinding) {
            val selectedRanges = getSelectedTimerRanges(this)
            searchQuery = searchQuery.copy(
                ingredients = getSelectedIngredients(this),
                timeRanges = if (selectedRanges.isEmpty())
                    listOf(SearchQuery.DEFAULT_RANGE)
                else
                    selectedRanges
            )
        }
        applySearch()
    }

    private fun getSelectedTimerRanges(bottomSheetBinding: FilterBottomSheetBinding): MutableList<IntRange> {
        with(bottomSheetBinding) {
            val selectedRanges = mutableListOf<IntRange>()
            val timerRangePairs = listOf(
                Pair(chipFastFood, SearchQuery.RANGE_FAST_FOOD),
                Pair(timeRange30To45, SearchQuery.RANGE_30_45),
                Pair(timeRange45To60, SearchQuery.RANGE_45_60),
                Pair(timeMoreThan60, SearchQuery.RANGE_MORE_THAN_HOUR)
            )
            return timerRangePairs.filter { it.first.isChecked }.mapTo(selectedRanges) { it.second }
        }
    }

    private fun getSelectedIngredients(bottomSheetBinding: FilterBottomSheetBinding): MutableList<String> {
        with(bottomSheetBinding) {
            val selectedIngredients = mutableListOf<String>()
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
            return ingredients.filter { it.first.isChecked }
                .mapTo(selectedIngredients) { it.second }
        }
    }

    override fun onRecipeClick(id: Int) {
        navigateToDetailsFragment(id)
    }

    private fun navigateToDetailsFragment(id: Int) {
        val detailsFragment = DetailsFragment.newInstance(id)
        activity?.supportFragmentManager?.beginTransaction()?.apply {
            add(R.id.fragmentContainer, detailsFragment)
            addToBackStack(detailsFragment.javaClass.simpleName)
            commit()
        }
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
        SearchUseCase(repository)(searchQuery)?.let { searchResult ->
            showRecipeResult(searchResult)
        } ?: showErrorMessage(searchQuery.name)
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


    private companion object {
        const val SEARCH_DELAY = 500L
    }
}