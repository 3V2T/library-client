package eu.tutorials.mybookapp.Model.repository

import eu.tutorials.mybookapp.Model.data.BooksResponse
import eu.tutorials.mybookapp.Model.data.service

class BookRepository {
    suspend fun getBooks(category: String = "", author: String = ""): BooksResponse {
        return service.getBooks(category, author)
    }
    suspend fun getBooksByKeyword(keyword: String): BooksResponse {
        return service.getBooksByKeyword(keyword)
    }

    suspend fun getBookById(id: String) : BooksResponse {
        return service.getBookById(id)
    }
}