==========================================
Análise do Utilitário: Calculo.kt
==========================================

O arquivo :file:`Calculo.kt`, localizado no pacote ``com.example.campominado.util``,
centraliza toda a **lógica de regras do jogo** Campo Minado. Em vez de espalhar
regras pelas telas, ele concentra o cálculo do tabuleiro, a contagem de bombas
adjacentes e as operações de revelar células.

Visão Geral
-----------

.. code-block:: kotlin

    object Calculo {
        fun gerarTabuleiro(
            linhas: Int,
            colunas: Int,
            totalBombas: Int
        ): List<MutableList<Celula>> { ... }

        private fun contarBombasAdjacentes(
            campo: List<MutableList<Celula>>,
            linha: Int,
            coluna: Int
        ): Int { ... }

        fun revelarCelula(celula: Celula, campo: List<MutableList<Celula>>) { ... }

        fun revelarTudo(campo: List<MutableList<Celula>>) { ... }
    }

* **Tipo**: ``object`` (Singleton em Kotlin), garantindo uma única instância global.
* **Responsabilidade**: Implementar as regras de geração e atualização do tabuleiro.
* **Colaboração**: Trabalha diretamente com o modelo :file:`Celula.kt`.

Função gerarTabuleiro
---------------------

Esta função cria a matriz de células e posiciona as bombas de forma aleatória.

.. code-block:: kotlin

    fun gerarTabuleiro(
        linhas: Int,
        colunas: Int,
        totalBombas: Int
    ): List<MutableList<Celula>> {
        val campo = List(linhas) { linha ->
            MutableList(colunas) { coluna ->
                Celula(linha = linha, coluna = coluna)
            }
        }

        var bombasColocadas = 0
        while (bombasColocadas < totalBombas) {
            val i = Random.nextInt(linhas)
            val j = Random.nextInt(colunas)
            val celula = campo[i][j]
            if (!celula.temMina.value) {
                celula.temMina.value = true
                bombasColocadas++
            }
        }

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

* **Criação do Campo**: Usa `List` e `MutableList` para criar uma grade de ``Celula``.
* **Posicionamento Aleatório de Bombas**: Gera índices aleatórios (`Random.nextInt`) até
  que o número desejado de bombas seja atingido.
* **Cálculo de Vizinhança**: Após posicionar as bombas, calcula o valor de cada célula
  (quantidade de minas adjacentes).

Função contarBombasAdjacentes
-----------------------------

Esta função privada varre a vizinhança 3x3 de uma célula e soma quantas minas existem.

.. code-block:: kotlin

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

* **Controle de Bordas**: Os operadores ``in campo.indices`` evitam acessos fora dos limites.
* **Reuso**: A função é usada em ``gerarTabuleiro`` para preencher o campo com valores.

Função revelarCelula
--------------------

Responsável por revelar uma célula e, se ela for vazia (valor 0 e sem mina),
revelar recursivamente suas vizinhas.

.. code-block:: kotlin

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

* **Regra de Negócio**: Não revela células já reveladas ou marcadas.
* **Propagação**: Implementa o comportamento clássico do Campo Minado, onde
  áreas vazias "abrem" automaticamente.

Função revelarTudo
-------------------

Usada em situações de *Game Over* para mostrar todo o campo.

.. code-block:: kotlin

    fun revelarTudo(campo: List<MutableList<Celula>>) {
        for (linha in campo) {
            for (celula in linha) {
                celula.revelada.value = true
            }
        }
    }

* **Uso Típico**: Chamado quando o jogador aciona uma bomba.
* **Feedback Visual**: Permite que o jogador veja a distribuição de bombas
  após perder a partida.

Enfoque em Orientação a Objetos
-------------------------------

* **Objeto singleton de domínio**:
  ``Calculo`` é declarado como ``object``, funcionando como um serviço de regras compartilhado em toda a aplicação.
* **Separação de preocupações**:
  A geração de tabuleiro, cálculo de vizinhança e regras de revelação estão isoladas da camada de UI, reforçando o papel de **modelo de domínio**.
* **Colaboração com o modelo**:
  Trabalha diretamente com a classe ``Celula``, modificando seu estado de forma consistente e previsível.

Possíveis Melhorias OO
----------------------

* **Evitar estado implícito em singletons**:
  Em contextos mais complexos, considerar transformar ``Calculo`` em uma classe injetável (por exemplo, ``BoardCalculator``), o que permite múltiplas instâncias com estratégias diferentes de tabuleiro.
* **Modelar regras em tipos dedicados**:
  Extrair conceitos como \"configuração de jogo\" (linhas, colunas, bombas) para classes específicas (`GameConfig`, `BoardSize`, `BombCount`), em vez de espalhar inteiros crus.
* **Retornar objetos de domínio mais ricos**:
  Em vez de trabalhar diretamente com `List<MutableList<Celula>>` em todos os lugares, encapsular o tabuleiro em uma classe (`Board`) que ofereça operações de alto nível (revelar, marcar, verificar vitória), delegando a ``Calculo`` apenas o trabalho puramente algorítmico.


