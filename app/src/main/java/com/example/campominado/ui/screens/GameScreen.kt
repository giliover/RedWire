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
import androidx.compose.foundation.layout.padding

import com.example.campominado.ui.screens.CampoMinadoGrid

import com.example.campominado.model.Celula

internal class GameScreen {
    private fun <T> gerarMatriz(
        n: Int,
        m: Int,
        createCell: (linha: Int, coluna: Int) -> T
    ): List<List<T>> {
        require(m % 2 == 0) { "O número de colunas (m) deve ser par." }

        return List(n) { linha ->
            List(m) { coluna ->
                createCell(linha, coluna)
            }
        }
    }


    @Composable
    private fun Play(mode: String) {
        var x = 10
        var y = 10

        if (mode == "easy") {
            x = 10 
            y = 10 
        }

        if (mode == "medium") {
            x = 10 
            y = 12 
        }
        
        if (mode == "hard") {
            x = 10 
            y = 14 
        }

        val campo = gerarMatriz(x, y) { linha, coluna ->
            Celula(linha = linha, coluna = coluna)
        }

        var campoMinadoGrid = CampoMinadoGrid()
        campoMinadoGrid.Create(matriz = campo, modifier = Modifier.padding(16.dp))
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

            Play(mode)

            Spacer(modifier = Modifier.height(16.dp))

            ui.CreateButton(onClick = { navController.popBackStack() }, text = "Voltar")
        }

    }

}
