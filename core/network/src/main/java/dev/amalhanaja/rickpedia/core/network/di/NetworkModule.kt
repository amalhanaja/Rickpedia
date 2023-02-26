package dev.amalhanaja.rickpedia.core.network.di

import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.amalhanaja.rickpedia.core.network.BuildConfig
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Provides
    fun provideGson(): Gson {
        return Gson()
    }

    @Provides
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
}
