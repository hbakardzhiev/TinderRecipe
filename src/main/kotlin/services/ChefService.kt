package services

import database.entities.Chef
import database.entities.Chef.score
import database.synchronizeMemoryDB
import dtos.ChefDTO

class ChefService {

    /**
     * Retrieves all chefs in from the database.
     * @return a list with all chefs
     */
    fun getChefs(): List<ChefDTO> {
        val chefs = synchronizeMemoryDB(Chef)
        return chefs.map { ChefDTO(id = it[Chef.id].value, name = it[Chef.name], score = it[Chef.score]) }
    }

    /**
     * Searches for a chef by an id.
     * @param idParam the id of the chef to be return
     * @return the chef with the given id if exists else null
     */
    fun getChefById(idParam:Int): ChefDTO? {
        val chefs = getChefs()
        val chefsId = chefs.filter{ it.id == idParam }
        if(chefsId.size > 1) {
            //error, more chefs with the same id, possible?
        }
        return if (chefsId.size == 0) null else chefsId.last()
    }

    /**
     * Search for the chef's score by a given id.
     * @param idParam the id of the chef
     * @return the chef's score if a chef with such id exists
     * else null
     */
    fun getChefScore(idParam: Int):Int? {
        val chef = getChefById(idParam)
        return chef?.score
    }

    /**
     * Retrieves a given number of chefs with the highest score.
     * @param number the number of the chefs to be return
     * @return a list of chefs with the highest score
     */
    fun getChefsHighestScore(number: Int):List<ChefDTO>{
        val chefs = getChefs()
        chefs.sortedWith( compareByDescending { it.score })
        return chefs.slice(IntRange(0,number - 1))
    }


}