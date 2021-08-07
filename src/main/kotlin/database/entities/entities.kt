package database.entities

import CONST_VARCHAR_LENGTH
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object Post : IntIdTable("posts") {
    val name = varchar("name", CONST_VARCHAR_LENGTH)
}

object Users : IntIdTable("user") {
    val username = varchar("username", CONST_VARCHAR_LENGTH)
    val password = varchar("password", 500)
}

class User(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<User>(Users)

    var username by Users.username
    var password by Users.password
}