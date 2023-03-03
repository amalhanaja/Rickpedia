package dev.amalhanaja.rickpedia.core.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.amalhanaja.rickpedia.core.data.repository.CharacterRepository
import dev.amalhanaja.rickpedia.core.data.repository.EpisodeRepository
import dev.amalhanaja.rickpedia.core.data.repository.OfflineFirstCharacterRepository
import dev.amalhanaja.rickpedia.core.data.repository.OfflineFirstEpisodeRepository

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    fun bindOfflineFirstCharacterRepository(offlineFirstCharacterRepository: OfflineFirstCharacterRepository): CharacterRepository

    @Binds
    fun bindOfflineFirstEpisodeRepository(offlineFirstEpisodeRepository: OfflineFirstEpisodeRepository): EpisodeRepository

}
