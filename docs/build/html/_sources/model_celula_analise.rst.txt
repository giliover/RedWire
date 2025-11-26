=========================================
Análise do Modelo: Celula.kt
=========================================

O arquivo :file:`Celula.kt`, no pacote ``com.example.campominado.model``,
define a **unidade básica de estado** do jogo: uma célula do tabuleiro.

Definição da Classe
-------------------

.. code-block:: kotlin

    data class Celula(
        val linha: Int,
        val coluna: Int
    ) {
        val temMina = mutableStateOf(false)   // se há bomba
        val revelada = mutableStateOf(false)  // se foi clicada
        val marcada = mutableStateOf(false)   // se o jogador marcou bandeira
        var valor: Int = 0                    // número de bombas ao redor
    }

- **Tipo**: ``data class`` — fornece automaticamente `equals`, `hashCode` e `toString`.
- **Coordenadas**: ``linha`` e ``coluna`` representam a posição da célula na matriz.
- **Estados Reativos (Compose)**: ``temMina``, ``revelada`` e ``marcada`` são `mutableStateOf`, o que faz com que alterações nesses campos disparem recomposições da UI.

Propriedades de Estado
----------------------

- **``temMina``**: indica se há uma mina associada àquela célula; é usada tanto na lógica de geração (`Calculo.gerarTabuleiro`) quanto na verificação de *Game Over*.
- **``revelada``**: indica se a célula já foi aberta pelo jogador e evita que a mesma célula seja processada mais de uma vez (especialmente na recursão de ``revelarCelula``).
- **``marcada``**: representa a marcação com bandeira pelo jogador; em telas como :file:`GameScreen.kt`, controla quando exibir o ícone 🚩.
- **``valor``**: armazena o número de bombas adjacentes e é preenchido por ``Calculo.gerarTabuleiro`` usando ``contarBombasAdjacentes``.

Papel na Arquitetura
--------------------

- **Modelo de Domínio**: ``Celula`` é o modelo que representa o **estado de negócio** do jogo, independente de como a UI é desenhada.
- **Integração com UI**: a matriz `List<MutableList<Celula>>` é usada tanto por :file:`CampoMinadoGrid.kt` quanto por :file:`GameScreen.kt` para desenhar o tabuleiro.
- **Reatividade**: por ser baseada em `mutableStateOf`, qualquer mudança de estado é refletida automaticamente na camada de apresentação em Compose.

Enfoque em Orientação a Objetos
-------------------------------

- **Entidade de domínio**: ``Celula`` representa um elemento fundamental do domínio do jogo, combinando dados (posição, valor) e estado (revelada, marcada, tem mina).
- **Encapsulamento de estado da UI**: o uso de `mutableStateOf` conecta diretamente o modelo ao sistema reativo de Compose, mas mantém as flags agrupadas dentro da própria classe.
- **Data class como valor de negócio**: por ser uma `data class`, é fácil comparar e logar estados de célula, o que ajuda em testes e depuração.

Possíveis Melhorias OO
----------------------

- **Separar estado de UI do estado de domínio**: em arquiteturas mais puras, considerar separar um modelo de domínio puro (sem `mutableStateOf`) de um modelo de apresentação (ViewModel ou DTO), para que a regra de negócio não dependa de Compose.
- **Métodos de comportamento na própria classe**: adicionar métodos como ``revelar()``, ``marcar()``, ``possuiMina()``, em vez de deixar toda a manipulação para utilitários externos, reforçando o encapsulamento.
- **Imutabilidade onde possível**: avaliar uso de propriedades imutáveis para informações que não mudam após a criação (como linha, coluna e valor final), reduzindo a possibilidade de inconsistências.


