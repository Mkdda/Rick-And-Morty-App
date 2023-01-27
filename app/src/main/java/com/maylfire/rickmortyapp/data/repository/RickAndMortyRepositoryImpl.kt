package com.maylfire.rickmortyapp.data.repository

import com.maylfire.rickmortyapp.data.datasource.RickAndMortyDataSource
import com.maylfire.rickmortyapp.data.remote.RickAndMortyApi
import com.maylfire.rickmortyapp.data.remote.dto.CharacterModel
import com.maylfire.rickmortyapp.data.remote.dto.CharacterSchemaModel
import com.maylfire.rickmortyapp.data.remote.dto.EpisodeModel
import com.maylfire.rickmortyapp.data.remote.dto.LocationModel
import com.maylfire.rickmortyapp.domain.repository.RickAndMortyRepository
import com.maylfire.rickmortyapp.utils.handleResultResponse
import com.maylfire.rickmortyapp.utils.Result
import retrofit2.Response
import javax.inject.Inject

class RickAndMortyRepositoryImpl @Inject constructor(
    private val dataSource: RickAndMortyDataSource
) : RickAndMortyRepository {

    override suspend fun getMultipleCharacters(ids: List<Int>): Result<List<CharacterModel>> {

        return this.dataSource.getMultipleCharacters(ids)
    }

    override suspend fun getSingleLocation(id: Int): Result<LocationModel> {

        return this.dataSource.getSingleLocation(id)
    }

    override suspend fun getMultipleEpisodes(ids: List<Int>): Result<List<EpisodeModel>> {

        return this.dataSource.getMultipleEpisodes(ids)
    }
}