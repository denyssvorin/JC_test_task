package com.example.jungleconsultingtesttask.screen.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.jungleconsultingtesttask.data.Repository
import com.example.jungleconsultingtesttask.data.mappers.toUserRepo
import com.example.jungleconsultingtesttask.domain.UserRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class UserRepoViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _apiRequestErrorFlow = repository.apiRequestErrorFlow
    val apiRequestError: StateFlow<Boolean> = _apiRequestErrorFlow

    private val userLogin = MutableLiveData("")

    fun setLogin(login: String) {
        if (this.userLogin.value == login) return
        this.userLogin.value = login
    }

    val userRepoDbPagingFlow: Flow<PagingData<UserRepo>> = userLogin.asFlow()
        .flatMapLatest {
            repository.getPagedUserReposFromDb(it)
        }.map { pagingData ->
            pagingData.map { it.toUserRepo()}
        }
        // use cacheIn operator for flows returned by Pager. Otherwise exception may be thrown
        // when refreshing/invalidating or subscribing to the flow more than once.
        .cachedIn(viewModelScope)

    fun reload() {
        this.userLogin.postValue(this.userLogin.value)
    }
}