package databaseEntities

import CONST_VARCHAR_LENGTH
import org.jetbrains.exposed.dao.id.IntIdTable

object Posts: IntIdTable("posts") {
    val name = varchar("name", CONST_VARCHAR_LENGTH)
}