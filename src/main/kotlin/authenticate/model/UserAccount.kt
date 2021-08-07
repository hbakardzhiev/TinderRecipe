package authenticate.model

data class UserAccount(val id: String, val username: String, val hashedPassword: ByteArray)
