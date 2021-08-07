package authenticate.model

data class UserResponse(val token: String, val user: UserAccount)
