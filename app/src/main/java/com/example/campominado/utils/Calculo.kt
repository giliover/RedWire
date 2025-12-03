package com.example.campominado.util

import com.example.campominado.model.Celula
import kotlin.random.Random

object Calculo {

    fun gerarTabuleiro(
        linhas: Int,
        colunas: Int,
        totalBombas: Int,
        excluirCelula: Celula? = null
    ): List<MutableList<Celula>> {
        val campo = List(linhas) { linha ->
            MutableList(colunas) { coluna ->
                Celula(linha = linha, coluna = coluna)
            }
        }

        // posiciona as bombas aleatoriamente, excluindo a célula do primeiro clique
        var bombasColocadas = 0
        while (bombasColocadas < totalBombas) {
            val i = Random.nextInt(linhas)
            val j = Random.nextInt(colunas)
            val celula = campo[i][j]
            
            val deveExcluir = excluirCelula != null && (
                (i == excluirCelula.linha && j == excluirCelula.coluna) ||
                (kotlin.math.abs(i - excluirCelula.linha) <= 1 && 
                 kotlin.math.abs(j - excluirCelula.coluna) <= 1)
            )
            
            if (!celula.temMina.value && !deveExcluir) {
                celula.temMina.value = true
                bombasColocadas++
            }
        }

        // calcula valores ao redor das bombas
        for (i in 0 until linhas) {
            for (j in 0 until colunas) {
                val celula = campo[i][j]
                if (!celula.temMina.value) {
                    celula.valor = contarBombasAdjacentes(campo, i, j)
                }
            }
        }

        return campo
    }

    private fun contarBombasAdjacentes(
        campo: List<MutableList<Celula>>,
        linha: Int,
        coluna: Int
    ): Int {
        var count = 0
        for (i in (linha - 1)..(linha + 1)) {
            for (j in (coluna - 1)..(coluna + 1)) {
                if (i in campo.indices && j in campo[0].indices) {
                    if (campo[i][j].temMina.value) count++
                }
            }
        }
        return count
    }

    fun revelarCelula(celula: Celula, campo: List<MutableList<Celula>>) {
        if (celula.revelada.value || celula.marcada.value) return
        celula.revelada.value = true

        if (celula.valor == 0 && !celula.temMina.value) {
            for (i in (celula.linha - 1)..(celula.linha + 1)) {
                for (j in (celula.coluna - 1)..(celula.coluna + 1)) {
                    if (i in campo.indices && j in campo[0].indices) {
                        revelarCelula(campo[i][j], campo)
                    }
                }
            }
        }
    }

    fun revelarTudo(campo: List<MutableList<Celula>>) {
        for (linha in campo) {
            for (celula in linha) {
                celula.revelada.value = true
            }
        }
    }

    fun verificarVitoria(campo: List<MutableList<Celula>>): Boolean {
        var totalCelulas = 0
        var celulasSemBombaReveladas = 0
        
        for (linha in campo) {
            for (celula in linha) {
                totalCelulas++
                if (!celula.temMina.value && celula.revelada.value) {
                    celulasSemBombaReveladas++
                }
            }
        }
        
        // Conta quantas células não têm bomba
        val totalCelulasSemBomba = totalCelulas - campo.sumOf { linha -> 
            linha.count { it.temMina.value } 
        }
        
        // Vitória: todas as células sem bomba foram reveladas
        return celulasSemBombaReveladas == totalCelulasSemBomba
    }
}
