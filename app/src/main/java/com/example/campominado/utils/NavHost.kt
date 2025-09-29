package com.example.campominado.util

import androidx.compose.runtime.Composable

import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

import com.example.campominado.ui.screens.MainMenuScreen
import com.example.campominado.ui.screens.GameScreen

internal class NavHost {
    @Composable
    public fun Create() {
        val navController: NavHostController = rememberNavController()

        NavHost(navController = navController, startDestination = "mainmenu") {
            composable("mainmenu") {
                var mainMenuScreen = MainMenuScreen()
                mainMenuScreen.Create(navController)
            }

            composable("game/{mode}") { backStackEntry ->
                val mode = backStackEntry.arguments?.getString("mode") ?: "facil"
                var gameScreen: GameScreen = GameScreen()
                gameScreen.Create(navController, mode)
            }
        }
    }
}
