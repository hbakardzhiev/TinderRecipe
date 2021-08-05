package graphQL

import authenticate.model.AuthenticationContext
import authenticate.model.AuthenticationException
import com.apurebase.kgraphql.Context
import com.apurebase.kgraphql.schema.dsl.SchemaBuilder
import dtos.PostDTO
import graphQL.inputTypes.PostInput
import services.AccountService
import services.PostsService

fun SchemaBuilder.authentication(service: AccountService) {

    query("authenticate") {
        resolver { username: String ->
            val result = service.returnAccessCode(username)
            result
        }
    }

}

fun SchemaBuilder.posts(service: PostsService) {

    type<PostDTO>()
    query("posts") {
        resolver { ctx: Context, first: Int?, afterID: Int?
            ->
            ctx.get<AuthenticationContext>()?.account
                ?: throw AuthenticationException()
            val result =
                service.getPosts(first, afterID)
            result
        }
    }

    mutation("addPost") {
        resolver { post: PostInput, ctx: Context
            ->
            ctx.get<AuthenticationContext>()?.account
                ?: throw AuthenticationException()
            service.createPost(post)
        }
    }
    inputType<PostInput>()
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
