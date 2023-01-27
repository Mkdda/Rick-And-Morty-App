package com.maylfire.rickmortyapp

import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.maylfire.rickmortyapp.presentation.ui.ID_LOTTIE
import com.maylfire.rickmortyapp.presentation.ui.ID_LOTTIE_WRAPPER
import com.maylfire.rickmortyapp.presentation.ui.RandMortyLottieAnimation
import com.maylfire.rickmortyapp.presentation.ui.theme.RickMortyAppTheme
import org.junit.Rule
import org.junit.Test

class LottieAnimationComposeTest {

    @get:Rule
    val composeTestRule: ComposeContentTestRule = createComposeRule()

    @Test
    fun lottieAnimationTest() {

        this.composeTestRule.setContent {

            RickMortyAppTheme {

                RandMortyLottieAnimation(lottieResource = R.raw.loader)
            }
        }

        this.composeTestRule.onNodeWithTag(ID_LOTTIE_WRAPPER).assertExists()
        this.composeTestRule.onNodeWithTag(ID_LOTTIE).assertExists()
    }
}