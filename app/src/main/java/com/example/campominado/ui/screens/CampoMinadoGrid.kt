package com.example.campominado.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.campominado.model.Celula
import com.example.campominado.util.Calculo

internal class CampoMinadoGrid {

    @Composable
    fun Create(
        matriz: List<MutableList<Celula>>,
        modifier: Modifier = Modifier
    ) {
        Column(modifier = modifier) {
            for (linha in matriz) {
                Row {
                    for (celula in linha) {
                        val backgroundColor = when {
                            celula.revelada.value -> Color.White
                            celula.temMina.value -> Color.LightGray
                            else -> Color.Gray
                        }

                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .padding(2.dp)
                                .background(backgroundColor)
                                .combinedClickable(
                                    onClick = {
                                        if (celula.temMina.value) {
                                            // Game Over
                                            Calculo.revelarTudo(matriz)
                                            println("Game Over!")
                                        } else {
                                            Calculo.revelarCelula(celula, matriz)
                                        }
                                    },
                                    onLongClick = {
                                        celula.temMina.value = !celula.temMina.value
                                    }
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = if (celula.revelada.value && celula.valor > 0) celula.valor.toString() else "",
                                fontSize = 16.sp
                            )
                        }
                    }
                }
            }
        }
    }
}
