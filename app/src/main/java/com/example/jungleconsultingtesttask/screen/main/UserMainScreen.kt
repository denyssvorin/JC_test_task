package com.example.jungleconsultingtesttask.screen.main

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems


@Composable
fun UserMainScreen(
    navController: NavHostController,
    viewModel: MainScreenViewModel = hiltViewModel()
) {
    val users = viewModel.usersPagingFlow.collectAsLazyPagingItems()

    UserList(users = users, navController = navController)
}
