package com.example.jungleconsultingtesttask.screen

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.jungleconsultingtesttask.screen.details.UserRepoScreen
import com.example.jungleconsultingtesttask.screen.main.UserMainScreen


@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = ScreenNavigation.MainScreen.route
    ) {
        composable(route = ScreenNavigation.MainScreen.route) {
            UserMainScreen(navController = navController)
        }
        composable(
            route = ScreenNavigation.UserDetailsScreen.route + "/{user_login}",
            arguments = listOf(
                navArgument("user_login") {
                    type = NavType.StringType
                }

            )
        ) { entry ->
            entry.arguments?.getString("user_login")?.let {
                UserRepoScreen(userLogin = it)
            }
        }
    }
}

