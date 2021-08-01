package database

import databaseEntities.Posts
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

val database = Database.connect (
    url = "jdbc:postgresql://localhost:5432/library",
    user = "postgres",
    password = "postgres"
)

fun synchronizeMemoryDB(): List<ResultRow> {
    return transaction {
        val result = Posts.selectAll()
        result.filterNotNull().toList()
    }
}