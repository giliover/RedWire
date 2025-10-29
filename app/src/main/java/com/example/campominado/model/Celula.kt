package com.example.campominado.model

import androidx.compose.runtime.mutableStateOf

data class Celula(
    val linha: Int,
    val coluna: Int
) {
    val temMina = mutableStateOf(false)   // se há bomba
    val revelada = mutableStateOf(false)  // se foi clicada
    val marcada = mutableStateOf(false)   // se o jogador marcou bandeira
    var valor: Int = 0                    // número de bombas ao redor
}
