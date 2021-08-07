package graphql

import authenticate.model.AuthenticationContext
import authenticate.model.AuthenticationException
import authenticate.model.UserAccount
import authenticate.model.UserResponse
import com.apurebase.kgraphql.Context
import com.apurebase.kgraphql.context
import com.apurebase.kgraphql.schema.dsl.SchemaBuilder
import dtos.PostDTO
import graphql.inputtypes.PostInput
import graphql.inputtypes.UserInput
import services.AuthService
import services.PostsService

fun SchemaBuilder.authentication(service: AuthService) {

    inputType<UserInput>()
    type<UserAccount> {
        UserAccount::hashedPassword.ignore()
    }
    query("signIn") {
        resolver { userInput: UserInput ->
            service.signIn(userInput)
        }
    }

    mutation("signUp") {
        resolver { userInput: UserInput ->
            val ctxResult = service.signUp(userInput)

            if (ctxResult != null) {

                context {
                    +ctxResult
                }
            }
            ctxResult
        }
    }

}

fun SchemaBuilder.posts(service: PostsService) {

    type<PostDTO>()
    query("posts") {
        resolver { ctx: Context, first: Int?, afterID: Int?
            ->
            ctx.get<UserResponse>()?.user
                ?: throw AuthenticationException()
            val result =
                service.getPosts(first, afterID)
            result
        }
    }

    inputType<PostInput>()
    mutation("addPost") {
        resolver { post: PostInput, ctx: Context
            ->
            ctx.get<AuthenticationContext>()?.account
                ?: throw AuthenticationException()
            service.createPost(post)
        }
    }
//
//    mutation("updateTodo") {
//        resolver { id: String,
//                   text: String?,
//                   completed: Boolean?,
//                   scopeId: String?,
//                   children: List<String>?,
//                   ctx: Context
//            ->
//            val log = ctx.get<Logger>()!!
//            val result = catchExceptions {
//                val user: LoggedInUser = ctx.get() ?: throw NotLoggedInExceptionException("Not Logged In")
//                service.updateTodo(user, id, text, completed, scopeId, children)
//            }
//            log.info(result.toString())
//            result.first
//        }
//    }
//
//    mutation("deleteTodo") {
//        resolver { id: String,
//                   ctx: Context
//            ->
//            val log = ctx.get<Logger>()!!
//            val result = catchExceptions {
//                val user: LoggedInUser = ctx.get() ?: throw NotLoggedInExceptionException("Not Logged In")
//                service.deleteTodo(user, id)
//            }
//            log.info(result.toString())
//            result.first
//        }
//    }
}
