package com.cupcake.chickenmasala.usecase.search

data class SearchQuery(
    val name: String = "",
    val timeRanges: List<IntRange> = listOf(DEFAULT_RANGE),
    val ingredients: List<String> = emptyList(),
){

    companion object{
        val DEFAULT_RANGE = Int.MIN_VALUE..Int.MAX_VALUE
        val RANGE_FAST_FOOD = 0..30
        val RANGE_30_45 = 30..45
        val RANGE_45_60 = 45..60
        val RANGE_MORE_THAN_HOUR = 60..Int.MAX_VALUE

        const val GREEN_CHILLIES = "green chillies"
        const val GINGER = "Ginger"
        const val CORIANDER_POWDER = "coriander powder"
        const val EGGS = "Eggs"
        const val CLOVES_LAUNG = "cloves (laung)"
        const val RED_CHILL_POWDER = "Red chill Powder"
        const val AMCHUR = "Amchur"
        const val KARELA = "Karela"
        const val SALT = "Salt"
        const val ONION = "Onion"
    }
}
