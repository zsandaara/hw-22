package com.example.hw_2.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.hw_2.ui.screens.RecipeDetailScreen
import com.example.hw_2.ui.screens.RecipeListScreen

@Composable
fun NavGraph() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "list") {
        composable("list") {
            RecipeListScreen(navController = navController)
        }
        composable(
            "detail/{recipeId}",
            arguments = listOf(navArgument("recipeId") { type = NavType.IntType })
        ) { backStackEntry ->
            val recipeId = backStackEntry.arguments?.getInt("recipeId")
            if (recipeId != null && recipeId > 0) {
                RecipeDetailScreen(navController = navController, recipeId = recipeId)
            } else {
                // Если ID невалидный – вернуться на список
                navController.popBackStack()
            }
        }
    }
}