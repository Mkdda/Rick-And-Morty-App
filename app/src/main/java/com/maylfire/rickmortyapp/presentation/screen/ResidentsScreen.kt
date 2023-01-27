package com.maylfire.rickmortyapp.presentation.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.maylfire.rickmortyapp.R
import com.maylfire.rickmortyapp.presentation.model.Character
import com.maylfire.rickmortyapp.presentation.ui.RandMortyLottieAnimation
import com.maylfire.rickmortyapp.presentation.ui.theme.*
import com.maylfire.rickmortyapp.presentation.viewmodel.ResidentsViewModel
import com.maylfire.rickmortyapp.utils.UiEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun ResidentsScreen(viewModel: ResidentsViewModel = hiltViewModel()) {

    viewModel.initializeResidents()

    val uiEvent: UiEvent<List<Character>> by viewModel.uiCharactersEvent.collectAsState(
        UiEvent.NoOne
    )

    val scaffoldState: ScaffoldState = rememberScaffoldState()

    Scaffold(scaffoldState = scaffoldState) {

        Column(modifier = Modifier.fillMaxSize()) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                contentAlignment = Alignment.Center
            ) {

                Text(
                    text = viewModel.residentsOriginPlanet,
                    fontSize = 20.sp,
                    color = purpleColor
                )
            }

            Spacer(modifier = Modifier.padding(default_padding))

            ResidentsScreen(
                uiEvent = uiEvent,
                scaffoldState = scaffoldState
            )
        }
    }
}

@Composable
fun ResidentsScreen(
    scaffoldState: ScaffoldState,
    uiEvent: UiEvent<List<Character>>
) {

    val coroutineScope: CoroutineScope = rememberCoroutineScope()

    when(uiEvent) {

        is UiEvent.Loading -> {

            RandMortyLottieAnimation(lottieResource = R.raw.loader)
        }

        is UiEvent.Success -> {

            val residents: List<Character> = uiEvent.data

            ResidentsScreen(residents = residents) {

                coroutineScope.launch {

                    scaffoldState.snackbarHostState.currentSnackbarData?.dismiss()

                    scaffoldState.snackbarHostState.showSnackbar(message = it)
                }
            }
        }

        is UiEvent.Error -> {

            val error: String = uiEvent.uiText.asTextValue()

            RandMortyLottieAnimation(lottieResource = R.raw.error)

            LaunchedEffect(key1 = true) {

                scaffoldState.snackbarHostState.showSnackbar(message = error)
            }
        }

        else -> Unit
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ResidentsScreen(
    residents: List<Character>,
    onResidentClick: (String) -> Unit
) {

    LazyVerticalGrid(cells = GridCells.Fixed(2)) {

        items(residents.size) { index: Int ->

            Card(
                modifier = Modifier
                    .padding(
                        horizontal = default_padding,
                        vertical = default_padding
                    )
                    .clickable { onResidentClick(residents[index].name) },
                elevation = default_horizontal_item_padding,
                shape = RoundedCornerShape(card_item_size)
            ) {

                SubcomposeAsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(residents[index].urlImage)
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    loading = {

                        CircularProgressIndicator(
                            modifier = Modifier.padding(default_paddingX4)
                        )
                    },
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .testTag("pending")
                        .fillMaxSize()
                )
            }
        }
    }
}