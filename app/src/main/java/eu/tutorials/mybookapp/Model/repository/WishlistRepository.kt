package eu.tutorials.mybookapp.Model.repository

import eu.tutorials.mybookapp.ViewModel.WishlistViewModel
import eu.tutorials.mybookapp.Model.data.WishlistResponse
import eu.tutorials.mybookapp.Model.data.service

class WishlistRepository {
    suspend fun addBookToUserWishlist(bookId: WishlistViewModel.BookId, token: String) {
        service.addBookToUserWishlist(bookId, "Bearer ${token}")
    }
    suspend fun removeBookFromUserWishlist(bookId: String, token: String) {
        service.removeBookFromUserWishlist(bookId, "Bearer ${token}")
    }
    suspend fun getWishListByToken(token: String): WishlistResponse {
        return service.getWishListByToken("Bearer ${token}")
    }
}