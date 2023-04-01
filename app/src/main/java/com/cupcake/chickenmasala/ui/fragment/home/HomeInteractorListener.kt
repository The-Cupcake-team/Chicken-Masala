package com.cupcake.chickenmasala.ui.fragment.home

interface HomeInteractorListener {
    fun onViewPagerClicked(id: Int)
    fun onViewAllButtonClicked()
    fun onRecipeCardClicked(id: Int)
    fun onChipClicked(chipId: Int)
}