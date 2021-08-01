package services

import Post
import database.synchronizeMemoryDB
import databaseEntities.Posts
import org.jetbrains.exposed.sql.ResultRow

class PostsService {
    private var posts: List<ResultRow> = synchronizeMemoryDB()

    fun getPosts(): List<Post> {
        posts = synchronizeMemoryDB()
        return posts.map { Post(name = it[Posts.name]) }
    }
}