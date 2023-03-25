package com.cupcake.chickenmasala.usecase.search

import com.cupcake.chickenmasala.usecase.DataManager

data class SearchUseCases(
    val dataManager: DataManager,
    val searchByRecipeNameUseCase: SearchByRecipeNameUseCase = SearchByRecipeNameUseCase(dataManager),
    val filterByTimeRangeUseCase: FilterByTimeRangeUseCase = FilterByTimeRangeUseCase(dataManager),
    val filterByIngredientsUseCase: FilterByIngredientsUseCase = FilterByIngredientsUseCase(
        dataManager
    ),
)
