package services

import authenticate.model.Account

class AccountService {

    private val accounts = listOf(
        Account(id = "1", name = "Hristo"),
        Account(id = "2", name = "Nevena")
    )

    fun findById(id: String): Account? = accounts.find { it.id == id }

    fun returnAccessCode(username: String): String? =
        accounts.firstOrNull { it.name == username }?.id
}