package com.cupcake.chickenmasala.ui.fragment.details.adapter


enum class DetailsItemType {
    INFO,
    STEP_INGREDIENTS,
    STEP_INSTRUCTIONS
}

data class DetailsItem<T>(
    var item: T,
    var type: DetailsItemType
)
