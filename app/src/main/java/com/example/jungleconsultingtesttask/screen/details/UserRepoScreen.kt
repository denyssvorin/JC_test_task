package com.example.jungleconsultingtesttask.screen.details

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems


@Composable
fun UserRepoScreen(
    userLogin: String,
    viewModel: UserRepoViewModel = hiltViewModel()
) {
    val login = remember {
        userLogin
    }

    val userRepoEntityList = viewModel.userRepoDbPagingFlow.collectAsLazyPagingItems()
    val context = LocalContext.current

    LaunchedEffect(key1 = login, key2 = userRepoEntityList.loadState) {
        viewModel.setLogin(login)

        if (userRepoEntityList.loadState.refresh is LoadState.Error) {
            Toast.makeText(
                context,
                "Error: " + (userRepoEntityList.loadState.refresh as LoadState.Error).error.message,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    UserRepoList(userLogin = login, userRepoEntityList = userRepoEntityList)
}