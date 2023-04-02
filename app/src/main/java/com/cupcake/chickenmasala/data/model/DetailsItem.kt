package com.cupcake.chickenmasala.data.model

sealed class DetailsItem(val priority: Int) {
    class DetailsRecipe(val recipe: Recipe) : DetailsItem(0)
    class StepIngredient(val ingredients: List<String>) : DetailsItem(1)
    class StepInstruction(val instructions: List<StepInstructions>) : DetailsItem(2)
}
