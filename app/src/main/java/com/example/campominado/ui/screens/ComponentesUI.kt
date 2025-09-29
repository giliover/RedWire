package com.example.campominado.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.material3.*
import androidx.compose.foundation.layout.*
import com.example.campominado.util.SafeClick


internal class ComponentesUI {
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

    @Composable
    public fun CreateButton(text: String, onClick: () -> Unit) {
        var safeClick = SafeClick()

        Button(onClick = safeClick.Create() { onClick() }) {
            Text(text)
        }
    }
}
