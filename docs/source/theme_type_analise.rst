============================================
Análise do Componente: Type.kt (Tipografia)
============================================

O arquivo :file:`Type.kt`, localizado no pacote ``ui.theme``, é responsável por definir os estilos tipográficos (fontes, tamanhos, pesos) da aplicação, aderindo às diretrizes do **Material Design 3 (M3)** no Jetpack Compose.

Objeto Typography
--------------------

O objeto principal é um **``val``** nomeado **``Typography``** do tipo ``androidx.compose.material3.Typography``, que é então injetado no ``MaterialTheme`` (definido em :file:`Theme.kt`).

.. code-block:: kotlin

    package com.example.campominado.ui.theme

    import androidx.compose.material3.Typography
    import androidx.compose.ui.text.TextStyle
    import androidx.compose.ui.text.font.FontFamily
    import androidx.compose.ui.text.font.FontWeight
    import androidx.compose.ui.unit.sp

    // Set of Material typography styles to start with
    val Typography = Typography(
        bodyLarge = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            lineHeight = 24.sp,
            letterSpacing = 0.5.sp
        )
        // ... Outros estilos comentados
    )

Estilo bodyLarge
----------------

A única propriedade de estilo sobrescrita explicitamente é ``bodyLarge``, que define o estilo padrão para a maioria dos textos de corpo na aplicação.

* **``fontFamily``**: ``FontFamily.Default``.
* **``fontWeight``**: ``FontWeight.Normal`` (Regular).
* **``fontSize``**: ``16.sp`` (Tamanho padrão e recomendado para texto de corpo M3).
* **``lineHeight``**: ``24.sp``.
* **``letterSpacing``**: ``0.5.sp``.

Benefício
-----------

Ao centralizar a tipografia em um objeto **``Typography``**, a aplicação garante que todos os componentes que utilizam estilos do tema (ex: `MaterialTheme.typography.bodyLarge`) herdem a mesma configuração, mantendo a consistência do design visual. Os estilos não sobrescritos (como `titleLarge` ou `labelSmall`) mantêm seus valores padrão do Material Design.
