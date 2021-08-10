import authenticate.authenticate
import authenticate.getContext
import com.apurebase.kgraphql.GraphQL
import graphql.authentication
import graphql.chefs
import graphql.posts
import io.ktor.application.Application
import io.ktor.application.ApplicationCallPipeline
import io.ktor.application.install
import services.AccountService
import services.ChefService
import services.PostsService

fun Application.module() {

    intercept(ApplicationCallPipeline.Call) {
        authenticate()
    }

    install(GraphQL) {
        val posts = PostsService()
        val chefs = ChefService()
        val authentication = AccountService()

        context { call ->
            +getContext(call)
        }
        playground = true
        schema {
            authentication(authentication)
            posts(posts)
            chefs(chefs)
        }
    }
}
