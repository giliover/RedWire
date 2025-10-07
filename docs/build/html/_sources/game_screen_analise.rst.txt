============================================
Análise do Componente: GameScreen.kt
============================================

O arquivo :file:`GameScreen.kt` define a tela principal de jogabilidade do Campo Minado. Como parte do pacote ``ui.screens``, ele é responsável por orquestrar o layout, exibir a lógica do jogo (função ``Play``) e gerenciar a navegação de saída.

.. code-block:: kotlin

    internal class GameScreen {
        // ...
    }

* **Classe**: ``GameScreen``
* **Pacote**: ``com.example.campominado.ui.screens``
* **Função**: Criar a interface de usuário (UI) da partida.

Função Play (Lógica de Jogo)
---------------------------

.. code-block:: kotlin

    @Composable
    private fun Play() {
        // run game
    }

* **Visibilidade**: A função é marcada como **`private`**, indicando que a implementação da renderização do tabuleiro e a interação de jogo estão **encapsuladas** dentro da própria classe ``GameScreen``.
* **Responsabilidade**: Este é o ponto onde o objeto **``Board``** e a lógica de jogo (possivelmente gerenciada por um **``GameManager``**) seriam renderizados e executados, aplicando o princípio de *separação de preocupações* (SRP).

Função Create (Entrada da Tela)
-----------------------------

Esta é a função Composable pública que é chamada pelo **``NavHost``** para exibir a tela.

.. code-block:: kotlin

    @Composable
    public fun Create(navController: NavController, mode: String) {
        val ui = ComponentesUI()
        // ... layout e componentes
    }

* **Parâmetros**:
    * **``navController: NavController``**: Objeto de navegação do Jetpack, essencial para controlar o fluxo de telas (ex: voltar para o menu).
    * **``mode: String``**: Recebe o modo de jogo (ex: "Fácil", "Médio"), permitindo que a tela se configure dinamicamente.
* **Composição de UI**: A classe **``ComponentesUI``** é instanciada e utilizada para manter a consistência visual:
    * **Título**: Exibe o modo de jogo (`ui.CreateTitle("Modo de jogo: $mode")`).
    * **Botão de Retorno**:
        * Utiliza `ui.CreateButton`.
        * A lógica de clique (`onClick`) é **`{ navController.popBackStack() }`**, que remove a tela atual da pilha de navegação e retorna à tela anterior (provavelmente :file:`MainMenuScreen.kt`).
* **Layout**: A tela usa um `Column` que preenche todo o espaço (`fillMaxSize`) e centraliza o conteúdo vertical e horizontalmente, garantindo um design responsivo.
