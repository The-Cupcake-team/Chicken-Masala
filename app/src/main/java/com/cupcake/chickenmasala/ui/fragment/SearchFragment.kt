package com.cupcake.chickenmasala.ui.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cupcake.chickenmasala.R
import com.cupcake.chickenmasala.data.RepositoryImpl
import com.cupcake.chickenmasala.data.data_source.DataSource
import com.cupcake.chickenmasala.data.data_source.DataSourceImpl
import com.cupcake.chickenmasala.databinding.FilterSheetCardBinding
import com.cupcake.chickenmasala.databinding.FragmentSearchBinding
import com.cupcake.chickenmasala.ui.adpter.search.RecipeClickListener
import com.cupcake.chickenmasala.ui.adpter.search.SearchAdapter
import com.cupcake.chickenmasala.ui.base.BaseFragment
import com.cupcake.chickenmasala.usecase.Repository
import com.cupcake.chickenmasala.usecase.search.SearchUseCases
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.util.Timer
import java.util.TimerTask

class SearchFragment : BaseFragment<FragmentSearchBinding>(), TextWatcher, RecipeClickListener {
    override val LOG_TAG = "SearchScreen"
    override val bindingInflater: (LayoutInflater, ViewGroup, Boolean) -> FragmentSearchBinding =
        FragmentSearchBinding::inflate

    private val dataSource: DataSource by lazy { DataSourceImpl(requireActivity().application) }
    private val repository: Repository by lazy { RepositoryImpl(dataSource) }
    private val searchUseCases by lazy { SearchUseCases(repository) }
    private val searchAdapter by lazy {
        SearchAdapter(
            searchUseCases.repository.getRecipes().shuffled(),
            this
        )
    }

    private var timerForDelaySearch: Timer? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialRecycleAdapter()
        addSearchCallBack()
    }

    private fun initialRecycleAdapter() {
        setupRecycleAdapter()
    }

    private fun addSearchCallBack() {
        binding.searchBox.setEndIconOnClickListener { showFilterSheet() }
        binding.editTextName.addTextChangedListener(this)
    }

    private fun setupRecycleAdapter() {
        binding.recyclerViewSearch.adapter = searchAdapter
    }

    override fun onRecipeClick(id: Int) {
        navigateToDetailsFragment(id)
    }

    private fun navigateToDetailsFragment(id: Int) {
        val detailsFragment = DetailsFragment.newInstance(id)
        requireActivity().supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentContainer, detailsFragment)
            commit()
        }
    }

    private fun showFilterSheet() {
        val binding = FilterSheetCardBinding.inflate(layoutInflater)
        val dialog = BottomSheetDialog(requireContext())

        with(binding) {
            btnApply.setOnClickListener {
                filterRecipes()
                dialog.dismiss()
            }
            btnClear.setOnClickListener {
                timeGroup.clearCheck()
                ingredientsGroup.clearCheck()
            }
        }

        dialog.setContentView(binding.root)
        dialog.show()
    }

    private fun FilterSheetCardBinding.filterRecipes() {
        val selectedRanges = mutableListOf<IntRange>()
        val selectedIngredients = mutableListOf<String>()

        fillSelected(selectedRanges, selectedIngredients)

        val filteredList = searchUseCases
            .filterByIngredientsAndTimeUseCase(
                ingredients = selectedIngredients,
                timeRange = if (selectedRanges.isEmpty()) listOf(DEFAULT_RANGE) else selectedRanges
            )

        if (selectedRanges.isNotEmpty() || selectedIngredients.isNotEmpty()) {
            searchAdapter.updateRecipes(filteredList.toList())
        }
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        timerForDelaySearch?.cancel()
    }

    override fun afterTextChanged(recipeName: Editable?) {
        timerForDelaySearch = Timer()
        timerForDelaySearch!!.schedule(object : TimerTask() {
            override fun run() {
                requireActivity().runOnUiThread {
                    applySearch(recipeName.toString())
                }
            }
        }, SEARCH_DELAY)
    }

    private fun applySearch(recipeName: String) {
        val searchResult = searchUseCases.searchByRecipeNameUseCase(recipeName)
        if (searchResult != null) {
            searchAdapter.updateRecipes(searchResult)
        } else {
            searchAdapter.updateRecipes(emptyList())
        }
    }

    private fun FilterSheetCardBinding.fillSelected(
        selectedRanges: MutableList<IntRange>,
        selectedIngredients: MutableList<String>
    ) {
        for (chipId in timeGroup.checkedChipIds) {
            when (chipId) {
                R.id.fast_food_chip -> {
                    selectedRanges.add(RANGE_FAST_FOOD)
                }
                R.id.time_range_30_to_45 -> {
                    selectedRanges.add(RANGE_30_45)
                }
                R.id.time_range_45_to_60 -> {
                    selectedRanges.add(RANGE_45_60)
                }
                R.id.time_more_than_60 -> {
                    selectedRanges.add(RANGE_MORE_THAN_HOUR)
                }
            }
        }

        for (chipId in ingredientsGroup.checkedChipIds) {
            when (chipId) {
                R.id.ingredient_green_chillies -> {
                    selectedIngredients.add(getString(R.string.green_chillies))
                }
                R.id.ingredient_ginger -> {
                    selectedIngredients.add(getString(R.string.ginger))
                }
                R.id.ingredient_onion -> {
                    selectedIngredients.add(getString(R.string.onion))
                }
                R.id.ingredient_coriander_powder -> {
                    selectedIngredients.add(getString(R.string.coriander_powder))
                }
                R.id.ingredient_eggs -> {
                    selectedIngredients.add(getString(R.string.eggs))
                }
                R.id.ingredient_cloves -> {
                    selectedIngredients.add(getString(R.string.cloves_laung))
                }
                R.id.ingredient_red_chill_powder -> {
                    selectedIngredients.add(getString(R.string.red_chill_powder))
                }
                R.id.ingredient_amchurr -> {
                    selectedIngredients.add(getString(R.string.amchuer))
                }
                R.id.ingredient_karela -> {
                    selectedIngredients.add(getString(R.string.karela))
                }
                R.id.ingredient_salt -> {
                    selectedIngredients.add(getString(R.string.salt))
                }
            }
        }
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

    }

    private companion object {
        val DEFAULT_RANGE = Int.MIN_VALUE..Int.MAX_VALUE
        val RANGE_FAST_FOOD = 0..30
        val RANGE_30_45 = 30..45
        val RANGE_45_60 = 45..60
        val RANGE_MORE_THAN_HOUR = 60..Int.MAX_VALUE
        const val SEARCH_DELAY = 600L
    }

}
