package com.example.hw_2.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.hw_2.ui.components.StatsBlock
import com.example.hw_2.viewmodel.RecipeListViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeListScreen(
    navController: NavController,
    viewModel: RecipeListViewModel = viewModel()
) {
    val searchQuery by viewModel.searchQuery.collectAsState()
    val selectedDifficulty by viewModel.selectedDifficulty.collectAsState()
    val filteredRecipes by viewModel.filteredRecipes.collectAsState()
    val stats by viewModel.stats.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Кулинарная книга", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = searchQuery,
            onValueChange = { viewModel.updateSearchQuery(it) },
            label = { Text("Поиск по названию") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row {
            listOf("Все", "Легко", "Средне", "Сложно").forEach { difficulty ->
                FilterChip(
                    onClick = { viewModel.updateSelectedDifficulty(difficulty) },
                    label = { Text(difficulty) },
                    selected = selectedDifficulty == difficulty,
                    modifier = Modifier.padding(end = 8.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        StatsBlock(want = stats.first, cooking = stats.second, cooked = stats.second)

        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn {
            items(
                items = filteredRecipes,
                key = { it.id }   // ← ключ по id, чтобы Compose не путал элементы при фильтрации
            ) { recipe ->
                RecipeItem(
                    recipe = recipe,
                    onClick = { navController.navigate("detail/${recipe.id}") }
                )
                Divider()
            }
        }
    }
}

@Composable
fun RecipeItem(
    recipe: com.example.hw_2.data.Recipe,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        onClick = onClick
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(recipe.name, style = MaterialTheme.typography.titleMedium)
            Text("${recipe.category} • ${recipe.prepTime} • ${recipe.difficulty}", style = MaterialTheme.typography.bodySmall)
            Text("Статус: ${recipe.state.displayName()}", style = MaterialTheme.typography.bodySmall)
        }
    }
}