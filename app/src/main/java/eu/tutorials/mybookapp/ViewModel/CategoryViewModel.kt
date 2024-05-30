package eu.tutorials.mybookapp.ViewModel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import eu.tutorials.mybookapp.Model.data.Category
import eu.tutorials.mybookapp.Model.repository.CategoryRepository
import kotlinx.coroutines.launch

class CategoryViewModel(
    private val categoryRepository: CategoryRepository = CategoryRepository()
):ViewModel() {
    private val _categoriesState = mutableStateOf(CategoryState())
    val categoriesState : State<CategoryState> = _categoriesState

    init {
        fetchCategories()
    }
    private fun fetchCategories() {
        viewModelScope.launch {
            try {
                val response = categoryRepository.getCategories()
                _categoriesState.value = _categoriesState.value.copy(
                    list = response.categories,
                    loading = false,
                    error = null
                )
            } catch (e : Exception) {
                _categoriesState.value = _categoriesState.value.copy(
                    loading = false,
                    error = "Error fetching categories ${e.message}"
                )
            }
        }
    }
    data class CategoryState(
        val loading:Boolean = true,
        val list: List<Category> = emptyList(),
        val error: String? = null
    )
}