package com.cupcake.chickenmasala.usecase

import com.cupcake.chickenmasala.data.model.Cuisine
import com.cupcake.chickenmasala.data.model.Recipe

class GetCuisineUseCase(private val dataSource: List<Recipe>) {

    operator fun invoke(): List<Cuisine> {
        return dataSource
            .groupBy { it.cuisine }
            .map {
                Cuisine(
                    key = it.key,
                    imageUrl = it.value[0].imageUrl,
                    name = it.key.replace("Recipes", ""),
                )
            }
    }
}