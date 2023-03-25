package com.cupcake.chickenmasala.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.iterator
import com.cupcake.chickenmasala.R
import com.cupcake.chickenmasala.data.model.Recipe
import com.cupcake.chickenmasala.databinding.FilterSheetCardBinding
import com.cupcake.chickenmasala.databinding.FragmentSearchBinding
import com.cupcake.chickenmasala.ui.activity.HomeActivity
import com.cupcake.chickenmasala.ui.adpter.search.SearchAdapter
import com.cupcake.chickenmasala.ui.base.BaseFragment
import com.cupcake.chickenmasala.usecase.search.SearchUseCases
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

class SearchFragment : BaseFragment<FragmentSearchBinding>() {
    override val LOG_TAG = "SearchScreen"
    override val bindingInflater: (LayoutInflater, ViewGroup, Boolean) -> FragmentSearchBinding =
        FragmentSearchBinding::inflate
    private val searchUseCases by lazy { SearchUseCases((activity as HomeActivity).dataManager) }
    private val searchAdapter by lazy { SearchAdapter(searchUseCases.dataManager.getRecipes()) }

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
        dialog.setCancelable(false)
        dialog.setContentView(binding.root)
        dialog.show()
    }

    private fun filterList(binder: FilterSheetCardBinding) {

    }
}