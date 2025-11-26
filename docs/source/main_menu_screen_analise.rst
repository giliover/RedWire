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
    A função ``ui.CreateButton`` é usada para criar três botões, um para cada nível de dificuldade: Fácil, Médio e Difícil.
* **Rotas com Parâmetros**: O bloco de clique de cada botão utiliza a função ``navController.navigate()``, passando a dificuldade como parte do caminho da URL:

::

    // Início da partida no Modo Fácil
    navController.navigate("game/easy")

    // Início da partida no Modo Médio
    navController.navigate("game/medium")

    // Início da partida no Modo Difícil
    navController.navigate("game/hard")

Essa implementação assume que o **``NavHost``** está configurado para reconhecer a rota ``"game/{mode}"`` e injetar o valor de `{mode}` (easy, medium, hard) como argumento para a tela de destino (:file:`GameScreen.kt`), que o recebe no seu parâmetro `mode`.

Enfoque em Orientação a Objetos
-------------------------------

* **Encapsulamento da tela**: ``MainMenuScreen`` agrupa em uma única classe toda a lógica visual e de interação do menu, expondo apenas o método composable ``Create``.
* **Composição de objetos**:
  Utiliza ``ComponentesUI`` para criar botões e título, evitando duplicação de código visual.
  Depende de um ``NavController`` para executar navegação, sem conhecer detalhes internos de como a navegação é implementada.
* **Baixo acoplamento com a lógica de jogo**:
  A tela apenas envia o parâmetro de dificuldade na rota; quem interpreta o parâmetro é a tela de jogo (`GameScreen`), mantendo responsabilidades bem separadas.

Possíveis Melhorias OO
----------------------

* **Separar modelo de configuração de modos**:
  Criar um modelo (por exemplo, ``GameMode``) que represente as opções de dificuldade, em vez de strings literais (`"easy"`, `"medium"`, `"hard"`), reduzindo risco de erro de digitação e centralizando regras de cada modo.
* **Desacoplar navegação de string de rota**:
  Introduzir um objeto de rotas ou um serviço (por exemplo, ``GameNavigator``) responsável por construir as rotas (`toGame(mode)`), para que a tela apenas invoque métodos fortemente tipados.
* **Configurar ações como dependências**:
  Em vez de chamar ``navController.navigate(...)`` diretamente, receber lambdas ou interfaces que representem ações de \"começar jogo fácil/médio/difícil\", o que facilita testes unitários da tela sem precisar de um ``NavController`` real.
