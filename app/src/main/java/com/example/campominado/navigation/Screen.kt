package com.example.campominado.navigation

sealed class Screen(val route: String) {
    object MainMenu : Screen("mainmenu")

}