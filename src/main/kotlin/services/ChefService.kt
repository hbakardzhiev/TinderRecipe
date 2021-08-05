package services

import database.entities.Chef
import database.entities.Post
import database.synchronizeMemoryDB
import dtos.ChefDTO

class ChefService {

    fun getChefs(): List<ChefDTO> {
        val posts = synchronizeMemoryDB(Post)
        return posts.map { ChefDTO(name = it[Chef.name],) }
    }

}