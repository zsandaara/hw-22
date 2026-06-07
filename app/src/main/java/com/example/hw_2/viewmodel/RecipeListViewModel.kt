package com.example.hw_2.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hw_2.data.Recipe
import com.example.hw_2.data.RecipeRepository
import com.example.hw_2.data.RecipeState
import kotlinx.coroutines.flow.*

class RecipeListViewModel(
    private val repository: RecipeRepository = RecipeRepository
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _selectedDifficulty = MutableStateFlow("Все")
    val selectedDifficulty: StateFlow<String> = _selectedDifficulty.asStateFlow()

    // Фильтрованный список рецептов
    val filteredRecipes: StateFlow<List<Recipe>> = combine(
        repository.recipes,
        _searchQuery,
        _selectedDifficulty
    ) { recipes, query, difficulty ->
        recipes.filter { recipe ->
            (query.isEmpty() || recipe.name.contains(query, ignoreCase = true)) &&
                    (difficulty == "Все" || recipe.difficulty == difficulty)
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    // Статистика считается ПО ФИЛЬТРОВАННОМУ СПИСКУ (исправлено)
    val stats: StateFlow<Triple<Int, Int, Int>> = filteredRecipes.map { recipes ->
        val want = recipes.count { it.state == RecipeState.WANT_TO_COOK }
        val cooking = recipes.count { it.state == RecipeState.COOKING }
        val cooked = recipes.count { it.state == RecipeState.COOKED }
        Triple(want, cooking, cooked)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = Triple(0, 0, 0)
    )

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun updateSelectedDifficulty(difficulty: String) {
        _selectedDifficulty.value = difficulty
    }
}