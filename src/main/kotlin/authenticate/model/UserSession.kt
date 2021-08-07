package authenticate.model

import io.ktor.auth.*

data class UserSession(val id: String) : Principal
