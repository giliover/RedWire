package com.example.campominado.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.campominado.model.Celula

internal class CampoMinadoGrid {
    @Composable
    public fun Create(
        matriz: List<List<Celula>>,
        modifier: Modifier = Modifier
    ) {
        Column(modifier = modifier) {
            for (linha in matriz) {
                Row {
                    for (celula in linha) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .padding(2.dp)
                                .background(
                                    if (celula.revelada) Color.LightGray else Color.Gray
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = if (celula.revelada) celula.valor.toString() else "",
                                fontSize = 16.sp
                            )
                        }
                    }
                }
            }
        }
    }
}