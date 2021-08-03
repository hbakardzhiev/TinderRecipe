package services

import database.synchronizeMemoryDB
import database.entities.User
import dtos.UserDTO

class AccountService {

    private val accounts = synchronizeMemoryDB(User)
        .map { UserDTO(id = it[User.id].value, name = it[User.name]) }


    fun findById(id: String): UserDTO? = accounts.firstOrNull { el -> el.id.toString() == id }

    fun returnAccessCode(username: String): Int? =
        accounts.firstOrNull { it.name == username }?.id
}
