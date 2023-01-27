/*
package com.maylfire.rickmortyapp

import android.content.Context
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.platform.app.InstrumentationRegistry
import com.google.common.truth.Truth.assertThat
import com.maylfire.rickmortyapp.presentation.ui.theme.RickMortyAppTheme
import org.junit.Rule
import org.junit.Test

class RickAndMortyMainMenuComposeTest {

    @get:Rule
    val composeTestRule: ComposeContentTestRule = createComposeRule()

    private val context: Context by lazy {

        InstrumentationRegistry.getInstrumentation().targetContext
    }

    @Test
    fun cardItemTest() {

        this.composeTestRule.setContent {

            RickMortyAppTheme {

                CardItem(
                    titleResourceId = R.string.characters,
                    heightCard = 100,
                    imageResource = R.drawable.characters,
                    onCardClick = {}
                )
            }
        }

        // assert the wrapper of card exists
        this.composeTestRule.onNodeWithTag(ID_CARD_WRAPPER_iTEM).assertExists()
        // assert item card exists
        this.composeTestRule.onNodeWithTag(ID_CARD_ITEM).assertExists()
        // assert image of card exists
        this.composeTestRule.onNodeWithTag(ID_CARD_IMAGE,useUnmergedTree = true).assertExists()
        // assert title text exists
        this.composeTestRule.onNodeWithTag(ID_TEXT_SECTION).assertExists()
    }

    @Test
    fun cardItemTitleTest() {

        val titleResID: Int = R.string.characters
        val expectedTitle: String = this.context.getString(titleResID)

        this.composeTestRule.setContent {

            RickMortyAppTheme {

                CardItem(
                    titleResourceId = titleResID,
                    heightCard = 100,
                    imageResource = R.drawable.characters,
                    onCardClick = {}
                )
            }
        }

        this.composeTestRule.onNodeWithTag(ID_TEXT_SECTION).assertTextEquals(expectedTitle)
    }

    @Test
    fun cardItemClickTest() {

        var isClicked = false

        val onCardClick: () -> Unit = {

            isClicked = true
        }

        this.composeTestRule.setContent {

            RickMortyAppTheme {

                CardItem(
                    titleResourceId = R.string.characters,
                    heightCard = 100,
                    imageResource = R.drawable.characters,
                    onCardClick = onCardClick
                )
            }
        }

        this.composeTestRule.onNodeWithTag(ID_CARD_ITEM).performClick()

        assertThat(isClicked).isTrue()
    }
}*/
