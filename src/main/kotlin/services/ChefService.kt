package services

import database.entities.Chef
import database.entities.Chef.score
import database.synchronizeMemoryDB
import dtos.ChefDTO

class ChefService {

    fun getChefs(): List<ChefDTO> {
        val chefs = synchronizeMemoryDB(Chef)
        return chefs.map { ChefDTO(id = it[Chef.id].value, name = it[Chef.name], score = it[Chef.score]) }
    }

    fun getChefById(idParam:Int): ChefDTO {
        val chefs = getChefs()
        val chefsId = chefs.filter{it.id == idParam}
        if(chefsId.size > 1) {
            //error
        }
        return chefsId.last()
    }

    fun getChefScore(idParam: Int):Int {
        val chef = getChefById(idParam)
        return chef.score;
    }

//    fun getChefsHighestScore(number: Int):List<ChefDTO>{
//        val chefs = getChefs()
//        chefs.sortedWith(ChefDTO)
//        return
//    }


}