package com.maylfire.rickmortyapp.navigation

//TODO - DELETE

/*@Composable
fun RandMNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
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

        composable(MAIN_MENU_ROUTE) {

            MainMenuScreen(onResidentsClick = navActions::navigateToResidents)
        }

        composable(
            RESIDENTS_ROUTE,
            arguments = listOf(
                navArgument(RESIDENTS_ORIGIN_PLANET_ARG) { type = NavType.StringType },
                navArgument(RESIDENTS_ORIGIN_ID_ARG) { type = NavType.IntType }
            )
        ) {

            ResidentsScreen()
        }
    }
}*/
