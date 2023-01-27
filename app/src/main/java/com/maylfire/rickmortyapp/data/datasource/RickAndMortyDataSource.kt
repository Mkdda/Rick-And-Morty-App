package com.maylfire.rickmortyapp.data.datasource

import com.maylfire.rickmortyapp.data.remote.dto.CharacterModel
import com.maylfire.rickmortyapp.data.remote.dto.EpisodeModel
import com.maylfire.rickmortyapp.data.remote.dto.LocationModel
import com.maylfire.rickmortyapp.utils.Result

interface RickAndMortyDataSource {

    suspend fun getMultipleCharacters(ids: List<Int>): Result<List<CharacterModel>>

    suspend fun getSingleLocation(id: Int): Result<LocationModel>

    suspend fun getMultipleEpisodes(ids: List<Int>): Result<List<EpisodeModel>>
}