package services

import PostDTO
import database.synchronizeMemoryDB
import databaseEntities.Posts
import org.jetbrains.exposed.sql.ResultRow

class PostsService {
    private var posts: List<ResultRow> = synchronizeMemoryDB()

    fun getPosts(): List<PostDTO> {
        posts = synchronizeMemoryDB()
        return posts.map { PostDTO(name = it[Posts.name]) }
    }
}