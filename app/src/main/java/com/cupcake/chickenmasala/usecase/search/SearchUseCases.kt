package com.cupcake.chickenmasala.usecase.search

import com.cupcake.chickenmasala.usecase.Repository

data class SearchUseCases(
    val repository: Repository,
    val searchByRecipeNameUseCase: SearchByRecipeNameUseCase =
        SearchByRecipeNameUseCase(repository),
    val filterByIngredientsAndTimeUseCase: FilterByIngredientsAndTimeUseCase =
        FilterByIngredientsAndTimeUseCase(repository)
)
