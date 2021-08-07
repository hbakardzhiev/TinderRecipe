package services

import at.favre.lib.crypto.bcrypt.BCrypt
import authenticate.model.UserAccount
import authenticate.model.UserResponse
import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import database.entities.User
import database.synchronizeMemoryDB
import database.entities.Users
import database.entities.Users.id
import database.entities.Users.password
import database.entities.Users.username
import dtos.UserDTO
import graphql.inputtypes.UserInput
import io.ktor.application.*
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction
import java.nio.charset.StandardCharsets

class AccountService {

    private val accounts = synchronizeMemoryDB(Users)
        .map { UserDTO(id = it[Users.id].value, name = it[Users.username]) }


    fun findById(id: String): UserDTO? = accounts.firstOrNull { el -> el.id.toString() == id }

    fun returnAccessCode(username: String): Int? =
        accounts.firstOrNull { it.name == username }?.id
}


class AuthService {
    //    private val repository: UserRepository = UserRepository(client)
    private val secret: String = "someHashedValueNobodyIsAbleToGuess"
    private val algorithm: Algorithm = Algorithm.HMAC256(secret)
    private val verifier: JWTVerifier = JWT.require(algorithm).build()

    fun signIn(userInput: UserInput): UserResponse? {
        val user = synchronizeMemoryDB(Users).map {
            UserAccount(it[id].value.toString(), it[username], it[password].toByteArray())
        }.firstOrNull{x->x.username == userInput.username} ?: return null
        val passwordToByteArray = user.hashedPassword
        if (BCrypt.verifyer()
                .verify(userInput.password.toByteArray(), passwordToByteArray).verified
        ) {
            val token = signAccessToken(user.id.toString())
            val userAccount = UserAccount(
                username = user.username,
                hashedPassword = passwordToByteArray,
                id = user.id.toString()
            )
            return UserResponse(token, userAccount)
        }
        return null
    }

    fun signUp(userInput: UserInput): UserResponse? {
        val hashedPassword =
            BCrypt.withDefaults().hash(10, userInput.password.toByteArray()).toString()

        val newUser = transaction {
            Users.insert {
                it[username] = userInput.username
                it[password] = hashedPassword
            } get Users.id
        }

        val userAdded = transaction {
            User.findById(newUser.value)
        }!!
        val userAccount = UserAccount(
            username = userAdded.username,
            hashedPassword = userAdded.password.toByteArray(),
            id = userAdded.id.value.toString()
        )
        val token = signAccessToken(userAccount.username)
        return UserResponse(token, userAccount)
    }

    fun verifyToken(call: ApplicationCall): UserAccount? {
        return try {
            val authHeader = call.request.headers["Authorization"] ?: ""
            val token = authHeader.split("Bearer ").last()
            val accessToken = verifier.verify(JWT.decode(token))
            val userId = accessToken.getClaim("userId").asString()
            return UserAccount(id = userId, hashedPassword = byteArrayOf(), username = "")
        } catch (e: Exception) {
            print(e.message)
            null
        }
    }

    private fun signAccessToken(id: String): String {
        return JWT.create()
            .withIssuer("ktor_backend")
            .withClaim("userId", id)
            .sign(algorithm)
    }
}
