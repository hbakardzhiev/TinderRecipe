package services

import PostDTO
import database.synchronizeMemoryDB
import database.entities.Post

class PostsService {

    fun getPosts(): List<PostDTO> {
        val posts = synchronizeMemoryDB(Post)
        return posts.map { PostDTO(name = it[Post.name]) }
    }

}
