package com.example.campominado.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.campominado.model.Celula
import com.example.campominado.util.Calculo
import com.example.campominado.util.rememberSoundManager
import com.example.campominado.R

internal class GameScreen {

    @Composable
    private fun Play(navController: NavController, mode: String) {
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
        val vitoria = remember { mutableStateOf(false) }
        val showVictoryDialog = remember { mutableStateOf(false) }
        
        // Inicia a música de fundo quando o jogo começa
        LaunchedEffect(Unit) {
            try {
                soundManager.playBackgroundMusic(R.raw.war_theme)
            } catch (e: Exception) {
                // Se o arquivo não existir, apenas ignora
                println("Música de fundo não encontrada: ${e.message}")
            }
        }

        val configuration = LocalConfiguration.current
        val screenWidthDp = configuration.screenWidthDp.dp
        val screenHeightDp = configuration.screenHeightDp.dp
        
        // Calcula o espaço disponível considerando padding e elementos da UI
        val horizontalPadding = 32.dp // 16dp de cada lado
        val verticalPadding = 160.dp // espaço para título, botões, mensagens e espaçamentos
        
        val availableWidth = screenWidthDp - horizontalPadding
        val availableHeight = screenHeightDp - verticalPadding
        
        // Calcula o tamanho da célula baseado no espaço disponível
        // Considera padding entre células (2dp de cada lado = 4dp total por célula)
        val cellPadding = 4.dp
        val maxCellWidth = (availableWidth / colunas) - cellPadding
        val maxCellHeight = (availableHeight / linhas) - cellPadding
        
        // Usa o menor valor para manter células quadradas e garantir que caiba na tela
        val cellSize = minOf(maxCellWidth, maxCellHeight, 60.dp).coerceAtLeast(24.dp)
        
        // Calcula tamanho da fonte proporcional ao tamanho da célula
        val fontSizeValue = (cellSize.value * 0.4).coerceIn(10.0, 20.0)
        val fontSize = fontSizeValue.sp

        val reiniciarJogo: () -> Unit = {
            campo.value = Calculo.gerarTabuleiro(linhas, colunas, totalBombas)
            jogoIniciado.value = false
            gameOver.value = false
            vitoria.value = false
            showVictoryDialog.value = false
            try {
                soundManager.stopBackgroundMusic()
                soundManager.playBackgroundMusic(R.raw.war_theme)
            } catch (e: Exception) {
                println("Erro ao reiniciar música: ${e.message}")
            }
        }

        val onCellClick: (Celula) -> Unit = { celula ->
            if (gameOver.value || vitoria.value) return@let

            if (!jogoIniciado.value) {
                campo.value = Calculo.gerarTabuleiro(
                    linhas,
                    colunas,
                    totalBombas,
                    excluirCelula = celula
                )
                jogoIniciado.value = true

                val celulaGerada = campo.value[celula.linha][celula.coluna]
                Calculo.revelarCelula(celulaGerada, campo.value)

                if (Calculo.verificarVitoria(campo.value)) {
                    vitoria.value = true
                    showVictoryDialog.value = true
                    soundManager.stopBackgroundMusic()
                }
            } else {
                if (!celula.temMina.value) {
                    Calculo.revelarCelula(celula, campo.value)

                    if (Calculo.verificarVitoria(campo.value)) {
                        vitoria.value = true
                        showVictoryDialog.value = true
                        soundManager.stopBackgroundMusic()
                    }
                } else {
                    try {
                        soundManager.playBombSound(R.raw.bomb_explosion)
                    } catch (e: Exception) {
                        println("Som de bomba não encontrado: ${e.message}")
                    }
                    Calculo.revelarTudo(campo.value)
                    gameOver.value = true
                    soundManager.stopBackgroundMusic()
                }
            }
        }

        val onCellLongClick: (Celula) -> Unit = { celula ->
            if (!celula.revelada.value && !gameOver.value && !vitoria.value && jogoIniciado.value) {
                celula.marcada.value = !celula.marcada.value
            }
        }

        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CampoGrid(
                campo = campo.value,
                cellSize = cellSize,
                fontSize = fontSize,
                onCellClick = onCellClick,
                onCellLongClick = onCellLongClick
            )

            GameOverMessage(gameOver = gameOver.value)

            VictoryDialog(
                showVictoryDialog = showVictoryDialog.value,
                onPlayAgain = reiniciarJogo,
                onBackToMenu = {
                    showVictoryDialog.value = false
                    soundManager.stopBackgroundMusic()
                    navController.popBackStack()
                }
            )
        }
    }

    @Composable
    private fun CampoGrid(
        campo: List<MutableList<Celula>>,
        cellSize: androidx.compose.ui.unit.Dp,
        fontSize: androidx.compose.ui.unit.TextUnit,
        onCellClick: (Celula) -> Unit,
        onCellLongClick: (Celula) -> Unit
    ) {
        for (linha in campo) {
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
                            .size(cellSize)
                            .padding(2.dp)
                            .background(backgroundColor)
                            .combinedClickable(
                                onClick = { onCellClick(celula) },
                                onLongClick = { onCellLongClick(celula) }
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
                            fontSize = fontSize
                        )
                    }
                }
            }
        }
    }

    @Composable
    private fun GameOverMessage(gameOver: Boolean) {
        if (gameOver) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "💀 Game Over!",
                color = Color.Red,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }

    @Composable
    private fun VictoryDialog(
        showVictoryDialog: Boolean,
        onPlayAgain: () -> Unit,
        onBackToMenu: () -> Unit
    ) {
        if (!showVictoryDialog) return

        AlertDialog(
            onDismissRequest = { },
            title = {
                Text(
                    text = "Parabéns!",
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                )
            },
            text = {
                Text("Você venceu a partida. O que deseja fazer?")
            },
            confirmButton = {
                TextButton(onClick = onPlayAgain) {
                    Text("Jogar novamente")
                }
            },
            dismissButton = {
                TextButton(onClick = onBackToMenu) {
                    Text("Menu principal")
                }
            }
        )
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
                Play(navController, mode)
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
