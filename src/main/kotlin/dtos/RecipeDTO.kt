package dtos

import database.entities.Chef

data class RecipeDTO(
    var name: String,//recipe name could be changed
    val category: String,
    val chef: Chef
)