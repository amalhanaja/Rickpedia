package dev.amalhanaja.rickpedia.feature.home

import android.annotation.SuppressLint
import android.os.Parcel
import android.os.Parcelable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridItemScope
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import dev.amalhanaja.rickpedia.core.designsystem.foundation.RickpediaTheme
import dev.amalhanaja.rickpedia.core.model.Character

@Composable
internal fun HomeRoute(
    homeViewModel: HomeViewModel,
) {
    val lazyPagingItems = homeViewModel.charactersPagingData.collectAsLazyPagingItems()
    HomeScreen(characterPagingItems = lazyPagingItems)
}

@Composable
private fun HomeScreen(characterPagingItems: LazyPagingItems<Character>) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
    ) {
        itemsIndexed(items = characterPagingItems, key = { _, c -> c.id }) { i, c ->
            CharacterComponent(character = c, modifier = Modifier.padding(RickpediaTheme.spacings.s))
        }
    }
}

@Composable
private fun CharacterComponent(modifier: Modifier = Modifier, character: Character) {
    val statusBgColor = when (character.status) {
        "Alive" -> RickpediaTheme.colorScheme.primary
        "Dead" -> RickpediaTheme.colorScheme.error
        else -> RickpediaTheme.colorScheme.secondary
    }
    Card(modifier = modifier) {
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val (imageRef, statusRef, infoRef) = createRefs()
            AsyncImage(
                modifier = Modifier
                    .constrainAs(ref = imageRef) {
                        linkTo(start = parent.start, end = parent.end)
                        width = Dimension.fillToConstraints
                        height = Dimension.ratio("1:1")
                    },
                model = character.image,
                contentDescription = character.name,
            )
            Text(
                modifier = Modifier
                    .constrainAs(ref = statusRef) {
                        end.linkTo(parent.end)
                        width = Dimension.wrapContent
                    }
                    .background(
                        color = statusBgColor,
                        shape = RickpediaTheme.shapes.medium.copy(topStart = CornerSize(0), bottomEnd = CornerSize(0)),
                    )
                    .padding(all = RickpediaTheme.spacings.s),
                text = character.status,
                color = RickpediaTheme.colorScheme.contentColorFor(statusBgColor),
            )
            Column(
                modifier = Modifier
                    .constrainAs(ref = infoRef) {
                        top.linkTo(imageRef.bottom)
                        linkTo(start = parent.start, end = parent.end)
                    }
                    .padding(horizontal = RickpediaTheme.spacings.s, vertical = RickpediaTheme.spacings.m),
            ) {
                Text(text = character.name, style = RickpediaTheme.typography.titleMedium, modifier = Modifier.fillMaxWidth())
                Spacer(modifier = Modifier.height(RickpediaTheme.spacings.xs))
                Text(
                    text = listOf(character.species, character.subSpecies).filter { it.isNotBlank() }.joinToString(separator = " - "),
                    style = RickpediaTheme.typography.bodySmall,
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 2,
                )
            }
        }
    }
}

private fun <T : Any> LazyGridScope.itemsIndexed(
    items: LazyPagingItems<T>,
    key: ((index: Int, item: T) -> Any)? = null,
    itemContent: @Composable LazyGridItemScope.(index: Int, value: T) -> Unit,
) {
    items(
        count = items.itemCount,
        key = if (key == null) null else { index ->
            val item = items.peek(index)
            if (item == null) {
                PagingPlaceholderKey(index)
            } else {
                key(index, item)
            }
        },
    ) { index ->
        items[index]?.let { item -> itemContent(index, item) }

    }
}


@SuppressLint("BanParcelableUsage")
private data class PagingPlaceholderKey(private val index: Int) : Parcelable {
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(index)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object {
        @Suppress("unused")
        @JvmField
        val CREATOR: Parcelable.Creator<PagingPlaceholderKey> =
            object : Parcelable.Creator<PagingPlaceholderKey> {
                override fun createFromParcel(parcel: Parcel) =
                    PagingPlaceholderKey(parcel.readInt())

                override fun newArray(size: Int) = arrayOfNulls<PagingPlaceholderKey?>(size)
            }
    }
}
