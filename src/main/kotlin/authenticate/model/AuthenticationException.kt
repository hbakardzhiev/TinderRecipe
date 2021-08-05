package authenticate.model

data class AuthenticationException(val error: String = "Unauthenticated") : Throwable()
