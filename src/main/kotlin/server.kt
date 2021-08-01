import com.apurebase.kgraphql.GraphQL
import io.ktor.application.*
import io.ktor.features.*
import services.PostsService

fun Application.module() {

    install(CORS)
    install(GraphQL) {
        val posts = PostsService()
        playground = true
        schema {
            posts(posts)
        }
    }
}