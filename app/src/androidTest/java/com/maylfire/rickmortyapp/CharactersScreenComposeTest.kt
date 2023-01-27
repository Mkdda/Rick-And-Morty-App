package com.maylfire.rickmortyapp

import androidx.compose.material.rememberScaffoldState
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.maylfire.rickmortyapp.data.remote.dto.CharacterModel
import com.maylfire.rickmortyapp.data.remote.dto.toCharacter
import com.maylfire.rickmortyapp.presentation.model.Character
import com.maylfire.rickmortyapp.presentation.screen.AllCharactersScreen
import com.maylfire.rickmortyapp.presentation.screen.CHARACTERS_LIST_ITEMS_WRAPPER
import com.maylfire.rickmortyapp.presentation.ui.ID_LOTTIE_WRAPPER
import com.maylfire.rickmortyapp.presentation.ui.theme.RickMortyAppTheme
import io.mockk.every
import kotlinx.coroutines.flow.flowOf
import org.junit.Rule
import org.junit.Test

class CharactersScreenComposeTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun allCharactersScreenLoadingTest() {

        this.composeTestRule.setContent {

            RickMortyAppTheme {

                AllCharactersScreen(
                    scaffoldState = rememberScaffoldState(),
                    items = null,
                    onCharacterClick = {},
                    expandedCardIds = emptyList(),
                    onResidentsClick = {_,_ -> },
                    onEpisodesClick = {}
                )
            }
        }

        // since there no are items, the lottie loader must be appear
        this.composeTestRule.onNodeWithTag(ID_LOTTIE_WRAPPER).assertExists()
        // the items wrapper must not appear
        this.composeTestRule.onNodeWithTag(CHARACTERS_LIST_ITEMS_WRAPPER).assertDoesNotExist()
    }

    @Test
    fun allCharactersScreenTest() {

        val character: Character = CharacterModel
            .Builder()
            .build()
            .toCharacter

        this.composeTestRule.setContent {

            val items: LazyPagingItems<Character> = flowOf(
                PagingData.from(listOf(character))
            ).collectAsLazyPagingItems()

            eve { items.itemCount } returns 2

            RickMortyAppTheme {

                AllCharactersScreen(
                    scaffoldState = rememberScaffoldState(),
                    items = items,
                    onCharacterClick = {},
                    expandedCardIds = emptyList(),
                    onResidentsClick = {_,_ -> },
                    onEpisodesClick = {}
                )
            }
        }

        // since there are items, the items wrapper must appear
        this.composeTestRule.onNodeWithTag(CHARACTERS_LIST_ITEMS_WRAPPER).assertExists()
        // since there are items, the lottie loader must not appear
        this.composeTestRule.onNodeWithTag(ID_LOTTIE_WRAPPER).assertDoesNotExist()
    }
}