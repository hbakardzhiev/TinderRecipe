import authenticate.authenticate
import authenticate.getContext
import com.apurebase.kgraphql.GraphQL
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import services.AccountService
import services.PostsService

fun Application.module() {

    intercept(ApplicationCallPipeline.Call) {
        authenticate()
    }

    install(GraphQL) {
        val posts = PostsService()
        val authentication = AccountService()

        context { call ->
            +getContext(call)
        }
        playground = true
        schema {
            authentication(authentication)
            posts(posts)
        }
    }
}