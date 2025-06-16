package com.darekbx.mineralscatalog.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

private object Routes {
    const val LIST = "list"
    const val DETAILS = "details/{${ArgParams.MINERAL_ID}}"
    const val NEW = "new/{${ArgParams.IMAGE_URI}}"

}

sealed class Screen(
    val route: String,
    val navArguments: List<NamedNavArgument> = listOf()
) {
    object ItemsList : Screen(Routes.LIST)

    object Details : Screen(Routes.DETAILS,
        navArguments = listOf(navArgument(ArgParams.MINERAL_ID) {
            type = NavType.Companion.StringType
        })
    ) {
        fun createRoute(mineralId: String) =
            Routes.DETAILS.replace(ArgParams.toPath(ArgParams.MINERAL_ID), mineralId)
    }

    object New : Screen(
        route = Routes.NEW,
        navArguments = listOf(navArgument(ArgParams.IMAGE_URI) {
            type = NavType.Companion.StringType
        })
    ) {
        fun createRoute(imageUri: String) =
            Routes.NEW.replace(
                ArgParams.toPath(ArgParams.IMAGE_URI),
                URLEncoder.encode(imageUri, StandardCharsets.UTF_8.toString())
            )
    }
}

object ArgParams {
    const val IMAGE_URI = "imageUri"
    const val MINERAL_ID = "mineralId"

    fun toPath(param: String) = "{${param}}"
}
