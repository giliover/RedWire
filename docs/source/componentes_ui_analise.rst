============================================
Análise do Componente: ComponentesUI.kt
============================================

O arquivo :file:`ComponentesUI.kt` é uma implementação direta do paradigma de reuso e composição em **Jetpack Compose**. A classe **``ComponentesUI``** centraliza a criação de elementos de interface comuns, garantindo a consistência visual e a padronização do comportamento em todo o aplicativo.

Classes e Localização
---------------------

.. code-block:: kotlin

    internal class ComponentesUI {
        // ... funções composable
    }

* **Classe**: ``ComponentesUI``
* **Pacote**: `com.example.campominado.ui.screens`
* **Visibilidade**: A classe é marcada como `internal`, sugerindo que seu uso está restrito ao módulo do aplicativo.
* **Propósito**: Oferecer um conjunto de funções **`@Composable`** que podem ser chamadas pelas telas (como :file:`MainMenuScreen.kt` ou :file:`GameScreen.kt`) para renderizar elementos padronizados.

Função CreateTitle
------------------

Esta função é responsável por criar um componente de texto formatado como título.

.. code-block:: kotlin

    @Composable
    public fun CreateTitle(text: String) {
        Text(
            text = text,
            style = MaterialTheme.typography.headlineLarge.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 32.sp
            ),
            color = MaterialTheme.colorScheme.primary
        )
    }

* **Reuso de Estilo**: Utiliza o tema do Material Design (`MaterialTheme.typography.headlineLarge`) e o personaliza (cópia) com **negrito** e tamanho de fonte fixo (`32.sp`).
* **Consistência**: Garante que todos os títulos no aplicativo tenham o mesmo estilo, e a cor seja a primária do tema.

Função CreateButton (Orientação a Objetos e Prevenção de Múltiplos Cliques)
------------------------------------------------------------------------

O componente de botão demonstra um conceito importante de **Orientação a Objetos (OO)** ao delegar a lógica de prevenção de múltiplos cliques a uma classe utilitária externa:

.. code-block:: kotlin

    @Composable
    public fun CreateButton(text: String, onClick: () -> Unit) {
        var safeClick = SafeClick()

        Button(onClick = safeClick.Create() { onClick() }) {
            Text(text)
        }
    }

* **Encapsulamento de Comportamento**: Em vez de implementar a lógica de *debounce* (prevenção de cliques rápidos) diretamente na função Composable, ela instancia a classe **``SafeClick``** (importada de :file:`com.example.campominado.util.SafeClick`).
* **Delegação**: A chamada `safeClick.Create() { onClick() }` utiliza o objeto `SafeClick` para interceptar a ação de clique.
* **Vantagem da OO**: Essa abordagem garante que a lógica de "clique seguro" seja **reutilizável** em qualquer botão do app e **testável** isoladamente, sem poluir o código do componente visual.
