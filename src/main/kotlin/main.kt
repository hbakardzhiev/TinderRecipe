import database.database
import databaseEntities.Posts
import io.ktor.application.Application
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

fun main() {
    val port = System.getenv("PORT")?.toInt() ?: CONST_PORT

    database.connector.invoke()

    transaction {
        SchemaUtils.create(Posts)
    }

//    database.droids.add(droid
//    println(query.iterator().next().get())

    embeddedServer(Netty, port = port, module = Application::module).start(wait = true)


}
