============================================
Análise do Componente: MainMenuScreen.kt
============================================

O arquivo :file:`MainMenuScreen.kt` define a tela inicial da aplicação. Sua responsabilidade é clara: receber o usuário, exibir o título do jogo e permitir a seleção do modo de dificuldade, que desencadeia a navegação para a partida.

.. code-block:: kotlin

    internal class MainMenuScreen {
        // ...
    }

* **Classe**: ``MainMenuScreen``
* **Pacote**: ``com.example.campominado.ui.screens``
* **Função**: Apresentar as opções de dificuldade e orquestrar o início do jogo.

Função Create (Entrada da Tela)
-----------------------------

A função **``Create``** é o ponto de montagem do Composable, recebendo o controlador de navegação necessário para as transições de tela.

.. code-block:: kotlin

    @Composable
    public fun Create(navController: NavController) {
        val ui = ComponentesUI()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(22.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ui.CreateTitle("Campo Minado")
            // ... botões de navegação
        }
    }

* **Composição e Reuso**: A tela demonstra o reuso da classe **``ComponentesUI``** para garantir a consistência no título e nos botões.
* **Layout**: O layout utiliza um ``Column`` para centralizar verticalmente o conteúdo e aplica um espaçamento (`padding`) de `22.dp` em torno da tela.

Gerenciamento de Navegação (Seleção de Dificuldade)
--------------------------------------------------

A lógica de navegação utiliza o objeto **``NavController``** para transicionar para a tela de jogo, sendo um excelente exemplo de **passagem de dados através da rota**.

* **Botões de Dificuldade**:
    * A função ``ui.CreateButton`` é usada para criar três botões, um para cada nível de dificuldade: Fácil, Médio e Difícil.
* **Rotas com Parâmetros**: O bloco de clique de cada botão utiliza a função ``navController.navigate()``, passando a dificuldade como parte do caminho da URL:

::

    // Início da partida no Modo Fácil
    navController.navigate("game/easy")

    // Início da partida no Modo Médio
    navController.navigate("game/medium")

    // Início da partida no Modo Difícil
    navController.navigate("game/hard")

Essa implementação assume que o **``NavHost``** está configurado para reconhecer a rota ``"game/{mode}"`` e injetar o valor de `{mode}` (easy, medium, hard) como argumento para a tela de destino (:file:`GameScreen.kt`), que o recebe no seu parâmetro `mode`.
