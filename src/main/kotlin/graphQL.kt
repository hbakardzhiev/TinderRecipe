import com.apurebase.kgraphql.schema.dsl.SchemaBuilder
import services.PostsService

fun SchemaBuilder.posts(service: PostsService) {

    type<PostDTO>()
//    query("droid") {
//        resolver { id: String,
//            ->
//            val result =
//                service.getPosts(id)
//            result
//        }
//    }

    query("posts") {
        resolver {
            ->
            val result =
               service.getPosts()
            result
        }
    }
//
//    mutation("addTodo") {
//        resolver { text: String,
//                   completed: Boolean,
//                   scopeId: String,
//                   rootTodo: Boolean,
//                   parentTodoId: String?,
//                   ctx: Context
//            ->
//            val log = ctx.get<Logger>()!!
//            val result = catchExceptions {
//                val user: LoggedInUser = ctx.get() ?: throw NotLoggedInExceptionException("Not Logged In")
//                service.addTodo(user, text, completed, scopeId, rootTodo, parentTodoId)
//            }
//            log.info(result.toString())
//            result.first
//        }
//    }
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