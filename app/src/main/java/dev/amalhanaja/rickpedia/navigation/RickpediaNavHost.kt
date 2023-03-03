package dev.amalhanaja.rickpedia.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import dev.amalhanaja.rickpedia.feature.home.navigation.HOME_ROUTE
import dev.amalhanaja.rickpedia.feature.home.navigation.homeScreen

@Composable
internal fun RickpediaNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    AnimatedNavHost(
        navController = navController,
        startDestination = HOME_ROUTE,
        modifier = modifier,
    ) {
        homeScreen()
    }
}
