package databaseEntities

import org.jetbrains.exposed.dao.id.IntIdTable
private const val CONST_LENGTH = 150

object Posts: IntIdTable("posts") {
    val name = varchar("name", CONST_LENGTH)
}