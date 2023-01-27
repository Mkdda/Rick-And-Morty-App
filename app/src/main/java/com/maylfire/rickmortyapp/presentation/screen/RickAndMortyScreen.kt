package com.maylfire.rickmortyapp.presentation.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.maylfire.rickmortyapp.presentation.navigation.RandMDestinations.EPISODES_ROUTE
import com.maylfire.rickmortyapp.presentation.navigation.RandMDestinations.MAIN_MENU_ROUTE
import com.maylfire.rickmortyapp.presentation.navigation.RandMDestinations.RESIDENTS_ROUTE
import com.maylfire.rickmortyapp.presentation.navigation.RandMDestinationsArgs.EPISODE_IDS_ARG
import com.maylfire.rickmortyapp.presentation.navigation.RandMDestinationsArgs.RESIDENTS_ORIGIN_ID_ARG
import com.maylfire.rickmortyapp.presentation.navigation.RandMDestinationsArgs.RESIDENTS_ORIGIN_PLANET_ARG
import com.maylfire.rickmortyapp.presentation.navigation.RandMNavigationActions

@Composable
fun RickAndMortyScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = MAIN_MENU_ROUTE,
    navActions: RandMNavigationActions = remember(navController) {
        RandMNavigationActions(navController)
    }
) {

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {

        composable(route = MAIN_MENU_ROUTE) {

            AllCharactersScreen(
                onResidentsClick = navActions::navigateToResidents,
                onEpisodesClick = navActions::navigateToEpisodes
            )
        }

        composable(
            route = RESIDENTS_ROUTE,
            arguments = listOf(
                navArgument(RESIDENTS_ORIGIN_PLANET_ARG) { type = NavType.StringType },
                navArgument(RESIDENTS_ORIGIN_ID_ARG) { type = NavType.IntType }
            )
        ) {

            ResidentsScreen()
        }

        composable(
            route = EPISODES_ROUTE,
            arguments = listOf(
                navArgument(EPISODE_IDS_ARG) { type = NavType.StringType }
            )
        ) {

            EpisodesScreen()
        }
    }
}