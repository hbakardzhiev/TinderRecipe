import authenticate
import authenticate.model.UserSession
import com.apurebase.kgraphql.GraphQL
import graphql.authentication
import graphql.posts
import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import io.ktor.routing.*
import io.ktor.sessions.*
import services.AuthService
import services.PostsService

fun Application.module() {

//    intercept(ApplicationCallPipeline.Call) {
//        authenticate()
//    }

//    install(Sessions) {
//        cookie<UserSession>("user_session") {
//            cookie.path = "/"
//            cookie.maxAgeInSeconds = 60 * 60
//        }
//    }
    val posts = PostsService()
    val authService = AuthService()

//    install(Authentication) {
//        session<UserSession>("auth-basic") {
//            validate { sessionData ->
//                val id = authentication.findById(sessionData.id)?.id
//                if (id == null)
//                    UserSession(id.toString())
//                else null
//            }
//        }
//    }
    install(Authentication) {
        jwt {
            realm = "localhost"
            verifier(token)
        }
    }
    install(GraphQL) {
        context { call ->
            authService.verifyToken(call)?.let { +it }
        }
        routing {
            authenticate(optional = true) {
                post {

                }
            }
        }

        playground = true
        schema {
            authentication(authService)
            posts(posts)
        }
    }
}
