package com.cupcake.chickenmasala.ui.fragment.home

import com.google.android.material.chip.ChipGroup

interface HomeInteractorListener {
    fun onViewPagerClicked(id: Int)
    fun onViewAllButtonClicked()
    fun onCardClicked(id: Int)
    fun onChipClicked(chipId: Int)
}