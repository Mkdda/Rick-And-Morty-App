package com.maylfire.rickmortyapp.data.datasource

import com.maylfire.rickmortyapp.data.remote.RickAndMortyApi
import com.maylfire.rickmortyapp.data.remote.dto.CharacterSchemaModel
import kotlinx.coroutines.delay
import javax.inject.Inject

class CharactersDataSourceImpl @Inject constructor(
    private val api: RickAndMortyApi
) : CharactersDataSource {

    companion object {

        const val FIRST_PAGE = 1
    }

    override suspend fun getCharacters(page: Int): CharacterSchemaModel {

        delay(1500)

        return this.api.getAllCharacters(page)
    }
}