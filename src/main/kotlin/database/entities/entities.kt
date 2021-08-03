package database.entities

import CONST_VARCHAR_LENGTH
import org.jetbrains.exposed.dao.id.IntIdTable

object Post : IntIdTable("posts") {
    val name = varchar("name", CONST_VARCHAR_LENGTH)
}

object User : IntIdTable("user") {
    val name = varchar("name", CONST_VARCHAR_LENGTH)
}
