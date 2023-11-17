package com.codebygus.cbgcollection.cbgNavigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.codebygus.cbgcollection.R
import com.codebygus.cbgcollection.cbgCore.HomeScreen
import com.codebygus.cbgcollection.cbgCore.TrackScreen
import com.codebygus.cbgcollection.cbgRoot.SelectRootScreen
import com.codebygus.cbgcollection.cbgSettings.AppearanceSettings
import com.codebygus.cbgcollection.cbgSettings.FunctionSettings
import com.codebygus.cbgcollection.cbgSettings.PlaylistSettings

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.HomeScreen.route) {
        composable(route = Screen.HomeScreen.route) {
            HomeScreen(
                navController = navController,
                appBarTitle = stringResource(id = R.string.app_home)
            )
        }
        composable(
            route = Screen.TrackScreen.route + "/{appBarTitle}",
            arguments = listOf(
                navArgument("appBarTitle") {
                    type = NavType.StringType
                    defaultValue = "Screen Title"
                    nullable = false
                }
            )
        ) { entry ->
            entry.arguments?.getString("appBarTitle")?.let {
                TrackScreen(
                    navController = navController,
                    appBarTitle = it
                )
            }
        }
        composable(
            route = Screen.SelectRootScreen.route + "/{appBarTitle}",
            arguments = listOf(
                navArgument("appBarTitle") {
                    type = NavType.StringType
                    defaultValue = "Screen Title"
                    nullable = false
                }
            )
        ) { entry ->
            entry.arguments?.getString("appBarTitle")?.let {
                SelectRootScreen(
                    navController,
                    it
                )
            }
        }
        composable(
            route = Screen.AppearanceSettings.route + "/{appBarTitle}",
            arguments = listOf(
                navArgument("appBarTitle") {
                    type = NavType.StringType
                    defaultValue = "Screen Title"
                    nullable = false
                }
            )
        ) { entry ->
            entry.arguments?.getString("appBarTitle")?.let {
                AppearanceSettings(
                    navController = navController,
                    appBarTitle = stringResource(id = R.string.appearance_settings_header)
                )
            }
        }
        composable(
            route = Screen.FunctionSettings.route + "/{appBarTitle}",
            arguments = listOf(
                navArgument("appBarTitle") {
                    type = NavType.StringType
                    defaultValue = "Screen Title"
                    nullable = false
                }
            )
        ) { entry ->
            entry.arguments?.getString("appBarTitle")?.let {
                FunctionSettings(
                    navController,
                    it
                )
            }
        }
        composable(
            route = Screen.PlaylistSettings.route + "/{appBarTitle}",
            arguments = listOf(
                navArgument("appBarTitle") {
                    type = NavType.StringType
                    defaultValue = "Screen Title"
                    nullable = false
                }
            )
        ) { entry ->
            entry.arguments?.getString("appBarTitle")?.let {
                PlaylistSettings(
                    navController,
                    it
                )
            }
        }
    }
}