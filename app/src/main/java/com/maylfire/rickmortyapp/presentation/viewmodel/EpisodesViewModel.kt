package com.maylfire.rickmortyapp.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maylfire.rickmortyapp.R
import com.maylfire.rickmortyapp.data.remote.dto.EpisodeModel
import com.maylfire.rickmortyapp.di.IODispatcher
import com.maylfire.rickmortyapp.domain.repository.RickAndMortyRepository
import com.maylfire.rickmortyapp.presentation.navigation.RandMDestinationsArgs
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
class EpisodesViewModel @Inject constructor(
    private val repository: RickAndMortyRepository,
    private val savedStateHandle: SavedStateHandle,
    @IODispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    companion object {

        internal const val episodesIdsError = "provide a valid episodeIds args"
        private const val delimiterComma = ","
    }

    private val episodesIds: String = this.savedStateHandle[RandMDestinationsArgs.EPISODE_IDS_ARG]
        ?: error(episodesIdsError)

    private val _uiEpisodesEvent = Channel<UiEvent<List<EpisodeModel>>>()

    val uiEpisodesEvent: Flow<UiEvent<List<EpisodeModel>>>
        get() = this._uiEpisodesEvent.receiveAsFlow()

    fun initializeEpisodes() {

        this.viewModelScope.launch(this.dispatcher) {

            _uiEpisodesEvent.send(UiEvent.Loading)

            val episodesIdsList: List<Int> = episodesIds.split(delimiterComma)
                .map { it.toInt() }

            when (val chapters = repository.getMultipleEpisodes(episodesIdsList)) {

                is Result.Success -> {

                    val dataEvent: UiEvent<List<EpisodeModel>> = UiEvent.Success(chapters.data)

                    _uiEpisodesEvent.send(dataEvent)
                }

                is Result.ErrorResponse -> {

                    val errorEvent = UiEvent.Error(chapters.message)

                    _uiEpisodesEvent.send(errorEvent)
                }

                else -> Unit
            }
        }
    }
}