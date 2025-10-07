============================================
Análise do Componente: Color.kt (Tema)
============================================

O arquivo :file:`Color.kt` está localizado no pacote ``ui.theme`` e é responsável por definir as constantes de cor utilizadas na composição do tema visual da aplicação, conforme as diretrizes do **Material Design 3 (M3)** no Jetpack Compose.

Objetivo e Estrutura
--------------------

.. code-block:: kotlin

    package com.example.campominado.ui.theme

    import androidx.compose.ui.graphics.Color

    val Purple80 = Color(0xFFD0BCFF)
    val PurpleGrey80 = Color(0xFFCCC2DC)
    val Pink80 = Color(0xFFEFB8C8)

    val Purple40 = Color(0xFF6650a4)
    val PurpleGrey40 = Color(0xFF625b71)
    val Pink40 = Color(0xFF7D5260)

* **Tipo**: Todas as cores são definidas como **`val`** (valores imutáveis) do tipo **``Color``** de Compose, garantindo a consistência e prevenindo alterações em tempo de execução.
* **Sistema de Cores**: O arquivo define seis cores principais, organizadas em dois grupos de três.

Convenção de Cores (Material Design 3)
--------------------------------------

As cores são nomeadas com sufixos **80** e **40**, o que é uma convenção padrão no Compose para dar suporte a temas claros e escuros:

* **Grupo ``80`` (Ex: ``Purple80``, ``Pink80``)**:
    * Cores mais claras e saturadas.
    * São tipicamente usadas para construir o **esquema de cores escuras** (Dark Theme), pois fornecem contraste adequado em fundos escuros.
    * Exemplo: ``Purple80`` (``#FFD0BCFF``) é uma tonalidade clara de roxo.

* **Grupo ``40`` (Ex: ``Purple40``, ``Pink40``)**:
    * Cores mais escuras e profundas.
    * São tipicamente usadas para construir o **esquema de cores claras** (Light Theme).
    * Exemplo: ``Purple40`` (``#FF6650a4``) é uma tonalidade escura de roxo.

Essa separação permite que o arquivo :file:`Theme.kt` (mencionado na estrutura de arquivos) defina e alterne entre os temas claro e escuro de forma limpa e organizada, usando a orientação a objetos para encapsular o design.
