package eu.tutorials.mybookapp.ViewModel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import eu.tutorials.mybookapp.Model.data.Author
import eu.tutorials.mybookapp.Model.repository.AuthorRepository
import kotlinx.coroutines.launch

class AuthorViewModel(
    private val authorRepository: AuthorRepository = AuthorRepository()
):ViewModel() {
    var searchValue by mutableStateOf("")
    private val _authorsState = mutableStateOf(AuthorState())
    val authorsState : State<AuthorState> = _authorsState

    private val _currentAuthor = mutableStateOf(Author("","",""))
    val currentAuthor : MutableState<Author> = _currentAuthor

    init {
        fetchAuthors()
    }
    fun onSearchValueChange (newString:String) {
        searchValue = newString
    }
    fun fetchAuthors() {
        viewModelScope.launch {
            try {
                val response = authorRepository.getAuthors()
                _authorsState.value = _authorsState.value.copy(
                    list = response.author,
                    loading = false,
                    error = null
                )
            } catch (e : Exception) {
                _authorsState.value = _authorsState.value.copy(
                    loading = false,
                    error = "Error fetching author ${e.message}"
                )
                println(e)
            }
        }
    }

    fun fetchAuthor(id:String) {
        viewModelScope.launch {
            val response = authorRepository.getAuthor(id)
            _currentAuthor.value = response.author[0]
        }
    }

    fun fetchAuthorsByKeyword(keyword: String) {
        viewModelScope.launch {
            try {
                val response = authorRepository.getAuthorsByKeyword(keyword=keyword)
                _authorsState.value = _authorsState.value.copy(
                    list = response.author,
                    loading = false,
                    error = null
                )
            } catch (e : Exception) {
                _authorsState.value = _authorsState.value.copy(
                    loading = false,
                    error = "Author not found"
                )
            }
        }
    }
    data class AuthorState(
        val loading:Boolean = true,
        val list: List<Author> = emptyList(),
        val error: String? = null
    )
}