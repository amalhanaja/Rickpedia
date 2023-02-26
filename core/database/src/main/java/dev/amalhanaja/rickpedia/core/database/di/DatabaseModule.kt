package dev.amalhanaja.rickpedia.core.database.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.amalhanaja.rickpedia.core.database.RickpediaDatabase
import dev.amalhanaja.rickpedia.core.database.dao.CharacterDao
import dev.amalhanaja.rickpedia.core.database.dao.EpisodeDao
import javax.inject.Singleton

private const val RICKPEDIA_DB_NAME = "rickpedia-database"

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideRickpediaDatabase(
        @ApplicationContext context: Context,
    ): RickpediaDatabase {
        return Room.databaseBuilder(
            context,
            RickpediaDatabase::class.java,
            RICKPEDIA_DB_NAME,
        ).build()
    }

    @Provides
    fun provideCharacterDao(database: RickpediaDatabase): CharacterDao {
        return database.characterDao()
    }

    @Provides
    fun provideEpisodeDao(database: RickpediaDatabase): EpisodeDao {
        return database.episodeDao()
    }
}
