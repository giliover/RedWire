package com.example.campominado.util

import android.content.Context
import android.media.MediaPlayer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext

class SoundManager(private val context: Context) {
    private var backgroundMusic: MediaPlayer? = null
    private var bombSound: MediaPlayer? = null
    
    fun playBackgroundMusic(resourceId: Int) {
        try {
            stopBackgroundMusic()
            backgroundMusic = MediaPlayer.create(context, resourceId)
            backgroundMusic?.apply {
                isLooping = true
                setVolume(0.5f, 0.5f)
                start()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    
    fun stopBackgroundMusic() {
        backgroundMusic?.release()
        backgroundMusic = null
    }
    
    fun playBombSound(resourceId: Int) {
        try {
            bombSound?.release()
            bombSound = MediaPlayer.create(context, resourceId)
            bombSound?.apply {
                setVolume(1.0f, 1.0f)
                start()
                setOnCompletionListener {
                    it.release()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    
    fun release() {
        stopBackgroundMusic()
        bombSound?.release()
        bombSound = null
    }
}

@Composable
fun rememberSoundManager(): SoundManager {
    val context = LocalContext.current
    val soundManager = remember { SoundManager(context) }
    
    DisposableEffect(soundManager) {
        onDispose {
            soundManager.release()
        }
    }
    
    return soundManager
}


