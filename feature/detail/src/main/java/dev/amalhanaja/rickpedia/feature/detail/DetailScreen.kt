package dev.amalhanaja.rickpedia.feature.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import dev.amalhanaja.rickpedia.core.designsystem.foundation.RickpediaTheme
import dev.amalhanaja.rickpedia.core.model.Character
import dev.amalhanaja.rickpedia.core.model.Episode

@Composable
fun DetailRoute(
    character: Character,
    detailViewModel: DetailViewModel,
    onBack: () -> Unit,
) {
    val episodeUiState by detailViewModel.episodeUiState.collectAsState()
    LaunchedEffect(Unit) {
        detailViewModel.fetchEpisodes(character.episodeIds)
    }
    DetailScreen(character = character, episodesUiState = episodeUiState, onBack = onBack)
}

@Composable
private fun DetailScreen(
    character: Character,
    episodesUiState: EpisodesUiState,
    onBack: () -> Unit,
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(character.name) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
            )
        },
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
        ) {
            item {
                CharacterInfoComponent(
                    character = character,
                    modifier = Modifier.padding(
                        horizontal = RickpediaTheme.spacings.l,
                        vertical = RickpediaTheme.spacings.m,
                    ),
                )
            }
            stickyHeader {
                Text(
                    modifier = Modifier
                        .background(color = RickpediaTheme.colorScheme.background)
                        .fillMaxWidth()
                        .padding(horizontal = RickpediaTheme.spacings.xl, vertical = RickpediaTheme.spacings.m),
                    text = stringResource(R.string.text_episodes),
                    style = RickpediaTheme.typography.titleLarge,
                )
            }
            when (episodesUiState) {
                is EpisodesUiState.Loading -> item {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        CircularProgressIndicator(
                            modifier = Modifier.padding(RickpediaTheme.spacings.xl),
                        )
                    }
                }
                is EpisodesUiState.Error -> item {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Text(text = episodesUiState.throwable.message.orEmpty())
                        Spacer(modifier = Modifier.height(RickpediaTheme.spacings.s))
                        Button(onClick = { /*TODO*/ }) {
                            Text(text = "Try again")
                        }
                    }
                }
                is EpisodesUiState.WithData -> items(episodesUiState.data) {
                    EpisodeComponent(
                        episode = it,
                        modifier = Modifier.padding(horizontal = RickpediaTheme.spacings.xl, vertical = RickpediaTheme.spacings.s),
                    )
                }
            }
        }
    }
}

@Composable
private fun EpisodeComponent(
    episode: Episode,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding()
            .then(modifier),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(RickpediaTheme.spacings.m),
        ) {
            Text(text = "${episode.name} (${episode.code})", style = RickpediaTheme.typography.titleMedium)
            Text(text = stringResource(R.string.text_airDate, episode.airDate))
        }
    }
}

@Composable
private fun CharacterInfoComponent(
    character: Character,
    modifier: Modifier = Modifier,
) {
    Row(modifier = modifier) {
        AsyncImage(
            model = character.image,
            contentDescription = character.name,
            modifier = Modifier
                .width(100.dp)
                .aspectRatio(1f / 1f),
        )
        Spacer(modifier = Modifier.width(RickpediaTheme.spacings.m))
        Column(modifier = Modifier.weight(1f)) {
            LabeledValueComponent(label = stringResource(R.string.text_status), value = character.status)
            LabeledValueComponent(
                label = stringResource(R.string.text_species),
                value = listOf(character.species, character.subSpecies).filter {
                    it.isNotBlank()
                }.joinToString(" - "),
            )
            LabeledValueComponent(label = stringResource(R.string.text_location), value = character.location)
            LabeledValueComponent(label = stringResource(R.string.text_origin), value = character.origin)
        }
    }
}

@Composable
private fun LabeledValueComponent(
    label: String,
    value: String,
    modifier: Modifier = Modifier,
) {
    Row(modifier) {
        Text(text = "$label :", style = RickpediaTheme.typography.labelLarge)
        Text(text = value, style = RickpediaTheme.typography.bodyMedium)
    }
}
