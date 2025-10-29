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

internal class GameScreen {

    @Composable
    private fun Play(mode: String) {
        val (linhas, colunas, totalBombas) = when (mode) {
            "easy" -> Triple(10, 10, 10)
            "medium" -> Triple(10, 12, 15)
            "hard" -> Triple(10, 14, 20)
            else -> Triple(10, 10, 10)
        }

        val campo = remember { mutableStateOf(Calculo.gerarTabuleiro(linhas, colunas, totalBombas)) }
        val gameOver = remember { mutableStateOf(false) }

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

                                        if (!celula.temMina.value) {
                                            Calculo.revelarCelula(celula, campo.value)
                                        } else {
                                            Calculo.revelarTudo(campo.value)
                                            gameOver.value = true
                                            println("💣 Game Over!")
                                        }
                                    },
                                    onLongClick = {
                                        if (!celula.revelada.value && !gameOver.value) {
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
                ui.CreateButton(onClick = { navController.popBackStack() }, text = "Voltar")
            }
        }
    }
}
