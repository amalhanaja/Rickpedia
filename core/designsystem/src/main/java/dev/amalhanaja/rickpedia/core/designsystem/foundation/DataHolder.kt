package dev.amalhanaja.rickpedia.core.designsystem.foundation

import androidx.compose.runtime.staticCompositionLocalOf

class DataHolder {

    private val mapHolder = hashMapOf<String, Any>()
    val lock = Any()

    fun <T : Any> holdData(key: String, data: T) {
        synchronized(lock) {
            mapHolder[key] = data
        }
    }

    fun <T : Any> consumeData(key: String): T? {
        return requireNotNull(mapHolder[key]) as? T
    }

    fun <T : Any> mustConsumeData(key: String): T {
        return requireNotNull(consumeData(key))
    }
}

val LocalDatatHolder = staticCompositionLocalOf { DataHolder() }
