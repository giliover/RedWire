package com.example.campominado.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.Arrangement

internal class GameScreen {
    @Composable
    private fun Play() {
        // run game
    }

    @Composable
    public fun Create(navController: NavController, mode: String) {
        val ui = ComponentesUI()

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            ui.CreateTitle("Modo de jogo: $mode")

            Play()

            Spacer(modifier = Modifier.height(16.dp))

            ui.CreateButton(onClick = { navController.popBackStack() }, text = "Voltar")
        }

    }

}
