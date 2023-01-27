package com.maylfire.rickmortyapp.presentation.screen

import android.annotation.SuppressLint
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.maylfire.rickmortyapp.R
import com.maylfire.rickmortyapp.presentation.model.Character
import com.maylfire.rickmortyapp.presentation.ui.RandMortyLottieAnimation
import com.maylfire.rickmortyapp.presentation.ui.theme.*
import com.maylfire.rickmortyapp.presentation.viewmodel.CharactersViewModel
import com.maylfire.rickmortyapp.utils.filterNumbersAsString
import com.maylfire.rickmortyapp.utils.findNumber

@Composable
fun AllCharactersScreen(
    viewModel: CharactersViewModel = hiltViewModel(),
    onResidentsClick: (String, Int) -> Unit,
    onEpisodesClick: (String) -> Unit
) {

    val characters: LazyPagingItems<Character> = viewModel.data.collectAsLazyPagingItems()

    val expandedCardIds: List<Int> by viewModel.expandedCardIdsList.collectAsState()

    val scaffoldState: ScaffoldState = rememberScaffoldState()

    Scaffold(scaffoldState = scaffoldState) {

        AllCharactersScreen(
            scaffoldState = scaffoldState,
            items = characters,
            onCharacterClick = viewModel::onCardArrowClicked,
            expandedCardIds = expandedCardIds,
            onResidentsClick = onResidentsClick,
            onEpisodesClick = onEpisodesClick
        )
    }
}

@Composable
fun AllCharactersScreen(
    scaffoldState: ScaffoldState,
    items: LazyPagingItems<Character>?,
    onCharacterClick: (Int) -> Unit,
    expandedCardIds: List<Int>,
    onResidentsClick: (String, Int) -> Unit,
    onEpisodesClick: (String) -> Unit
) {

    if ((items?.itemCount ?: 0) <= 0) {

        RandMortyLottieAnimation(lottieResource = R.raw.loader)
    } else {

        CharactersListItems(
            characters = items,
            onCharacterClick = onCharacterClick,
            expandedCardIds = expandedCardIds,
            onResidentsClick = onResidentsClick,
            onEpisodesClick = onEpisodesClick
        ) {

            val state: CombinedLoadStates? = items?.loadState

            when {

                state?.append is LoadState.Loading -> LinearProgressIndicator(modifier = Modifier.fillMaxWidth())

                state?.refresh is LoadState.Error || state?.append is LoadState.Error -> {

                    val error: String = stringResource(id = R.string.unexpected_error)

                    LaunchedEffect(key1 = true) {

                        scaffoldState.snackbarHostState.showSnackbar(message = error)
                    }
                }
            }
        }
    }
}

const val CHARACTERS_LIST_ITEMS_WRAPPER = "characters_list_items_wrapper"

@Composable
fun CharactersListItems(
    characters: LazyPagingItems<Character>?,
    onCharacterClick: (Int) -> Unit,
    expandedCardIds: List<Int>,
    onResidentsClick: (String, Int) -> Unit,
    onEpisodesClick: (String) -> Unit,
    content: @Composable (ColumnScope.() -> Unit)
) {

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        LazyColumn {

            items(characters?.itemCount?:0) { index: Int ->

                characters?.get(index)?.let { character: Character ->

                    ExpandableCard(
                        character = character,
                        isExpanded = expandedCardIds.contains(character.id),
                        onItemClick = { onCharacterClick(character.id) },
                        onResidentsClick = onResidentsClick,
                        onEpisodesClick = onEpisodesClick
                    )
                }
            }

            item { content() }
        }
    }
}

private const val expansionDurationTransition = 450

@SuppressLint("UnusedTransitionTargetStateParameter")
@Composable
fun ExpandableCard(
    character: Character,
    isExpanded: Boolean,
    onItemClick: () -> Unit,
    onResidentsClick: (String, Int) -> Unit,
    onEpisodesClick: (String) -> Unit
) {

    val transitionState = remember {
        MutableTransitionState(isExpanded).apply {
            targetState = !isExpanded
        }
    }

    val transition = updateTransition(transitionState = transitionState, label = "transition")

    val horizontalPadding by transition.animateDp({
        tween(durationMillis = expansionDurationTransition)
    }, label = "horizontalTransition") {
        if (isExpanded) default_horizontal_item_padding else default_paddingX2
    }

    val verticalPadding by transition.animateDp({
        tween(durationMillis = expansionDurationTransition)
    }, label = "verticalTransition") {
        if (isExpanded) default_paddingX2 else default_padding
    }

    val arrowRotationDegree by transition.animateFloat({
        tween(durationMillis = expansionDurationTransition)
    }, label = "rotationTransition") { if (isExpanded) 0f else 180f }

    Card(
        elevation = default_horizontal_item_padding,
        shape = RoundedCornerShape(card_item_size),
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = horizontalPadding,
                vertical = verticalPadding
            )
    ) {

        Column {

            Box {

                CardCharacterArrow(
                    onCardArrowClick = onItemClick,
                    arrowRotationDegree = arrowRotationDegree
                )

                CardCharacterTitle(characterName = character.name)

                ExpandableContent(
                    urlImage = character.urlImage,
                    visible = isExpanded,
                    specie = character.specie,
                    gender = character.gender,
                    status = character.status,
                    originPlanet = character.originPlanetName,
                    episodesCount = character.episodesCount,
                    onResidentsClick = {

                        if (character.originPlanetUrl.isNotEmpty()) {

                            character.originPlanetUrl.findNumber?.let {

                                onResidentsClick(character.originPlanetName, it)
                            }
                        }
                    },
                    onEpisodesClick = {

                        if (character.episodes.isNotEmpty()) {

                            onEpisodesClick(character.episodes.filterNumbersAsString)
                        }
                    }
                )
            }
        }
    }
}

const val ID_CARD_ARROW_BUTTON = "card_arrow_button"
const val ID_CARD_ARROW_ICON = "card_arrow_icon"

@Composable
fun CardCharacterArrow(
    onCardArrowClick: () -> Unit,
    arrowRotationDegree: Float
) {

    IconButton(
        modifier = Modifier.testTag(ID_CARD_ARROW_BUTTON),
        onClick = onCardArrowClick
    ) {

        Icon(
            imageVector = Icons.Rounded.ArrowDropDown,
            contentDescription = null,
            modifier = Modifier
                .testTag(ID_CARD_ARROW_ICON)
                .rotate(arrowRotationDegree)
        )
    }
}

const val ID_CHARACTER_TITLE = "character_title"

@Composable
fun CardCharacterTitle(characterName: String) {

    Text(
        text = characterName,
        textAlign = TextAlign.Center,
        color = purpleColor,
        modifier = Modifier
            .testTag(ID_CHARACTER_TITLE)
            .fillMaxWidth()
            .padding(default_paddingX2)
    )
}

internal const val ID_IMAGE_CARD = "image_card"

@Composable
fun ExpandableContent(
    urlImage: String,
    visible: Boolean,
    specie: String,
    gender: String,
    status: String,
    originPlanet: String,
    episodesCount: Int,
    onResidentsClick: () -> Unit,
    onEpisodesClick: () -> Unit
) {

    val enterTransition: EnterTransition = remember {

        expandVertically(
            expandFrom = Alignment.Top,
            animationSpec = tween(expansionDurationTransition)
        ) + fadeIn(
            initialAlpha = 0.3f,
            animationSpec = tween(expansionDurationTransition)
        )
    }

    val exitTransition: ExitTransition = remember {

        shrinkVertically(
            shrinkTowards = Alignment.Top,
            animationSpec = tween(expansionDurationTransition)
        ) + fadeOut(animationSpec = tween(expansionDurationTransition))
    }

    AnimatedVisibility(
        visible = visible,
        enter = enterTransition,
        exit = exitTransition
    ) {

        Column(
            modifier = Modifier
                .padding(top = default_paddingX3)
                .wrapContentHeight()
                .fillMaxWidth()
        ) {

            Spacer(modifier = Modifier.padding(default_padding))

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Card(
                    elevation = default_horizontal_item_padding,
                    shape = RoundedCornerShape(card_item_size),
                ) {

                    SubcomposeAsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(urlImage)
                            .crossfade(true)
                            .build(),
                        loading = {

                            CircularProgressIndicator(
                                modifier = Modifier.padding(default_paddingX4)
                            )
                        },
                        contentDescription = null,
                        modifier = Modifier
                            .testTag(ID_IMAGE_CARD)
                            .size(250.dp)
                    )
                }
            }

            TextContent(
                specie = specie,
                gender = gender,
                status = status,
                originPlanet = originPlanet,
                episodesCount = episodesCount,
                onResidentsClick = onResidentsClick,
                onEpisodesClick = onEpisodesClick
            )

            Spacer(modifier = Modifier.padding(default_padding))
        }
    }
}

@Composable
fun TextContent(
    specie: String,
    gender: String,
    status: String,
    originPlanet: String,
    episodesCount: Int,
    onResidentsClick: () -> Unit,
    onEpisodesClick: () -> Unit
) {

    val specieLabel: String = stringResource(id = R.string.specie, specie)

    TextLabel(
        label = specieLabel,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = default_paddingX2, start = default_paddingX2)
    )

    val genderLabel: String = stringResource(id = R.string.gender, gender)

    TextLabel(
        label = genderLabel,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = default_padding, start = default_paddingX2)
    )

    val statusLabel: String = stringResource(id = R.string.status, status)

    TextLabel(
        label = statusLabel,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = default_padding, start = default_paddingX2)
    )

    val originLabel: String = stringResource(id = R.string.origin_planet, originPlanet)

    TextLabel(
        label = originLabel,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = default_padding, start = default_paddingX2)
    )

    Text(
        modifier = Modifier
            .padding(start = default_horizontal_link_padding)
            .clickable { onResidentsClick() },
        color = Color.Blue,
        fontStyle = FontStyle.Italic,
        text = stringResource(id = R.string.see_residents)
    )

    val episodesLabel: String = stringResource(id = R.string.episodes_count, episodesCount)

    TextLabel(
        label = episodesLabel,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = default_padding, start = default_paddingX2)
    )

    Text(
        modifier = Modifier
            .padding(start = default_horizontal_link_padding)
            .clickable { onEpisodesClick() },
        color = Color.Blue,
        fontStyle = FontStyle.Italic,
        text = stringResource(id = R.string.see_episodes)
    )
}

private val defaultFontSize = 15.sp

@Composable
fun TextLabel(
    modifier: Modifier = Modifier,
    label: String
) {

    Text(
        fontSize = defaultFontSize,
        color = purpleColor,
        text = label,
        modifier = modifier
    )
}
