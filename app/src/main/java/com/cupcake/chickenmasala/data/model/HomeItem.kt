package com.cupcake.chickenmasala.data.model


sealed class HomeItem(val priority: Int) {
    class Advice(val advices: List<HealthAdvice>) : HomeItem(0)
    class RecentFood(val recent: List<Recipe>) : HomeItem(1)
    class ChipFood(val items: List<String>) : HomeItem(2)
    class RecipeFood(val recipe: Recipe) : HomeItem(3)
}
