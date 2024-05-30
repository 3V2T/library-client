package eu.tutorials.mybookapp.ViewModel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import eu.tutorials.mybookapp.Model.data.BookWithAuthor
import eu.tutorials.mybookapp.Model.repository.AuthorRepository
import eu.tutorials.mybookapp.Model.repository.BookRepository
import eu.tutorials.mybookapp.Model.repository.WishlistRepository
import kotlinx.coroutines.launch

class WishlistViewModel(
    private val bookRepository: BookRepository = BookRepository(),
    private val authorRepository: AuthorRepository = AuthorRepository(),
    private val wishlistRepository: WishlistRepository = WishlistRepository()
):ViewModel() {
    private val _wishlistState = mutableStateOf(WishlistState())
    val wishlistState : State<WishlistState> = _wishlistState


    fun fetchWishlist(token:String) {
        viewModelScope.launch {
            try {
                val bookList: MutableList<BookWithAuthor> = mutableListOf()
                val response = wishlistRepository.getWishListByToken(token)
                for(wish in response.wishList) {
                    val response2 = bookRepository.getBookById(wish.book_id)
                    val response3 = authorRepository.getAuthor(response2.books[0].author_id)
                    val bookWithAuthor = BookWithAuthor(
                        id = response2.books[0].id,
                        title = response2.books[0].title,
                        author_id = response2.books[0].author_id,
                        category_id = response2.books[0].category_id,
                        cover_path = response2.books[0].cover_path,
                        file_path = response2.books[0].file_path,
                        description = response2.books[0].description,
                        author = response3.author[0].author,
                        published = response2.books[0].published
                    )
                    bookList.add(bookWithAuthor)
                }
                _wishlistState.value = _wishlistState.value.copy(
                    wishlist = bookList,
                    loading = false,
                    error = null
                )
            }
            catch (e : Exception) {
                _wishlistState.value = _wishlistState.value.copy(
                    loading = false,
                    error = "Error fetching wishlist ${e.message}"
                )
            }
        }
    }

    fun addBookToWishlist(token: String, bookId:String) {
        val bookId = BookId(bookId)
        viewModelScope.launch {
            try {
                val response = wishlistRepository.addBookToUserWishlist(bookId, token)
                val bookList: MutableList<BookWithAuthor> = mutableListOf()
                val response1 = wishlistRepository.getWishListByToken(token)
                for(wish in response1.wishList) {
                    val response2 = bookRepository.getBookById(wish.book_id)
                    val response3 = authorRepository.getAuthor(response2.books[0].author_id)
                    val bookWithAuthor = BookWithAuthor(
                        id = response2.books[0].id,
                        title = response2.books[0].title,
                        author_id = response2.books[0].author_id,
                        category_id = response2.books[0].category_id,
                        cover_path = response2.books[0].cover_path,
                        file_path = response2.books[0].file_path,
                        description = response2.books[0].description,
                        author = response3.author[0].author,
                        published = response2.books[0].published
                    )
                    bookList.add(bookWithAuthor)
                }
                _wishlistState.value = _wishlistState.value.copy(
                    wishlist = bookList,
                    loading = false,
                    error = null
                )
            }
            catch (e : Exception) {
                println(e)
                _wishlistState.value = _wishlistState.value.copy(
                    loading = false,
                    error = "Error fetching wishlist ${e.message}"
                )
            }
        }
    }
    fun removeBookFromWishlist(token: String, bookId:String) {
        viewModelScope.launch {
            try {
                val response = wishlistRepository.removeBookFromUserWishlist(bookId,token)
                val bookList: MutableList<BookWithAuthor> = mutableListOf()
                val response1 = wishlistRepository.getWishListByToken(token)
                for(wish in response1.wishList) {
                    val response2 = bookRepository.getBookById(wish.book_id)
                    val response3 = authorRepository.getAuthor(response2.books[0].author_id)
                    val bookWithAuthor = BookWithAuthor(
                        id = response2.books[0].id,
                        title = response2.books[0].title,
                        author_id = response2.books[0].author_id,
                        category_id = response2.books[0].category_id,
                        cover_path = response2.books[0].cover_path,
                        file_path = response2.books[0].file_path,
                        description = response2.books[0].description,
                        author = response3.author[0].author,
                        published = response2.books[0].published
                    )
                    bookList.add(bookWithAuthor)
                }
                _wishlistState.value = _wishlistState.value.copy(
                    wishlist = bookList,
                    loading = false,
                    error = null
                )
            }
            catch (e : Exception) {
                println(e)
                _wishlistState.value = _wishlistState.value.copy(
                    loading = false,
                    error = "Error fetching wishlist ${e.message}"
                )
            }
        }
    }

    fun clearWishlist() {
        _wishlistState.value = _wishlistState.value.copy(
            wishlist = emptyList(),
            loading = false,
            error = null
        )
    }
    data class BookId(
        val bookId: String
    )
    data class WishlistState(
        val loading:Boolean = true,
        var wishlist: List<BookWithAuthor> = emptyList(),
        val error: String? = null
    )
}

