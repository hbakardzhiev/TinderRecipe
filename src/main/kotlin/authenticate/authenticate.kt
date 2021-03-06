package authenticate

import io.ktor.application.ApplicationCall
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import io.ktor.util.AttributeKey
import io.ktor.util.pipeline.PipelineContext
import authenticate.model.AuthenticationContext
import dtos.UserDTO
import services.AccountService

val accountKey = AttributeKey<UserDTO>("account")

suspend fun PipelineContext<Unit, ApplicationCall>.authenticate() {

    val accountId = call.request.queryParameters["access_token"]

    if (accountId != null) {
        val account = AccountService().findById(accountId)

        if (account != null) {
            call.attributes.put(accountKey, account)
        } else {
            call.respond(HttpStatusCode.Unauthorized, "Authentication failed")
            finish()
        }
    }
}

fun getContext(call: ApplicationCall): AuthenticationContext {
    val account: UserDTO? =  call.attributes.getOrNull(accountKey)
    return AuthenticationContext(account)
}
