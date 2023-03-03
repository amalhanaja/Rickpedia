package dev.amalhanaja.rickpedia.feature.home.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.animation.composable
import dev.amalhanaja.rickpedia.core.model.Character
import dev.amalhanaja.rickpedia.feature.home.HomeRoute

const val HOME_ROUTE = "home"

fun NavGraphBuilder.homeScreen(onClickCharacter: (Character) -> Unit) {
    composable(route = HOME_ROUTE) {
        HomeRoute(homeViewModel = hiltViewModel(), onClickCharacter = onClickCharacter)
    }
}
