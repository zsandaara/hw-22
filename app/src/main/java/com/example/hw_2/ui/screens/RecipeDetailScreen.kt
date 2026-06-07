package com.example.hw_2.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.hw_2.data.RecipeState
import com.example.hw_2.viewmodel.RecipeDetailViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeDetailScreen(
    navController: NavController,
    recipeId: Int
) {
    val viewModel: RecipeDetailViewModel = viewModel(
        key = "detail_$recipeId",
        factory = RecipeDetailViewModel.provideFactory(recipeId)
    )
    val recipe by viewModel.recipe.collectAsState()
    val isNotFound by viewModel.isNotFound.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Детали рецепта") },
                navigationIcon = {
                    TextButton(onClick = { navController.popBackStack() }) {
                        Text("Назад")
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when {
                isNotFound -> {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.align(Alignment.Center)
                    ) {
                        Text("Рецепт не найден", style = MaterialTheme.typography.headlineSmall)
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = { navController.popBackStack() }) {
                            Text("Вернуться назад")
                        }
                    }
                }
                recipe == null -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                else -> {
                    val currentRecipe = recipe!!
                    LazyColumn(modifier = Modifier.padding(16.dp)) {
                        item {
                            Text(currentRecipe.name, style = MaterialTheme.typography.headlineMedium)
                            Spacer(modifier = Modifier.height(8.dp))
                            Text("Описание: ${currentRecipe.description}", style = MaterialTheme.typography.bodyLarge)
                            Spacer(modifier = Modifier.height(8.dp))
                            Text("Время приготовления: ${currentRecipe.prepTime}", style = MaterialTheme.typography.bodyMedium)
                            Text("Сложность: ${currentRecipe.difficulty}", style = MaterialTheme.typography.bodyMedium)
                            Spacer(modifier = Modifier.height(8.dp))
                            Text("Ингредиенты:", style = MaterialTheme.typography.titleMedium)
                            currentRecipe.ingredients.forEach { ingredient ->
                                Text("• $ingredient", style = MaterialTheme.typography.bodyMedium)
                            }
                            Spacer(modifier = Modifier.height(16.dp))

                            Text("Изменить статус:", style = MaterialTheme.typography.titleMedium)
                            Spacer(modifier = Modifier.height(8.dp))
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                RecipeState.entries.forEach { state ->
                                    FilterChip(
                                        selected = currentRecipe.state == state,
                                        onClick = { viewModel.changeState(state) },
                                        label = { Text(state.displayName()) }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}