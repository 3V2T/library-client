package eu.tutorials.mybookapp.Model.repository

import eu.tutorials.mybookapp.Model.data.AuthorResponse
import eu.tutorials.mybookapp.Model.data.AuthorsResponse
import eu.tutorials.mybookapp.Model.data.service

class AuthorRepository {
    suspend fun getAuthors(): AuthorsResponse {
        return service.getAuthors()
    }
    suspend fun getAuthor(id: String): AuthorResponse {
        return service.getAuthor(id)
    }
    suspend fun getAuthorsByKeyword(keyword: String): AuthorResponse {
        return service.getAuthorsByKeyword(name = keyword)
    }
}