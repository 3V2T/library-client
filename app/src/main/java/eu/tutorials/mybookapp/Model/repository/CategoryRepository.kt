package eu.tutorials.mybookapp.Model.repository

import eu.tutorials.mybookapp.Model.data.CategoriesResponse
import eu.tutorials.mybookapp.Model.data.service

class CategoryRepository {
    suspend fun getCategories(): CategoriesResponse {
        return service.getCategories()
    }
}