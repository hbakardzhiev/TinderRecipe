package database.entities

import CONST_VARCHAR_LENGTH
import org.jetbrains.exposed.dao.id.IntIdTable

object Post : IntIdTable("posts") {
    val name = varchar("name", CONST_VARCHAR_LENGTH)
}

object User : IntIdTable("user") {
    val name = varchar("name", CONST_VARCHAR_LENGTH)
}

object Chef : IntIdTable("chef") {
    val name = varchar("name", CONST_VARCHAR_LENGTH)
    val score = integer("score")//score for chef
//(depends on how many people have liked recipes?)
}

object Recipe : IntIdTable("recipes") {
    val name = varchar("name", CONST_VARCHAR_LENGTH)
    val category = varchar("category",CONST_VARCHAR_LENGTH)
//    val image =
    val chef = (integer("chef").references(Chef.id))
}
