package com.maylfire.rickmortyapp.domain.repository

import com.maylfire.rickmortyapp.data.remote.dto.CharacterSchemaModel

interface CharactersRepository {

    suspend fun getCharacters(page: Int): CharacterSchemaModel
}