package com.cupcake.chickenmasala.usecase.home

import com.cupcake.chickenmasala.data.model.Recipe
import com.cupcake.chickenmasala.usecase.DataManager
class FoodTypeFilterUseCase(
    private val dataManager: DataManager
    ){
    operator fun invoke():List<Recipe> = dataManager.getRecipes()
}