package com.cupcake.chickenmasala.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cupcake.chickenmasala.ui.base.BaseFragment
import com.cupcake.chickenmasala.R
import com.cupcake.chickenmasala.databinding.FilterSheetCardBinding
import com.cupcake.chickenmasala.databinding.FragmentSearchBinding
import com.cupcake.chickenmasala.ui.activity.HomeActivity
import com.cupcake.chickenmasala.usecase.search.FilterByIngredientsUseCase
import com.cupcake.chickenmasala.usecase.search.FilterByTimeRangeUseCase
import com.cupcake.chickenmasala.usecase.search.SearchByRecipeNameUseCase
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButton
import com.google.android.material.chip.ChipGroup


class SearchFragment : BaseFragment<FragmentSearchBinding>() {
    override val LOG_TAG = "SearchScreen"
    override val bindingInflater: (LayoutInflater, ViewGroup, Boolean) -> FragmentSearchBinding =
        FragmentSearchBinding::inflate

    val dataManager by lazy { (activity as HomeActivity).dataManager }
    private val filterByIngredientsUseCase by lazy { FilterByIngredientsUseCase(dataManager) }
    private val filterByTimeRangeUseCase by lazy { FilterByTimeRangeUseCase(dataManager) }
    private val searchByRecipeNameUseCase by lazy { SearchByRecipeNameUseCase(dataManager) }

    private val sheetBinding by lazy { FilterSheetCardBinding.inflate(layoutInflater) }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupBottomSheet()

        //log(filterByIngredientsUseCase(listOf(getString(R.string.onion), getString(R.string.eggs), getString(R.string.green_chillies), getString(R.string.salt))).toString())

        //log(filterByTimeRangeUseCase(listOf(0..30, 30..60))?.map { it.totalTimeInMin }.toString ())

        // log(searchByRecipeNameUseCase(recipeName = "tomato")?.map { it.translatedRecipeName }.toString())
    }

    private fun setupBottomSheet() {
        binding.searchBox.setEndIconOnClickListener {
            showFilterSheet()
        }
    }

    private fun showFilterSheet() {
        val dialog = BottomSheetDialog(requireContext())
        with(sheetBinding) {
            btnApply.setOnClickListener {
                filterList()
                dialog.dismiss()
            }
            btnClear.setOnClickListener {
                timeGroup.clearCheck()
                ingredientsGroup.clearCheck()
            }
        }
        dialog.setContentView(sheetBinding.root)
        dialog.show()
    }

    private fun filterList() {

    }

}