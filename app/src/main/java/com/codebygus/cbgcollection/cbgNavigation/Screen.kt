package com.codebygus.cbgcollection.cbgNavigation

sealed class Screen(val route: String) {
    object HomeScreen : Screen("home_screen")
    object SelectRootScreen : Screen("select_root_screen")
    object TrackScreen : Screen("track_screen")
    object AppearanceSettings : Screen("appearance_settings")
    object FunctionSettings : Screen("function_settings")
    object PlaylistSettings : Screen("playlist_settings")

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}