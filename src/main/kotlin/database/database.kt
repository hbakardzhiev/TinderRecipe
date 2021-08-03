package database

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

val database = Database.connect (
    url = "jdbc:postgresql://localhost:5432/library",
    user = "postgres",
    password = "postgres"
)

fun <T: IntIdTable> synchronizeMemoryDB(inputObject: T): Iterable<ResultRow> {
    return transaction {
        val result = inputObject.selectAll()
        result.filterNotNull()
    }
}
