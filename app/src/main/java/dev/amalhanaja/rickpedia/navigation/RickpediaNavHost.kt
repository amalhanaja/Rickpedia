package dev.amalhanaja.rickpedia.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import dev.amalhanaja.rickpedia.core.designsystem.foundation.LocalDatatHolder
import dev.amalhanaja.rickpedia.feature.detail.navigation.detailScreen
import dev.amalhanaja.rickpedia.feature.detail.navigation.goToDetail
import dev.amalhanaja.rickpedia.feature.home.navigation.HOME_ROUTE
import dev.amalhanaja.rickpedia.feature.home.navigation.homeScreen

@Composable
internal fun RickpediaNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    val dataHolder = LocalDatatHolder.current
    AnimatedNavHost(
        navController = navController,
        startDestination = HOME_ROUTE,
        modifier = modifier,
    ) {
        homeScreen(onClickCharacter = { navController.goToDetail(it, dataHolder) })
        detailScreen(onBack = { navController.popBackStack() })
    }
}
