@file:OptIn(ExperimentalGlideComposeApi::class)

package com.example.jungleconsultingtesttask.screen.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.jungleconsultingtesttask.domain.User
import com.example.jungleconsultingtesttask.screen.ScreenNavigation

@Composable
fun UserList(
    users: LazyPagingItems<User>,
    navController: NavController
) {

    Box(modifier = Modifier.fillMaxSize()) {
        if (users.loadState.refresh is LoadState.Loading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        } else {
            LazyColumn {
                items(users) { user ->
                    if (user != null) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 16.dp)
                                .clickable {
                                    navController.navigate(
                                        ScreenNavigation.UserDetailsScreen.withArgs(
                                            user.login
                                        )
                                    )
                                },
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surfaceVariant,
                            ),
                            shape = MaterialTheme.shapes.medium
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                GlideImage(
                                    model = user.avatar_url,
                                    contentDescription = "Avatar",
                                    modifier = Modifier
                                        .padding(start = 16.dp)
                                        .clip(CircleShape)
                                        .size(70.dp),
                                )
                                Text(
                                    text = user.login,
                                    style = MaterialTheme.typography.titleLarge,
                                    modifier = Modifier
                                        .padding(
                                            start = 16.dp,
                                            end = 16.dp,
                                            top = 32.dp,
                                            bottom = 32.dp
                                        )
                                )
                            }
                        }
                    }
                }
                item {
                    if (users.loadState.append is LoadState.Loading) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }
            }
        }
    }
}