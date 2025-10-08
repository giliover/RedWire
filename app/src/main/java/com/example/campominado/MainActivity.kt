package com.example.campominado

import android.os.Bundle

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen

import androidx.lifecycle.lifecycleScope

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

import com.example.campominado.ui.theme.CampoMinadoTheme


import com.example.campominado.util.NavHost

internal class MainActivity : ComponentActivity() {
    private var isLoading by mutableStateOf(true)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val splashScreen = installSplashScreen()
        splashScreen.setKeepOnScreenCondition { isLoading }

        lifecycleScope.launch {
            delay(400)
            isLoading = false
        }

        enableEdgeToEdge()

        setContent {
            if (!isLoading) {
                CampoMinadoTheme {
                    var navHost = NavHost()
                    navHost.Create()
                }   
            }
        }
    }
}