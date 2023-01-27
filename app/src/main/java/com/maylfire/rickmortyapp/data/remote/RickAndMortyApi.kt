package com.maylfire.rickmortyapp.data.remote

import com.maylfire.rickmortyapp.data.remote.dto.CharacterModel
import com.maylfire.rickmortyapp.data.remote.dto.CharacterSchemaModel
import com.maylfire.rickmortyapp.data.remote.dto.EpisodeModel
import com.maylfire.rickmortyapp.data.remote.dto.LocationModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RickAndMortyApi {

    companion object {

        const val RickAndMortyEndpoint = "https://rickandmortyapi.com/api/"
    }

    // region characters

    @GET("character/")
    suspend fun getAllCharacters(@Query("page") page: Int): CharacterSchemaModel

    @GET("character/{ids}")
    suspend fun getMultipleCharacters(@Path("ids") ids: List<Int>): Response<List<CharacterModel>>

    // endregion

    // region locations

    @GET("location/{id}")
    suspend fun getSingleLocation(@Path("id") id: Int): Response<LocationModel>

    // endregion

    // region episodes

    @GET("episode/{ids}")
    suspend fun getMultipleEpisodes(@Path("ids") ids: List<Int>): Response<List<EpisodeModel>>

    // endregion
}