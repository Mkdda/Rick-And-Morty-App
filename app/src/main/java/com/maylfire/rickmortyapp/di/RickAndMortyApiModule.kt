package com.maylfire.rickmortyapp.di

import com.maylfire.rickmortyapp.data.datasource.CharactersDataSource
import com.maylfire.rickmortyapp.data.datasource.CharactersDataSourceImpl
import com.maylfire.rickmortyapp.data.datasource.RickAndMortyDataSource
import com.maylfire.rickmortyapp.data.datasource.RickAndMortyDataSourceImpl
import com.maylfire.rickmortyapp.data.repository.RickAndMortyRepositoryImpl
import com.maylfire.rickmortyapp.data.remote.RickAndMortyApi
import com.maylfire.rickmortyapp.data.remote.RickAndMortyApi.Companion.RickAndMortyEndpoint
import com.maylfire.rickmortyapp.data.repository.CharactersRepositoryImpl
import com.maylfire.rickmortyapp.domain.repository.CharactersRepository
import com.maylfire.rickmortyapp.domain.repository.RickAndMortyRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object RickAndMortyApiModule {

    @Provides
    fun provideRickAndMortyApiClient(): RickAndMortyApi {

        return Retrofit.Builder()
            .client(OkHttpClient.Builder().build())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(RickAndMortyEndpoint)
            .build()
            .create(RickAndMortyApi::class.java)
    }

    // region rick and morty module

    @Provides
    fun provideRickAndMortyDataSource(api: RickAndMortyApi): RickAndMortyDataSource {

        return RickAndMortyDataSourceImpl(api)
    }

    @Provides
    fun provideRickAndMortyRepository(dataSource: RickAndMortyDataSource): RickAndMortyRepository {

        return RickAndMortyRepositoryImpl(dataSource)
    }

    // endregion

    // region characters module

    @Provides
    fun provideCharactersDataSource(api: RickAndMortyApi): CharactersDataSource {

        return CharactersDataSourceImpl(api)
    }

    @Provides
    fun provideCharactersRepository(dataSource: CharactersDataSource): CharactersRepository {

        return CharactersRepositoryImpl(dataSource)
    }

    // endregion
}