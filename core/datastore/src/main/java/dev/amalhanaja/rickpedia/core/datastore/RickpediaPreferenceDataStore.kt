package dev.amalhanaja.rickpedia.core.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private val KEY_ALL_CHARACTER_NEXT_PAGE = intPreferencesKey("all_character_next_page")

class RickpediaPreferenceDataStore @Inject constructor(
    private val dataStore: DataStore<Preferences>,
) : RickpediaPreferenceDataSource {

    override val allCharacterNextPage: Flow<Int?> = dataStore.data.map { preferences ->
        preferences[KEY_ALL_CHARACTER_NEXT_PAGE]
    }

    override suspend fun setAllCharacterNextPage(nextPage: Int) {
        dataStore.edit { mutablePreferences ->
            mutablePreferences[KEY_ALL_CHARACTER_NEXT_PAGE] = nextPage

        }
    }
}
