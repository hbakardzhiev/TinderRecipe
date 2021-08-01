import database.database
import databaseEntities.Posts
import io.ktor.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

fun main() {
    val port = System.getenv("PORT")?.toInt() ?: 8080

    database.connector.invoke()

    transaction {
        SchemaUtils.create(Posts)
    }

//    database.droids.add(droid
//    println(query.iterator().next().get())

    embeddedServer(Netty, port = port, module = Application::module).start(wait = true)


}