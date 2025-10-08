============================================
Análise do Componente: NavHost.kt
============================================

O arquivo :file:`NavHost.kt`, localizado no pacote ``com.example.campominado.util``, define o grafo de navegação completo da aplicação, utilizando a biblioteca **Jetpack Navigation com Compose**. Esta classe encapsula toda a lógica de roteamento, separando-a da *Activity* principal e das telas.

.. code-block:: kotlin

    internal class NavHost {
        @Composable
        public fun Create() {
            // ...
        }
    }

* **Classe**: ``NavHost``
* **Visibilidade**: É uma classe `internal`, visível apenas dentro do módulo de compilação.
* **Função Principal**: A função **``Create()``** é a única Composable e é responsável por construir e gerenciar o estado do controlador de navegação.

Grafo de Navegação (Navigation Graph)
-------------------------------------

O método ``Create()`` inicializa o ``NavHostController`` e define as rotas:

.. code-block:: kotlin

    @Composable
    public fun Create() {
        val navController: NavHostController = rememberNavController()

        NavHost(navController = navController, startDestination = "mainmenu") {
            // ... Definição das rotas
        }
    }

* **``rememberNavController()``**: Cria e lembra o estado do controlador de navegação através das recomposições.
* **``startDestination = "mainmenu"``**: Define a rota inicial que será carregada quando o ``NavHost`` for montado, garantindo que o aplicativo comece na tela do menu principal.

Rotas Implementadas
-------------------

O grafo de navegação é composto por dois destinos principais definidos pela diretiva ``composable``:

1.  **Rota ``mainmenu``**:
    * **Caminho**: `"mainmenu"`
    * **Ação**: Instancia e chama a função ``Create`` da classe **``MainMenuScreen``**, passando o ``navController`` para que o menu possa navegar para outras telas.

2.  **Rota de Jogo com Argumentos**:
    * **Caminho**: `"game/{mode}"`
    * **Argumento Necessário**: A rota espera um argumento chamado **``mode``** na URL (ex: `game/easy`).
    * **Extração de Dados**: O valor do modo é extraído da `backStackEntry` usando ``backStackEntry.arguments?.getString("mode")``.
    * **Valor Padrão**: Caso o argumento ``mode`` esteja ausente, o valor padrão é definido como `"easy"`.
    * **Ação**: Instancia e chama a função ``Create`` da classe **``GameScreen``**, passando o ``navController`` e o argumento ``mode`` extraído.

**Princípio da Orientação a Objetos (OO)**: A classe ``NavHost`` serve como um **Controlador** (Controller) que gerencia o fluxo de trabalho (Work Flow), instanciando e orquestrando as classes de UI (**``MainMenuScreen``** e **``GameScreen``**) e garantindo que cada tela receba o contexto de navegação necessário.
