package com.example.campominado.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.campominado.R
import com.example.campominado.util.SafeClick

internal class ComponentesUI {

    // 🔹 Título padrão
    @Composable
    fun CreateTitle(text: String) {
        Text(
            text = text,
            style = MaterialTheme.typography.headlineLarge.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 32.sp
            ),
            color = MaterialTheme.colorScheme.primary
        )
    }

    // 🔹 Botão com clique seguro
    @Composable
    fun CreateButton(text: String, onClick: () -> Unit) {
        val safeClick = SafeClick()
        Button(onClick = safeClick.Create() { onClick() }) {
            Text(text)
        }
    }

    // 🔹 NOVO: Fundo reutilizável com imagem
    @Composable
    fun CreateBackground(
        modifier: Modifier = Modifier,
        imageResId: Int = R.drawable.background, // imagem padrão
        overlayColor: Color = Color(0x33000000), // camada transparente opcional
        content: @Composable () -> Unit
    ) {
        Box(modifier = modifier.fillMaxSize()) {
            // Imagem de fundo
            Image(
                painter = painterResource(id = imageResId),
                contentDescription = "Fundo da tela",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            // Camada semitransparente
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(overlayColor)
            )

            // Conteúdo sobre o fundo
            content()
        }
    }
}
