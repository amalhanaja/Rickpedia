package dev.amalhanaja.rickpedia

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.amalhanaja.rickpedia.core.designsystem.foundation.DataHolder
import dev.amalhanaja.rickpedia.core.designsystem.foundation.LocalDatatHolder
import dev.amalhanaja.rickpedia.core.designsystem.foundation.RickpediaTheme
import dev.amalhanaja.rickpedia.navigation.RickpediaNavHost


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberAnimatedNavController()
            CompositionLocalProvider(
                LocalDatatHolder provides DataHolder(),
            ) {
                RickpediaTheme {
                    RickpediaNavHost(
                        navController = navController,
                        modifier = Modifier.fillMaxSize(),
                    )
                }
            }
        }
    }
}
