package com.maylfire.rickmortyapp

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.maylfire.rickmortyapp.presentation.navigation.RandMDestinations
import com.maylfire.rickmortyapp.presentation.screen.RickAndMortyScreen
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class RandMNavigationActionsTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    lateinit var navController: TestNavHostController

    @Before
    fun setupAppNavHost() {

        this.composeTestRule.setContent {

            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())

            RickAndMortyScreen(navController = navController)
        }
    }

    @Test
    fun rickAndMortyScreenVerifyStartDestination() {

        this.composeTestRule
            .onNodeWithContentDescription(RandMDestinations.MAIN_MENU_ROUTE)
            .assertIsDisplayed()
    }
}