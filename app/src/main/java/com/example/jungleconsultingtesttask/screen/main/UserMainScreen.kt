package com.example.jungleconsultingtesttask.screen.main

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems


@Composable
fun UserMainScreen(
    navController: NavHostController,
    viewModel: MainScreenViewModel = hiltViewModel()
) {
    val users = viewModel.usersPagingFlow.collectAsLazyPagingItems()

    val context = LocalContext.current
    LaunchedEffect(key1 = users.loadState) {
        if (users.loadState.refresh is LoadState.Error) {
            Toast.makeText(
                context,
                "Error: " + (users.loadState.refresh as LoadState.Error).error.message,
                Toast.LENGTH_LONG
            ).show()
        }
    }
    UserList(users = users, navController = navController)
}
