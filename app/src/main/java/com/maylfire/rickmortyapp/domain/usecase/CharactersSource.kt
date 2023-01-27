package com.maylfire.rickmortyapp.domain.usecase

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.maylfire.rickmortyapp.data.datasource.CharactersDataSource
import com.maylfire.rickmortyapp.data.datasource.CharactersDataSourceImpl
import com.maylfire.rickmortyapp.data.datasource.CharactersDataSourceImpl.Companion.FIRST_PAGE
import com.maylfire.rickmortyapp.data.remote.dto.CharacterInfoModel
import com.maylfire.rickmortyapp.data.remote.dto.CharacterModel
import com.maylfire.rickmortyapp.data.remote.dto.CharacterSchemaModel
import com.maylfire.rickmortyapp.domain.repository.CharactersRepository
import com.maylfire.rickmortyapp.utils.findNumber
import kotlinx.coroutines.delay
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class CharactersSource @Inject constructor(
    private val charactersRepo: CharactersRepository
) : PagingSource<Int, CharacterModel>() {

    companion object {

        const val PAGE_ELEMENTS_SIZE = 20
    }

    override fun getRefreshKey(state: PagingState<Int, CharacterModel>): Int? = null

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CharacterModel> {

        return try {

            val page: Int = params.key?: FIRST_PAGE

            val characters: CharacterSchemaModel = this.charactersRepo.getCharacters(page)

            val nextKey: Int? = characters.info.next.findNumber

            LoadResult.Page(
                data = characters.results,
                prevKey = null,
                nextKey = nextKey
            )
        } catch (e: HttpException) {

            LoadResult.Error(Exception(e.localizedMessage))
        } catch (e: IOException) {

            LoadResult.Error(Exception(e.localizedMessage))
        }
    }
}