package authenticate

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.util.*
import io.ktor.util.pipeline.*
import authenticate.model.Account
import authenticate.model.AuthenticationContext
import services.AccountService

val accountKey = AttributeKey<Account>("account")

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
    var account: Account? = null
    if (call.attributes.contains(accountKey)) {
        account = call.attributes[accountKey]
    }

    return AuthenticationContext(account)
}