package com.example.jungleconsultingtesttask.screen

sealed class ScreenNavigation(val route: String) {
    object MainScreen : ScreenNavigation("main_screen")
    object UserDetailsScreen : ScreenNavigation("user_details_screen")

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}