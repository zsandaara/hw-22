package com.example.hw_2.data

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

object RecipeRepository {
    private val _recipes = MutableStateFlow(initialRecipes())
    val recipes: StateFlow<List<Recipe>> = _recipes.asStateFlow()

    fun updateRecipeState(recipeId: Int, newState: RecipeState) {
        _recipes.update { currentList ->
            currentList.map { recipe ->
                if (recipe.id == recipeId) recipe.copy(state = newState) else recipe
            }
        }
    }

    private fun initialRecipes(): List<Recipe> = listOf(
        Recipe(
            id = 1,
            name = "Спагетти Карбонара",
            description = "Классическая итальянская паста с яйцами, сыром, панчеттой и перцем.",
            ingredients = listOf("200 г спагетти", "100 г панчетты", "2 яйца", "50 г сыра пекорино", "чёрный перец"),
            prepTime = "20 мин",
            difficulty = "Средне",
            category = "Паста",
            state = RecipeState.WANT_TO_COOK
        ),
        Recipe(
            id = 2,
            name = "Салат Цезарь с курицей",
            description = "Свежий салат романо, куриная грудка на гриле, гренки и соус Цезарь.",
            ingredients = listOf("Салат романо", "200 г куриной грудки", "гренки", "соус Цезарь", "пармезан"),
            prepTime = "15 мин",
            difficulty = "Легко",
            category = "Салат",
            state = RecipeState.COOKING
        ),
        Recipe(
            id = 3,
            name = "Шоколадный лавовый кекс",
            description = "Тёплый шоколадный кекс с жидкой начинкой.",
            ingredients = listOf("100 г тёмного шоколада", "50 г сливочного масла", "2 яйца", "30 г сахара", "20 г муки"),
            prepTime = "25 мин",
            difficulty = "Сложно",
            category = "Десерт",
            state = RecipeState.COOKED
        ),
        Recipe(
            id = 4,
            name = "Овощная жареная смесь",
            description = "Быстрая обжарка с брокколи, болгарским перцем и соевым соусом.",
            ingredients = listOf("Брокколи", "болгарский перец", "морковь", "соевый соус", "чеснок", "имбирь"),
            prepTime = "15 мин",
            difficulty = "Легко",
            category = "Веган",
            state = RecipeState.WANT_TO_COOK
        ),
        Recipe(
            id = 5,
            name = "Тако с говядиной",
            description = "Тако из говяжьего фарша с сальсой, сыром и сметаной.",
            ingredients = listOf("500 г говяжьего фарша", "оболочки для тако", "салат латук", "сыр", "сальса", "сметана"),
            prepTime = "30 мин",
            difficulty = "Средне",
            category = "Мексиканская",
            state = RecipeState.COOKING
        )
    )
}