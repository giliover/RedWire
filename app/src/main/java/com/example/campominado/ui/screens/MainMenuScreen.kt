package com.example.campominado.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.ui.unit.sp

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*

import com.example.campominado.ui.screens.ComponentesUI


internal class MainMenuScreen {
    @Composable
    public fun Create(navController: NavController) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(22.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val ui = ComponentesUI()
            ui.CreateTitle("Campo Minado")

            Spacer(modifier = Modifier.height(24.dp))

            ui.CreateButton(onClick = { navController.navigate("game/easy") }, text = "Modo Fácil")

            Spacer(modifier = Modifier.height(12.dp))

            ui.CreateButton(onClick = { navController.navigate("game/medium") }, text = "Modo Médio")

            Spacer(modifier = Modifier.height(12.dp))

            ui.CreateButton(onClick = { navController.navigate("game/hard") }, text = "Modo Difícil")
        }
    }
}
