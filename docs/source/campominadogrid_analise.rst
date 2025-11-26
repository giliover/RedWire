==============================================
Análise do Componente: CampoMinadoGrid.kt
==============================================

O arquivo :file:`CampoMinadoGrid.kt`, no pacote ``com.example.campominado.ui.screens``,
implementa um componente **reutilizável** responsável por desenhar a grade do
Campo Minado com base em uma matriz de ``Celula``.

Visão Geral
-----------

.. code-block:: kotlin

    internal class CampoMinadoGrid {

        @Composable
        fun Create(
            matriz: List<MutableList<Celula>>,
            modifier: Modifier = Modifier
        ) { ... }
    }

* **Responsabilidade**: Renderizar visualmente o tabuleiro, tratando cliques e
  long clicks em cada célula.
* **Entrada**: Recebe uma matriz de ``Celula`` já configurada pela lógica de
  negócio (``Calculo``).

Layout da Grade
---------------

.. code-block:: kotlin

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
                                    onClick = { ... },
                                    onLongClick = { ... }
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = if (celula.revelada.value && celula.valor > 0)
                                    celula.valor.toString() else "",
                                fontSize = 16.sp
                            )
                        }
                    }
                }
            }
        }
    }

* **Estrutura**:
  * Um ``Column`` externo contém um ``Row`` para cada linha da matriz.
  * Cada célula é representada por um ``Box`` com tamanho fixo e padding.
* **Cores**:
  * Célula revelada: ``Color.White``.
  * Célula com mina (não revelada): ``Color.LightGray``.
  * Célula fechada: ``Color.Gray``.

Interação: Clique e Long Click
------------------------------

* **Clique (onClick)**:

  .. code-block:: kotlin

      onClick = {
          if (celula.temMina.value) {
              Calculo.revelarTudo(matriz)
              println("Game Over!")
          } else {
              Calculo.revelarCelula(celula, matriz)
          }
      }

  * Revela a célula clicada.
  * Se tiver mina, chama ``revelarTudo`` e registra *Game Over* no console.

* **Clique Longo (onLongClick)**:

  .. code-block:: kotlin

      onLongClick = {
          celula.temMina.value = !celula.temMina.value
      }

  * Nesta versão, o long click inverte diretamente o estado de ``temMina``,
    funcionando mais como um exemplo didático de interação do que como a
    marcação clássica de bandeira.

Integração com a Lógica de Jogo
-------------------------------

* **Dependência**: Utiliza as funções do objeto ``Calculo`` para aplicar as
  regras do jogo (revelar célula, revelar tudo).
* **Separação de Responsabilidades**:
  ``CampoMinadoGrid`` cuida da apresentação e dos eventos de input.
  ``Calculo`` cuida das regras e atualiza o modelo ``Celula``.

Enfoque em Orientação a Objetos
-------------------------------

* **Componente de apresentação especializado**:
  ``CampoMinadoGrid`` é uma classe dedicada a representar visualmente uma coleção de ``Celula`` e tratar eventos de interação.
* **Colaboração clara entre objetos**:
  Trabalha em conjunto com ``Celula`` (modelo) e ``Calculo`` (regras), seguindo o padrão de objetos especializados que cooperam.
* **Encapsulamento de lógica de interação**:
  Toda a lógica de clique/long click e escolha de cores fica centralizada em um único lugar, facilitando manutenção e evolução.

Possíveis Melhorias OO
----------------------

* **Uso de um objeto \"Board\"**:
  Em vez de receber `List<MutableList<Celula>>` diretamente, receber um objeto de domínio (`Board`) que exponha operações como ``revelar(celula)`` e ``alternarMarcacao(celula)``, evitando que a grade precise conhecer detalhes de cálculo.
* **Separar estilização de lógica**:
  Criar uma pequena abstração para estilo de célula (por exemplo, ``CellStylePolicy``) para decidir cores e ícones, permitindo variar o tema do jogo sem mudar a lógica de interação.
* **Reduzir efeitos colaterais em UI**:
  Em vez de chamar diretamente `println("Game Over!")`, delegar a comunicação de eventos de alto nível (como fim de jogo) a um callback ou interface (`GameEvents`), mantendo o componente mais reutilizável.


