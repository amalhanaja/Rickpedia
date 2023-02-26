package dev.amalhanaja.rickpedia.core.network.di

import com.google.gson.Gson
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.amalhanaja.rickpedia.core.network.BuildConfig
import dev.amalhanaja.rickpedia.core.network.RickpediaNetworkDataSource
import dev.amalhanaja.rickpedia.core.network.api.RickpediaApiClient
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface NetworkModule {

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return Gson()
    }

    @Provides
    @Singleton
    fun provideOkHttpCallFactory(): Call.Factory {
        return OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor()
                    .setLevel(
                        when {
                            BuildConfig.DEBUG -> HttpLoggingInterceptor.Level.BODY
                            else -> HttpLoggingInterceptor.Level.NONE
                        },
                    ),
            )
            .build()
    }

    @Singleton
    @Binds
    fun bindsRickpediaApiClient(networkApiClient: RickpediaApiClient): RickpediaNetworkDataSource
}
