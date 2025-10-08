package com.example.campominado.model

data class Celula(
    val linha: Int,
    val coluna: Int,
    var revelada: Boolean = false,
    var temMina: Boolean = false,
    var valor: Int = 0
)
