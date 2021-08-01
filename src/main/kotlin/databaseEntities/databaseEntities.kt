package databaseEntities

import org.jetbrains.exposed.dao.id.IntIdTable


object Posts: IntIdTable("posts") {
    val name = varchar("name", 150)
}