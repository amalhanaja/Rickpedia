package dev.amalhanaja.rickpedia.feature.detail.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.animation.composable
import dev.amalhanaja.rickpedia.core.designsystem.foundation.DataHolder
import dev.amalhanaja.rickpedia.core.designsystem.foundation.LocalDatatHolder
import dev.amalhanaja.rickpedia.core.model.Character
import dev.amalhanaja.rickpedia.feature.detail.DetailRoute


fun NavController.goToDetail(character: Character, dataHolder: DataHolder) {
    dataHolder.holdData("detail", character)
    navigate(route = "detail")
}

fun NavGraphBuilder.detailScreen(
    onBack: () -> Unit,
) {
    composable(route = "detail") {
        val dataHolder = LocalDatatHolder.current
        DetailRoute(character = dataHolder.mustConsumeData("detail"), detailViewModel = hiltViewModel(), onBack = onBack)
    }
}
