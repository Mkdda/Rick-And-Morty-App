package com.maylfire.rickmortyapp.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maylfire.rickmortyapp.R
import com.maylfire.rickmortyapp.data.remote.dto.CharacterModel
import com.maylfire.rickmortyapp.data.remote.dto.toCharacter
import com.maylfire.rickmortyapp.data.repository.RickAndMortyRepositoryImpl
import com.maylfire.rickmortyapp.di.IODispatcher
import com.maylfire.rickmortyapp.domain.repository.RickAndMortyRepository
import com.maylfire.rickmortyapp.presentation.model.Character
import com.maylfire.rickmortyapp.presentation.navigation.RandMDestinationsArgs.RESIDENTS_ORIGIN_ID_ARG
import com.maylfire.rickmortyapp.presentation.navigation.RandMDestinationsArgs.RESIDENTS_ORIGIN_PLANET_ARG
import com.maylfire.rickmortyapp.utils.Result
import com.maylfire.rickmortyapp.utils.UiEvent
import com.maylfire.rickmortyapp.utils.UiText
import com.maylfire.rickmortyapp.utils.findNumber
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResidentsViewModel @Inject constructor(
    private val repository: RickAndMortyRepository,
    @IODispatcher private val dispatcher: CoroutineDispatcher,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {

    companion object {

        internal const val residentsOriginPlanetError = "provide a valid originPlanet arg"
        internal const val residentsOriginIdError = "provide a valid originId arg"
    }

    val residentsOriginPlanet: String = this.savedStateHandle[RESIDENTS_ORIGIN_PLANET_ARG]
        ?: error(residentsOriginPlanetError)

    private val residentsOriginId: Int = this.savedStateHandle[RESIDENTS_ORIGIN_ID_ARG]
        ?: error(residentsOriginIdError)

    private val _uiCharactersEvent = Channel<UiEvent<List<Character>>>()

    val uiCharactersEvent: Flow<UiEvent<List<Character>>>
        get() = this._uiCharactersEvent.receiveAsFlow()

    fun initializeResidents() {

        this.viewModelScope.launch(this.dispatcher) {

            _uiCharactersEvent.send(UiEvent.Loading)

            when(val locations = repository.getSingleLocation(residentsOriginId)) {

                is Result.Success -> fetchCharactersByResidentsData(locations.data.residents)

                is Result.ErrorResponse -> {

                    val error = UiEvent.Error(locations.message)

                    _uiCharactersEvent.send(error)
                }

                is Result.InvalidData -> {

                    val error = UiText.StringResource(R.string.no_data)

                    _uiCharactersEvent.send(UiEvent.Error(error))
                }
            }
        }
    }

    private suspend fun fetchCharactersByResidentsData(data: List<String>) {

        val residentsIds: MutableList<Int> = mutableListOf()

        data.forEach { urlResident: String ->

            urlResident.findNumber?.let { residentsIds.add(it) }
        }

        val characters: Result<List<CharacterModel>> = this.repository.getMultipleCharacters(residentsIds)

        if (characters is Result.Success) {

            _uiCharactersEvent.send(
                UiEvent.Success(
                    characters.data.map { it.toCharacter }
                )
            )
        } else {

            val error = UiText.StringResource(R.string.no_data)

            _uiCharactersEvent.send(UiEvent.Error(error))
        }
    }
}