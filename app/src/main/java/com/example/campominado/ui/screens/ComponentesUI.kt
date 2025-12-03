package com.example.campominado.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.shape.RoundedCornerShape
import com.example.campominado.R
import com.example.campominado.util.SafeClick

internal class ComponentesUI {

    // 🔹 Título padrão (usado, por exemplo, no menu principal)
    @Composable
    fun CreateTitle(text: String) {
        Text(
            text = text,
            style = MaterialTheme.typography.headlineLarge.copy(
                fontWeight = FontWeight.ExtraBold,
                fontSize = 40.sp
            ),
            color = MaterialTheme.colorScheme.primary
        )
    }

    // 🔹 Subtítulo opcional (por exemplo, "Campo Minado")
    @Composable
    fun CreateSubtitle(text: String) {
        Text(
            text = text,
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Medium,
                fontSize = 18.sp
            ),
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.85f)
        )
    }

    // 🔹 Botão com clique seguro e visual mais moderno
    @Composable
    fun CreateButton(text: String, onClick: () -> Unit) {
        val safeClick = SafeClick()

        Button(
            onClick = safeClick.Create() { onClick() },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(28.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ),
            elevation = ButtonDefaults.buttonElevation(
                defaultElevation = 6.dp,
                pressedElevation = 2.dp
            )
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.labelLarge.copy(
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp
                )
            )
        }
    }

    // 🔹 Fundo reutilizável com imagem
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
