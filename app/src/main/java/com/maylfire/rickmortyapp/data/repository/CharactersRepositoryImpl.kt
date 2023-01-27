package com.maylfire.rickmortyapp.data.repository

import com.maylfire.rickmortyapp.data.datasource.CharactersDataSource
import com.maylfire.rickmortyapp.data.remote.dto.CharacterSchemaModel
import com.maylfire.rickmortyapp.domain.repository.CharactersRepository
import javax.inject.Inject

class CharactersRepositoryImpl @Inject constructor(
    private val dataSource: CharactersDataSource
): CharactersRepository {

    override suspend fun getCharacters(page: Int): CharacterSchemaModel = this.dataSource.getCharacters(page)
}