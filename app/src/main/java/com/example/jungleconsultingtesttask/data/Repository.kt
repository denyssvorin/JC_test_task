package com.example.jungleconsultingtesttask.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.jungleconsultingtesttask.data.api.UserApi
import com.example.jungleconsultingtesttask.data.api.userrepo.UserRepoApiPagingSource
import com.example.jungleconsultingtesttask.data.api.userrepo.UsersReposApiPageLoader
import com.example.jungleconsultingtesttask.data.local.UserDatabase
import com.example.jungleconsultingtesttask.data.local.userrepo.UserRepoDbEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class Repository @Inject constructor(
    private val api: UserApi,
    private val db: UserDatabase
) {

    private val _apiRequestErrorFlow = UserRepoApiPagingSource.apiRequestErrorFlow
    val apiRequestErrorFlow: StateFlow<Boolean> = _apiRequestErrorFlow

    fun getPagedUserReposFromDb(userLogin: String): Flow<PagingData<UserRepoDbEntity>> {
        val apiLoader: UsersReposApiPageLoader = { pageIndex, pageSize ->

            api.getUserRepos(userLogin = userLogin, pageCount = pageSize, page = pageIndex)
        }
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                UserRepoApiPagingSource(
                    apiLoader = apiLoader,
                    pageSize = PAGE_SIZE,
                    db = db,
                    userLogin = userLogin
                )
            }
        ).flow
    }

    private companion object {
        const val PAGE_SIZE = 10
    }
}