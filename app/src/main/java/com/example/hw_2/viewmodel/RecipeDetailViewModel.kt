package com.example.hw_2.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.hw_2.data.Recipe
import com.example.hw_2.data.RecipeRepository
import com.example.hw_2.data.RecipeState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class RecipeDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val repository: RecipeRepository = RecipeRepository
) : ViewModel() {
    private val recipeId: Int = savedStateHandle["recipeId"] ?: -1

    private val _recipe = MutableStateFlow<Recipe?>(null)
    val recipe: StateFlow<Recipe?> = _recipe.asStateFlow()

    private val _isNotFound = MutableStateFlow(false)
    val isNotFound: StateFlow<Boolean> = _isNotFound.asStateFlow()

    init {
        viewModelScope.launch {
            repository.recipes.map { list ->
                list.find { it.id == recipeId }
            }.collect { found ->
                if (found == null && recipeId != -1) {
                    _isNotFound.value = true
                }
                _recipe.value = found
            }
        }
    }

    fun changeState(newState: RecipeState) {
        if (recipeId != -1) {
            repository.updateRecipeState(recipeId, newState)
        }
    }

    companion object {
        fun provideFactory(recipeId: Int) = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val handle = SavedStateHandle(mapOf("recipeId" to recipeId))
                return RecipeDetailViewModel(handle) as T
            }
        }
    }
}