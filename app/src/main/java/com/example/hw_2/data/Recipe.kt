package com.example.hw_2.data

data class Recipe(
    val id: Int,
    val name: String,
    val description: String,
    val ingredients: List<String>,
    val prepTime: String,
    val difficulty: String,
    val category: String,
    var state: RecipeState
)