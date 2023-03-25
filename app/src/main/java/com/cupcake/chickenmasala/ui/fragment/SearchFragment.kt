package com.cupcake.chickenmasala.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cupcake.chickenmasala.R
import com.cupcake.chickenmasala.databinding.FilterSheetCardBinding
import com.cupcake.chickenmasala.databinding.FragmentSearchBinding
import com.cupcake.chickenmasala.ui.activity.HomeActivity
import com.cupcake.chickenmasala.ui.adpter.search.SearchAdapter
import com.cupcake.chickenmasala.ui.base.BaseFragment
import com.cupcake.chickenmasala.usecase.search.SearchUseCases
import com.google.android.material.bottomsheet.BottomSheetDialog

class SearchFragment : BaseFragment<FragmentSearchBinding>() {
    override val LOG_TAG = "SearchScreen"
    override val bindingInflater: (LayoutInflater, ViewGroup, Boolean) -> FragmentSearchBinding =
        FragmentSearchBinding::inflate
    private val searchUseCases by lazy { SearchUseCases((activity as HomeActivity).dataManager) }
    private val searchAdapter by lazy {
        SearchAdapter(
            searchUseCases.dataManager.getRecipes().shuffled()
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupBottomSheet()
        setupRecycleView()
    }

    private fun setupRecycleView() {
        binding.rvLarge.adapter = searchAdapter
    }

    private fun setupBottomSheet() {
        binding.searchBox.setEndIconOnClickListener {
            showFilterSheet()
        }
    }

    private fun showFilterSheet() {
        val binding = FilterSheetCardBinding.inflate(layoutInflater)
        val dialog = BottomSheetDialog(requireContext())
        with(binding) {
            btnApply.setOnClickListener {
                filterList(this)
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

    private fun filterList(binder: FilterSheetCardBinding) {
        val selectedRanges = mutableListOf<IntRange>()
        val selectedIngredients = mutableListOf<String>()

        binder.apply {
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
    }

    private companion object {
        val DEFAULT_RANGE = Int.MIN_VALUE..Int.MAX_VALUE
        val RANGE_FAST_FOOD = 0..30
        val RANGE_30_45 = 30..45
        val RANGE_45_60 = 45..60
        val RANGE_MORE_THAN_HOUR = 60..Int.MAX_VALUE
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
}