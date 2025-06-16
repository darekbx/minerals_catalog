package com.darekbx.mineralscatalog.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.darekbx.mineralscatalog.ui.details.DetailsScreen
import com.darekbx.mineralscatalog.ui.list.ListScreen
import com.darekbx.mineralscatalog.ui.new.NewScreen
import java.net.URLDecoder
import java.nio.charset.StandardCharsets


@Composable
fun AppNavHost(modifier: Modifier, navController: NavHostController) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Screen.ItemsList.route
    ) {
        composable(Screen.ItemsList.route) {
            ListScreen(onItemClick = { mineralId ->
                navController.navigate(Screen.Details.createRoute(mineralId))
            })
        }

        composable(
            route = Screen.Details.route,
            arguments = Screen.Details.navArguments
        ) { navBackStackEntry ->
            navBackStackEntry.arguments
                ?.getString(ArgParams.MINERAL_ID)
                ?.let {
                    DetailsScreen(mineralId = it, onBack = { navController.navigateUp() })
                }
        }

        composable(route = Screen.New.route) {navBackStackEntry ->
            navBackStackEntry.arguments
                ?.getString(ArgParams.IMAGE_URI)
                ?.let { encodedUri ->
                    val uri = URLDecoder.decode(encodedUri, StandardCharsets.UTF_8.toString())
                    NewScreen(uri = uri) { navController.navigateUp() }
                }
        }
    }
}