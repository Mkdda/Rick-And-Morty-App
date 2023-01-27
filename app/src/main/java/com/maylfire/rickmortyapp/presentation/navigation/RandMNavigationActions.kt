package com.maylfire.rickmortyapp.presentation.navigation

import androidx.navigation.NavController
import com.maylfire.rickmortyapp.presentation.navigation.RandMDestinationsArgs.EPISODE_IDS_ARG
import com.maylfire.rickmortyapp.presentation.navigation.RandMDestinationsArgs.RESIDENTS_ORIGIN_ID_ARG
import com.maylfire.rickmortyapp.presentation.navigation.RandMDestinationsArgs.RESIDENTS_ORIGIN_PLANET_ARG
import com.maylfire.rickmortyapp.presentation.navigation.RandMScreens.EPISODES_SCREEN
import com.maylfire.rickmortyapp.presentation.navigation.RandMScreens.MAIN_MENU_SCREEN
import com.maylfire.rickmortyapp.presentation.navigation.RandMScreens.RESIDENTS_SCREEN

private object RandMScreens {

    const val MAIN_MENU_SCREEN = "main_menu"
    const val RESIDENTS_SCREEN = "residents"
    const val EPISODES_SCREEN = "episodes"
}

object RandMDestinationsArgs {

    const val RESIDENTS_ORIGIN_PLANET_ARG = "originPlanet"
    const val RESIDENTS_ORIGIN_ID_ARG = "originId"
    const val EPISODE_IDS_ARG = "episodesIds"
}

object RandMDestinations {

    const val MAIN_MENU_ROUTE = MAIN_MENU_SCREEN
    const val RESIDENTS_ROUTE = "${RESIDENTS_SCREEN}/{$RESIDENTS_ORIGIN_PLANET_ARG}/{$RESIDENTS_ORIGIN_ID_ARG}"
    const val EPISODES_ROUTE = "${EPISODES_SCREEN}/{$EPISODE_IDS_ARG}"
}

class RandMNavigationActions(private val navController: NavController) {

    fun navigateToResidents(originPlanet: String, originId: Int) {

        this.navController.navigate("$RESIDENTS_SCREEN/$originPlanet/$originId")
    }

    fun navigateToEpisodes(episodes: String) {

        this.navController.navigate("$EPISODES_SCREEN/$episodes")
    }
}