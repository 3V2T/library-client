package eu.tutorials.mybookapp.ViewModel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import eu.tutorials.mybookapp.Model.data.User
import eu.tutorials.mybookapp.Model.repository.UserRepository
import kotlinx.coroutines.launch

class UserViewModel(
    private val userRepository: UserRepository = UserRepository()
): ViewModel() {
    private val _userState = mutableStateOf(UserState())
    val userState : State<UserState> = _userState

    fun clearUserSession() {
        _userState.value = _userState.value.copy(user = emptyList())
    }

    fun fetchCurrentUser(token:String) {
        viewModelScope.launch {
            try {
                val response = userRepository.getCurrentUser(token)
                _userState.value = _userState.value.copy(
                    user = response.user,
                    loading = false,
                    error = null
                )
            }
            catch (e : Exception) {
                _userState.value = _userState.value.copy(
                    loading = false,
                    error = "Error fetching current user ${e.message}"
                )
            }
        }
    }
    data class UserState(
        val loading:Boolean = true,
        var user: List<User> = emptyList(),
        val error: String? = null
    )
}