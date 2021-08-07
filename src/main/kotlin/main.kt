import database.database
import database.entities.Post
import database.entities.Users
import io.ktor.application.Application
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import org.jetbrains.exposed.sql.SchemaUtils
//import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction

fun main() {
    val port = System.getenv("PORT")?.toInt() ?: CONST_PORT

    database.connector.invoke()

    transaction {
        SchemaUtils.create(Post)
        SchemaUtils.create(Users)

//        val hristo = User.insert {
//            it[name] = "Hristo"
//        }
//        val nevena = User.insert {
//            it[name] = "Nevena"
//        }
    }

    embeddedServer(Netty, port = port, module = Application::module).start(wait = true)

}
