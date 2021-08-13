package services

import database.entities.Chef
import database.entities.Recipe
import database.synchronizeMemoryDB
import dtos.RecipeDTO
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

class RecipeService {
    //create a recipe
    //delete a recipe
    //update a recipe
    //retrieve all recipes//all liked recipes
    //read a recipe
    //mark a recipe as liked by a user ??

    fun getAllRecipes(): List<RecipeDTO>{
        val recipes = synchronizeMemoryDB(Recipe)
        return recipes.map{ RecipeDTO(id = it[Recipe.id].value, name = it[Recipe.name], category = it[Recipe.category], chefID = it[Recipe.chef]) }
    }

    fun getRecipeByID(idParam: Int): RecipeDTO? {
        val recipes = getAllRecipes()
        val recipeId = recipes.filter{ it.id == idParam }
        if(recipeId.size > 1) {
            //error, more chefs with the same id, possible?
        }
        return if (recipeId.isEmpty()) null else recipeId.last()
    }

    fun updateRecipe(recipeID: Int, newname: String){
        val recipe = getRecipeByID(recipeID)
        if (recipe != null) {
            transaction{
                Recipe.update {
                    it[name] = newname
                }
            }
        }
    }

    fun deleteRecipe(recipeID: Int){
        val recipe = getRecipeByID(recipeID)
        if (recipe != null) {
            transaction{
                Recipe.deleteWhere {
                    Recipe.name like recipe.name
                }
            }
        }
    }

    fun createRecipe(recipeName: String, type: String, idChef: Int): RecipeDTO {
        transaction{
            Recipe.insert{
                it[name] = recipeName
                it[category] = type
                it[Recipe.chef] = idChef
            }
        }
        return getAllRecipes().last()
    }
}