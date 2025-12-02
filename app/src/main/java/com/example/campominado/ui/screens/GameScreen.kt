package com.example.campominado.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.campominado.model.Celula
import com.example.campominado.util.Calculo
import com.example.campominado.util.rememberSoundManager
import com.example.campominado.R

internal class GameScreen {

    @Composable
    private fun Play(mode: String) {
        val soundManager = rememberSoundManager()
        val (linhas, colunas, totalBombas) = when (mode) {
            "easy" -> Triple(10, 10, 10)
            "medium" -> Triple(10, 12, 15)
            "hard" -> Triple(10, 14, 20)
            else -> Triple(10, 10, 10)
        }

        val campo = remember { mutableStateOf(Calculo.gerarTabuleiro(linhas, colunas, totalBombas)) }
        val jogoIniciado = remember { mutableStateOf(false) }
        val gameOver = remember { mutableStateOf(false) }
        
        // Inicia a música de fundo quando o jogo começa
        LaunchedEffect(Unit) {
            try {
                soundManager.playBackgroundMusic(R.raw.war_theme)
            } catch (e: Exception) {
                // Se o arquivo não existir, apenas ignora
                println("Música de fundo não encontrada: ${e.message}")
            }
        }

        Column(modifier = Modifier.padding(16.dp)) {
            for (linha in campo.value) {
                Row {
                    for (celula in linha) {
                        val backgroundColor = when {
                            celula.revelada.value && celula.temMina.value -> Color.Red
                            celula.marcada.value -> Color.Yellow
                            celula.revelada.value -> Color.White
                            else -> Color.Gray
                        }

                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .padding(2.dp)
                                    .background(backgroundColor)
                                    .combinedClickable(
                                        onClick = {
                                            if (gameOver.value) return@combinedClickable

                                            // Se é o primeiro clique, gera o tabuleiro excluindo esta célula
                                            if (!jogoIniciado.value) {
                                                campo.value = Calculo.gerarTabuleiro(
                                                    linhas, 
                                                    colunas, 
                                                    totalBombas, 
                                                    excluirCelula = celula
                                                )
                                                jogoIniciado.value = true
                                                // Revela a célula clicada após gerar o tabuleiro
                                                val celulaGerada = campo.value[celula.linha][celula.coluna]
                                                Calculo.revelarCelula(celulaGerada, campo.value)
                                            } else {
                                                // Cliques subsequentes
                                                if (!celula.temMina.value) {
                                                    Calculo.revelarCelula(celula, campo.value)
                                                } else {
                                                    // Toca som de bomba quando explode
                                                    try {
                                                        soundManager.playBombSound(R.raw.bomb_explosion)
                                                    } catch (e: Exception) {
                                                        println("Som de bomba não encontrado: ${e.message}")
                                                    }
                                                    Calculo.revelarTudo(campo.value)
                                                    gameOver.value = true
                                                    soundManager.stopBackgroundMusic()
                                                    println("💣 Game Over!")
                                                }
                                            }
                                        },
                                        onLongClick = {
                                            if (!celula.revelada.value && !gameOver.value && jogoIniciado.value) {
                                                celula.marcada.value = !celula.marcada.value
                                            }
                                        }
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = when {
                                        celula.revelada.value && !celula.temMina.value && celula.valor > 0 -> celula.valor.toString()
                                        celula.revelada.value && celula.temMina.value -> "💣"
                                        celula.marcada.value -> "🚩"
                                        else -> ""
                                    },
                                    fontSize = 16.sp
                                )

                        }
                    }
                }
            }

            if (gameOver.value) {
                Spacer(modifier = Modifier.height(16.dp))
                Text("💀 Game Over!", color = Color.Red, fontSize = 20.sp)
            }
        }
    }

    @Composable
    fun Create(navController: NavController, mode: String) {
        val ui = ComponentesUI()
        val soundManager = rememberSoundManager()

        ui.CreateBackground {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                ui.CreateTitle("Modo de jogo: $mode")
                Spacer(modifier = Modifier.height(16.dp))
                Play(mode)
                Spacer(modifier = Modifier.height(16.dp))
                ui.CreateButton(
                    text = "Voltar",
                    onClick = { 
                        soundManager.stopBackgroundMusic()
                        navController.popBackStack() 
                    }
                )
            }
        }
    }
}
