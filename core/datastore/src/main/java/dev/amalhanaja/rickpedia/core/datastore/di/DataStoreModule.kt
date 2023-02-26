package dev.amalhanaja.rickpedia.core.datastore.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStoreFile
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

private const val RICKPEDIA_PREFERENCE_NAME = "rickpedia.preferences_pb"

@Module
@InstallIn(SingletonComponent::class)
class DataStoreModule {

    @Provides
    fun providePreferencesDataStore(
        @ApplicationContext context: Context,
    ): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            produceFile = { context.dataStoreFile(RICKPEDIA_PREFERENCE_NAME) },
        )
    }

}
