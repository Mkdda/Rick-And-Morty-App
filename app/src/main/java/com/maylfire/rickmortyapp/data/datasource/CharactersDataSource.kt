package com.maylfire.rickmortyapp.data.datasource

import com.maylfire.rickmortyapp.data.remote.dto.CharacterSchemaModel

interface CharactersDataSource {

    suspend fun getCharacters(page: Int): CharacterSchemaModel
}