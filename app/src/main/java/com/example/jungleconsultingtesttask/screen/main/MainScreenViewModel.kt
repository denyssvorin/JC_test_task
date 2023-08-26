package com.example.jungleconsultingtesttask.screen.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.jungleconsultingtesttask.data.local.user.UserDbEntity
import com.example.jungleconsultingtesttask.data.mappers.toUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    pager: Pager<Int, UserDbEntity>
) : ViewModel() {
    
    val usersPagingFlow = pager
        .flow
        .map { pagingData ->
            pagingData.map { it.toUser() }
        }
        .cachedIn(viewModelScope)
}