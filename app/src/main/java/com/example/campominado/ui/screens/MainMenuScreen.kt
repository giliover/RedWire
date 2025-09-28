package com.example.campominado.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.material3.MaterialTheme

@Composable
fun MainMenuScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Campo Minado")

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = { navController.navigate("game/facil") }) {
            Text("Modo Fácil")
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(onClick = { navController.navigate("game/medio") }) {
            Text("Modo Médio")
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(onClick = { navController.navigate("game/dificil") }) {
            Text("Modo Difícil")
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(onClick = { navController.navigate("game/personalizado") }) {
            Text("Modo Personalizado")
        }
    }
}