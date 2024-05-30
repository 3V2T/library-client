package eu.tutorials.mybookapp.ViewModel

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import eu.tutorials.mybookapp.Model.data.BookWithAuthor
import eu.tutorials.mybookapp.Model.repository.AuthorRepository
import eu.tutorials.mybookapp.Model.repository.BookRepository
import kotlinx.coroutines.launch

class BookViewModel(
    private val bookRepository: BookRepository = BookRepository(),
    private val authorRepository: AuthorRepository = AuthorRepository()
): ViewModel() {
    var searchValue by mutableStateOf("")
    private val _booksState = mutableStateOf(BookState())
    val booksState : State<BookState> = _booksState

    init {
        fetchBooks()
    }
    fun onSearchValueChange (newString:String) {
        searchValue = newString
    }
    fun fetchBooks() {
        viewModelScope.launch {
            try {
                val bookList: MutableList<BookWithAuthor> = mutableListOf()
                val response = bookRepository.getBooks("", "")
                for(book in response.books) {
                    val response2 = authorRepository.getAuthor(book.author_id)
                    val bookWithAuthor = BookWithAuthor(
                        id = book.id,
                        title = book.title,
                        author_id = book.author_id,
                        category_id = book.category_id,
                        cover_path = book.cover_path,
                        file_path = book.file_path,
                        description = book.description,
                        author = response2.author[0].author,
                        published = book.published
                    )
                    bookList.add(bookWithAuthor)
                }
                _booksState.value = _booksState.value.copy(
                    list = bookList,
                    loading = false,
                    error = null
                )
            } catch (e : Exception) {
                _booksState.value = _booksState.value.copy(
                    loading = false,
                    error = "Error fetching books ${e.message}"
                )
            }
        }
    }

    fun fetchBooksByCategory(category: String) {
        viewModelScope.launch {
            try {
                val bookList: MutableList<BookWithAuthor> = mutableListOf()
                val response = bookRepository.getBooks(category=category, author="")
                for(book in response.books) {
                    val response2 = authorRepository.getAuthor(book.author_id)
                    val bookWithAuthor = BookWithAuthor(
                        id = book.id,
                        title = book.title,
                        author_id = book.author_id,
                        category_id = book.category_id,
                        cover_path = book.cover_path,
                        file_path = book.file_path,
                        description = book.description,
                        author = response2.author[0].author,
                        published = book.published
                    )
                    bookList.add(bookWithAuthor)
                }
                _booksState.value = _booksState.value.copy(
                    list = bookList,
                    loading = false,
                    error = null
                )
            } catch (e : Exception) {
                _booksState.value = _booksState.value.copy(
                    loading = false,
                    error = "Error fetching books ${e.message}"
                )
            }
        }
    }

    fun fetchBooksByAuthor(author: String) {
        viewModelScope.launch {
            try {
                val bookList: MutableList<BookWithAuthor> = mutableListOf()
                val response = bookRepository.getBooks("", author = author)
                for(book in response.books) {
                    val response2 = authorRepository.getAuthor(book.author_id)
                    val bookWithAuthor = BookWithAuthor(
                        id = book.id,
                        title = book.title,
                        author_id = book.author_id,
                        category_id = book.category_id,
                        cover_path = book.cover_path,
                        file_path = book.file_path,
                        description = book.description,
                        author = response2.author[0].author,
                        published = book.published
                    )
                    bookList.add(bookWithAuthor)
                }
                _booksState.value = _booksState.value.copy(
                    list = bookList,
                    loading = false,
                    error = null
                )
            } catch (e : Exception) {
                _booksState.value = _booksState.value.copy(
                    loading = false,
                    error = "Error fetching books ${e.message}"
                )
            }
        }
    }

    fun fetchBooksByKeyword(keyword: String) {
        viewModelScope.launch {
            try {
                val bookList: MutableList<BookWithAuthor> = mutableListOf()
                val response = bookRepository.getBooksByKeyword(keyword = keyword)
                for(book in response.books) {
                    val response2 = authorRepository.getAuthor(book.author_id)
                    val bookWithAuthor = BookWithAuthor(
                        id = book.id,
                        title = book.title,
                        author_id = book.author_id,
                        category_id = book.category_id,
                        cover_path = book.cover_path,
                        file_path = book.file_path,
                        description = book.description,
                        author = response2.author[0].author,
                        published = book.published
                    )
                    bookList.add(bookWithAuthor)
                }
                _booksState.value = _booksState.value.copy(
                    list = bookList,
                    loading = false,
                    error = null
                )
            } catch (e : Exception) {
                _booksState.value = _booksState.value.copy(
                    loading = false,
                    error = "Book not found"
                )
            }
        }
    }

    fun fetchBooksById(id: String) {
        viewModelScope.launch {
            try {
                val bookList: MutableList<BookWithAuthor> = mutableListOf()
                val response = bookRepository.getBookById(id)
                for(book in response.books) {
                    val response2 = authorRepository.getAuthor(book.author_id)
                    val bookWithAuthor = BookWithAuthor(
                        id = book.id,
                        title = book.title,
                        author_id = book.author_id,
                        category_id = book.category_id,
                        cover_path = book.cover_path,
                        file_path = book.file_path,
                        description = book.description,
                        author = response2.author[0].author,
                        published = book.published
                    )
                    bookList.add(bookWithAuthor)
                }
                _booksState.value = _booksState.value.copy(
                    list = bookList,
                    loading = false,
                    error = null
                )
            } catch (e : Exception) {
                _booksState.value = _booksState.value.copy(
                    loading = false,
                    error = "Error fetching books ${e.message}"
                )
            }
        }
    }


    data class BookState(
        val loading:Boolean = true,
        val list: List<BookWithAuthor> = emptyList(),
        val error: String? = null
    )

}