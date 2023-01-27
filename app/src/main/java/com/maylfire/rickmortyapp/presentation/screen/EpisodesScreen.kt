package com.maylfire.rickmortyapp.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.maylfire.rickmortyapp.R
import com.maylfire.rickmortyapp.data.remote.dto.EpisodeModel
import com.maylfire.rickmortyapp.presentation.ui.RandMortyLottieAnimation
import com.maylfire.rickmortyapp.presentation.ui.theme.default_horizontal_item_padding
import com.maylfire.rickmortyapp.presentation.ui.theme.default_padding
import com.maylfire.rickmortyapp.presentation.ui.theme.default_paddingX2
import com.maylfire.rickmortyapp.presentation.ui.theme.default_size
import com.maylfire.rickmortyapp.presentation.viewmodel.EpisodesViewModel
import com.maylfire.rickmortyapp.utils.UiEvent

@Composable
fun EpisodesScreen(
    viewModel: EpisodesViewModel = hiltViewModel(),
) {

    viewModel.initializeEpisodes()

    val uiEvent: UiEvent<List<EpisodeModel>> by viewModel.uiEpisodesEvent.collectAsState(
        initial = UiEvent.NoOne
    )

    val scaffoldState: ScaffoldState = rememberScaffoldState()

    Scaffold(scaffoldState = scaffoldState) {

        EpisodesScreen(
            scaffoldState = scaffoldState,
            episodesEvent = uiEvent
        )
    }
}

@Composable
fun EpisodesScreen(
    scaffoldState: ScaffoldState,
    episodesEvent: UiEvent<List<EpisodeModel>>
) {

    when (episodesEvent) {

        is UiEvent.Loading -> RandMortyLottieAnimation(lottieResource = R.raw.loader)

        is UiEvent.Success -> {

            val episodesData: List<EpisodeModel> = episodesEvent.data

            LazyColumn {

                items(episodesData.size) { index: Int ->

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                top = default_padding,
                                start = default_paddingX2,
                                end = default_paddingX2
                            ),
                        elevation = default_horizontal_item_padding,
                        shape = RoundedCornerShape(default_size)
                    ) {

                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                            val seasonLabel: String = stringResource(id = R.string.season)

                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        top = default_padding,
                                        start = default_padding,
                                        bottom = default_padding
                                    ),
                                text = "$seasonLabel ${episodesData[index].episode}"
                            )

                            val episodeLabel: String = stringResource(id = R.string.episode_name)

                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        start = default_padding,
                                        bottom = default_padding
                                    ),
                                text = "$episodeLabel ${episodesData[index].name}"
                            )

                            val releaseLabel: String = stringResource(id = R.string.release_date)

                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        start = default_padding,
                                        bottom = default_padding
                                    ),
                                text = "$releaseLabel ${episodesData[index].airDate}"
                            )

                            val charactersCount: Int = episodesData[index].characters.size

                            val charactersLabel: String = stringResource(id = R.string.characters_count,charactersCount)

                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        start = default_padding,
                                        bottom = default_padding
                                    ),
                                text = charactersLabel
                            )
                        }
                    }
                }
            }
        }

        is UiEvent.Error -> {

            val errorMessage: String = episodesEvent.uiText.asTextValue()

            LaunchedEffect(key1 = true) {

                scaffoldState.snackbarHostState.showSnackbar(
                    message = errorMessage
                )
            }
        }

        else -> Unit
    }
}