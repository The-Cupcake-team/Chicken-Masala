package com.cupcake.chickenmasala.usecase.search

import com.cupcake.chickenmasala.data.model.Recipe
import com.cupcake.chickenmasala.usecase.DataManager

class FilterByTimeRangeUseCase(private val dataManager: DataManager) {
    operator fun invoke(timeRange: List<IntRange>): List<Recipe>? {
        return dataManager
            .getRecipes()
            .filter { recipe ->
                timeRange.any { recipe.isInRange(it) }
            }.takeIf { it.isNotEmpty() }
    }

}