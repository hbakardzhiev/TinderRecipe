package dtos

import database.entities.Chef

data class RecipeDTO(
    val id: Int,
    var name: String,//recipe name could be changed
    val category: String,
    val chefID: Int
//    val image: URI
)