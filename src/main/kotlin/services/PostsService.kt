package services

import dtos.PostDTO
import database.synchronizeMemoryDB
import database.entities.Post
import graphQL.inputTypes.PostInput
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction

class PostsService {

    fun getPosts(first: Int?, afterID: Int?): List<PostDTO> {
        val posts = postDTO()
        val index = afterID ?: (posts.first().id - 1)
        val numberOfElementToOutput = (first ?: posts.size) - 1
        return posts.slice(IntRange(index, numberOfElementToOutput))
    }

    private fun postDTO(): List<PostDTO> {
        val posts = synchronizeMemoryDB(Post)
        return posts.map {
            PostDTO(id = it[Post.id].value, name = it[Post.name], totalElements = posts.count())
        }
    }

    fun createPost(input: PostInput): PostDTO {
        transaction {
            Post.insert {
                it[name] = input.name
            }
        }
        return postDTO().last()
    }
}
