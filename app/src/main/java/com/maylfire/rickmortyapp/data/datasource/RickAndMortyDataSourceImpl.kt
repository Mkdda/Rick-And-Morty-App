package com.maylfire.rickmortyapp.data.datasource

import com.maylfire.rickmortyapp.data.remote.RickAndMortyApi
import com.maylfire.rickmortyapp.data.remote.dto.CharacterModel
import com.maylfire.rickmortyapp.data.remote.dto.EpisodeModel
import com.maylfire.rickmortyapp.data.remote.dto.LocationModel
import com.maylfire.rickmortyapp.utils.Result
import com.maylfire.rickmortyapp.utils.handleResultResponse
import retrofit2.Response
import javax.inject.Inject

class RickAndMortyDataSourceImpl @Inject constructor(
    private val api: RickAndMortyApi
): RickAndMortyDataSource {

    override suspend fun getMultipleCharacters(ids: List<Int>): Result<List<CharacterModel>> {

        val charactersResponse: Response<List<CharacterModel>> = this.api.getMultipleCharacters(ids)

        return handleResultResponse(response = charactersResponse)
    }

    override suspend fun getSingleLocation(id: Int): Result<LocationModel> {

        val locationResponse: Response<LocationModel> = this.api.getSingleLocation(id)

        return handleResultResponse(response = locationResponse)
    }

    override suspend fun getMultipleEpisodes(ids: List<Int>): Result<List<EpisodeModel>> {

        val episodesResponse: Response<List<EpisodeModel>> = this.api.getMultipleEpisodes(ids)

        return handleResultResponse(response = episodesResponse)
    }
}