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

Enfoque em Orientação a Objetos
-------------------------------

- **Encapsulamento da lógica da partida**: a função privada ``Play`` concentra a renderização e a interação do tabuleiro, escondendo detalhes de implementação do mundo externo.
- **Composição de componentes**: a tela delega a criação de elementos comuns (título, botão voltar, background) para ``ComponentesUI``, aplicando o princípio de reuso de objetos.
- **Colaboração entre objetos**: ``GameScreen`` colabora com o utilitário de navegação (via ``NavController``), com a lógica de tabuleiro (``Calculo`` e/ou ``Celula``) e com serviços auxiliares como som (``SoundManager`` em código mais recente), reforçando o padrão de objetos especializados que cooperam.

Possíveis Melhorias OO
----------------------

- **Isolar regras de jogo em um \"Game\" ou \"GameSession\"**: extrair o controle de estado da partida (vitória, derrota, bombas restantes, tempo) para uma classe de domínio (por exemplo, ``GameSession``), deixando a tela responsável apenas por exibir o estado.
- **Substituir literais de modo por tipo forte**: introduzir um tipo (``enum class GameMode``) ou classe de valor em vez de ``mode: String``, encapsulando configurações como linhas, colunas e bombas em um lugar único.
- **Compor uma grade reutilizável**: integrar explicitamente o componente ``CampoMinadoGrid`` (já existente) como parte da tela, para separar completamente a renderização da malha (grid) da lógica de UI geral (botões, título, navegação).
