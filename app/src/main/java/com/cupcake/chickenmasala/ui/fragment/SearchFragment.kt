package com.cupcake.chickenmasala.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cupcake.chickenmasala.ui.base.BaseFragment
import com.cupcake.chickenmasala.R
import com.cupcake.chickenmasala.databinding.FragmentSearchBinding
import com.cupcake.chickenmasala.ui.activity.HomeActivity
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButton
import com.google.android.material.chip.ChipGroup


class SearchFragment : BaseFragment<FragmentSearchBinding>() {
    override val LOG_TAG = "SearchScreen"
    override val bindingInflater: (LayoutInflater, ViewGroup, Boolean) -> FragmentSearchBinding =
        FragmentSearchBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupBottomSheet()

        (activity as HomeActivity).dataManager.getRecipes().forEach {
            log(it.id.toString())
        }

    }

    private fun setupBottomSheet() {
        binding.searchBox.setEndIconOnClickListener {
            showFilterSheet()
        }
    }

    private fun showFilterSheet() {
        val dialog = BottomSheetDialog(requireContext())
        val view = layoutInflater.inflate(R.layout.filter_sheet_card, null)
        with(view) {
            findViewById<MaterialButton>(R.id.btn_apply).setOnClickListener {
                filterList()
                dialog.dismiss()
            }
            findViewById<MaterialButton>(R.id.btn_clear).setOnClickListener {
                findViewById<ChipGroup>(R.id.time_group).clearCheck()
                findViewById<ChipGroup>(R.id.ingredients_group).clearCheck()
            }
        }
        dialog.setContentView(view)
        dialog.show()
    }

    private fun filterList() {

    }

}