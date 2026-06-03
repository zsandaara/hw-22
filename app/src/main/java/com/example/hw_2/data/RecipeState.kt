package com.example.hw_2.data

enum class RecipeState {
    WANT_TO_COOK,
    COOKING,
    COOKED;

    fun displayName(): String = when (this) {
        WANT_TO_COOK -> "Хочу приготовить"
        COOKING -> "Готовлю"
        COOKED -> "Приготовлено"
    }
}