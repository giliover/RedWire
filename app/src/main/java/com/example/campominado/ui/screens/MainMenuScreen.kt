package com.example.campominado.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.campominado.util.rememberSoundManager
import com.example.campominado.util.rememberSoundManager
import com.example.campominado.R
import androidx.compose.runtime.*

internal class MainMenuScreen {

    @Composable
    fun Create(navController: NavController) {
        val soundManager = rememberSoundManager()
        val ui = ComponentesUI()
        
        // Inicia a música de fundo quando o jogo começa
        LaunchedEffect(Unit) {
            try {
                soundManager.playBackgroundMusic(R.raw.main_menu)
            } catch (e: Exception) {
                // Se o arquivo não existir, apenas ignora
                println("Música de fundo não encontrada: ${e.message}")
            }
        }

        // Usa o background padronizado de todos os menus
        ui.CreateBackground {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(22.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Título do jogo
                ui.CreateTitle("Campo Minado")

                Spacer(modifier = Modifier.height(24.dp))

                // Botões para os modos de jogo
                ui.CreateButton(
                    onClick = { navController.navigate("game/easy") },
                    text = "Modo Fácil"
                )

                Spacer(modifier = Modifier.height(12.dp))

                ui.CreateButton(
                    onClick = { navController.navigate("game/medium") },
                    text = "Modo Médio"
                )
                Spacer(modifier = Modifier.height(12.dp))

                ui.CreateButton(
                    onClick = { navController.navigate("game/hard") },
                    text = "Modo Difícil"
                )
            }
        }
    }
}
