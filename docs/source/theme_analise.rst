============================================
Análise do Componente: Theme.kt (Implementação do Tema)
============================================

O arquivo :file:`Theme.kt`, localizado em ``ui.theme``, é responsável por implementar o sistema de temas do Material Design 3 (M3) no Jetpack Compose. Ele define os esquemas de cores para os temas claro e escuro e o principal Composable que aplica o tema a todo o conteúdo da aplicação.

Esquemas de Cores (Color Schemes)
----------------------------------

Os esquemas de cores utilizam as constantes definidas em :file:`Color.kt` para criar objetos ``ColorScheme``, que representam as cores funcionais da interface (primária, secundária, terciária, etc.).

* **Tema Escuro (DarkColorScheme)**:
    * Utiliza as cores mais claras (sufixo 80) para garantir um contraste adequado em fundos escuros.

.. code-block:: kotlin

    private val DarkColorScheme = darkColorScheme(
        primary = Purple80,
        secondary = PurpleGrey80,
        tertiary = Pink80
    )

* **Tema Claro (LightColorScheme)**:
    * Utiliza as cores mais escuras (sufixo 40).

.. code-block:: kotlin

    private val LightColorScheme = lightColorScheme(
        primary = Purple40,
        secondary = PurpleGrey40,
        tertiary = Pink40
    )

Composable CampoMinadoTheme
---------------------------

Esta é a função principal que envolve o conteúdo da aplicação, aplicando a lógica de seleção de tema.

.. code-block:: kotlin

    @Composable
    fun CampoMinadoTheme(
        darkTheme: Boolean = isSystemInDarkTheme(),
        dynamicColor: Boolean = true,
        content: @Composable () -> Unit
    ) {
        // ... Lógica de seleção de cores
    }

* **Seleção de Tema Dinâmico (Dynamic Color)**: O Composable implementa a lógica do Dynamic Color, que extrai cores do papel de parede do usuário para uso na aplicação.
    * A cor dinâmica é ativada se `dynamicColor` for `true` e a versão do Android for **12 (SDK_INT >= S)** ou superior.
    * Ele usa `dynamicDarkColorScheme(context)` ou `dynamicLightColorScheme(context)` para obter as cores do sistema.
* **Fallback**: Se a cor dinâmica não for aplicável (versão antiga do Android ou desativada), o tema é escolhido com base na preferência do sistema (`darkTheme`), caindo para `DarkColorScheme` ou `LightColorScheme`.
* **Aplicação Final**: A função empacota o conteúdo (`content: @Composable () -> Unit`) com o Composable **``MaterialTheme``**, injetando o ``colorScheme`` determinado e a tipografia (presumivelmente definida em :file:`Type.kt`).
