package com.example.campominado.util
import kotlinx.coroutines.*
import java.util.concurrent.atomic.AtomicBoolean


internal class SafeClick {
    public fun Create(delayMillis: Long = 1000L, action: () -> Unit): () -> Unit {
        val canClick = AtomicBoolean(true)

        return {
            if (canClick.get()) {
                canClick.set(false)
                action()

                CoroutineScope(Dispatchers.Main).launch {
                    delay(delayMillis)
                    canClick.set(true)
                }
            }
        }
    }
}
