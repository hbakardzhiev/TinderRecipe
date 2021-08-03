package databaseEntities

import CONST_LENGTH
import org.jetbrains.exposed.dao.id.IntIdTable

object Posts: IntIdTable("posts") {
    val name = varchar("name", CONST_LENGTH)
}