package eu.tutorials.mybookapp.ViewModel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import eu.tutorials.mybookapp.Model.data.EventInfo
import eu.tutorials.mybookapp.Model.repository.AuthorRepository
import eu.tutorials.mybookapp.Model.repository.BookRepository
import eu.tutorials.mybookapp.Model.repository.HistoryRepository
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class HistoryViewModel(
    private val historyRepository: HistoryRepository = HistoryRepository(),
    private val bookRepository: BookRepository = BookRepository(),
    private val authorRepository: AuthorRepository = AuthorRepository()
): ViewModel() {
    private val _historyState = mutableStateOf(HistoryState())
    val historyState : State<HistoryState> = _historyState


    fun fetchHistory(token:String) {
        viewModelScope.launch {
            try {
                val history: MutableList<EventInfo> = mutableListOf()
                val response = historyRepository.getHistoryByToken(token)
                for(event in response.history) {
                    val response2 = bookRepository.getBookById(event.book_id)
                    val response3 = authorRepository.getAuthor(response2.books[0].author_id)
                    val eventInfo = EventInfo(
                        id = event.id,
                        nameOfBook = response2.books[0].title,
                        author = response3.author[0].author,
                        last_read = event.last_read,
                        file_path = response2.books[0].file_path,
                        book_id = event.book_id
                    )
                    history.add(eventInfo)
                }
                _historyState.value = _historyState.value.copy(
                    history = history,
                    loading = false,
                    error = null
                )
            }
            catch (e : Exception) {
                _historyState.value = _historyState.value.copy(
                    loading = false,
                    error = "Error fetching history ${e.message}"
                )
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun addEventToHistory(token: String, bookId:String) {
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val formatted = current.format(formatter)
        val eventBody = EventBody(bookId, formatted)
        viewModelScope.launch {
            try {
                val response = historyRepository.addEventToHistory(eventBody, token)
            }
            catch (e : Exception) {
                println(e)
                _historyState.value = _historyState.value.copy(
                    loading = false,
                    error = "Error add event to history ${e.message}"
                )
            }
        }
    }

    data class EventBody(
        val bookId: String,
        val lastRead: String
    )
    fun clearHistory() {
        _historyState.value = _historyState.value.copy(
            history = emptyList(),
            loading = false,
            error = null
        )
    }
    data class HistoryState(
        val loading:Boolean = true,
        var history: List<EventInfo> = emptyList(),
        val error: String? = null
    )
}