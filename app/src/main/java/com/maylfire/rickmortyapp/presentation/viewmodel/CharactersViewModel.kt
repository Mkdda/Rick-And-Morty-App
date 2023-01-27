package com.maylfire.rickmortyapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.maylfire.rickmortyapp.data.remote.dto.CharacterModel
import com.maylfire.rickmortyapp.data.remote.dto.toCharacter
import com.maylfire.rickmortyapp.domain.usecase.CharactersSource
import com.maylfire.rickmortyapp.domain.usecase.CharactersSource.Companion.PAGE_ELEMENTS_SIZE
import com.maylfire.rickmortyapp.presentation.model.Character
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(
    private val charactersSource: CharactersSource
) : ViewModel() {

    val data: Flow<PagingData<Character>> =
        getCharacters().map { pagingData: PagingData<CharacterModel> ->

            pagingData.map { it.toCharacter }
        }.cachedIn(this.viewModelScope)

    private fun getCharacters(): Flow<PagingData<CharacterModel>> {

        return Pager(PagingConfig(PAGE_ELEMENTS_SIZE)) {
            this.charactersSource
        }.flow
    }

    private val _expandedCardIdsList: MutableStateFlow<List<Int>> by lazy {

        MutableStateFlow(emptyList())
    }

    val expandedCardIdsList: StateFlow<List<Int>> get() = this._expandedCardIdsList

    fun onCardArrowClicked(cardId: Int) {

        this._expandedCardIdsList.value = this._expandedCardIdsList.value
            .toMutableList().also { list: MutableList<Int> ->

                if (list.contains(cardId)) {

                    list.remove(cardId)
                } else {
                    list.add(cardId)
                }
            }
    }
}