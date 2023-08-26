package com.example.jungleconsultingtesttask.data.api.userrepo

import android.util.Log
import androidx.paging.LoadState
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.jungleconsultingtesttask.data.local.UserDatabase
import com.example.jungleconsultingtesttask.data.local.userrepo.UserRepoDbEntity
import com.example.jungleconsultingtesttask.data.mappers.toUserDbEntity
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import okio.IOException
import kotlin.properties.Delegates

typealias UsersReposApiPageLoader = suspend (pageIndex: Int, pageSize: Int) -> List<UserRepoApiEntity>

class UserRepoApiPagingSource(
    private val apiLoader: UsersReposApiPageLoader,
    private val pageSize: Int,
    private val db: UserDatabase,
    private val userLogin: String,
) : PagingSource<Int, UserRepoDbEntity>() {

    private lateinit var dbResponse: List<UserRepoDbEntity>

    override fun getRefreshKey(state: PagingState<Int, UserRepoDbEntity>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UserRepoDbEntity> {
        val pageIndex = params.key ?: 1
        Log.d("TAG", "pageIndex: $pageIndex")

        val offset = (pageIndex - 1) * pageSize

        return try {
            _apiRequestErrorFlow.value = false
            val response = apiLoader.invoke(pageIndex, params.loadSize)
            val responseEntities = response.map { it.toUserDbEntity() }

            if (response.isNotEmpty()) {
                db.userRepoDao.upsertAllUserRepo(responseEntities)
            }

            delay(1000L)
            dbResponse = db.userRepoDao.getUserRepo(
                userLogin = userLogin,
                limit = params.loadSize,
                offset = offset
            )
            Log.d("TAG", "db response: $dbResponse")

            LoadResult.Page(
                data = dbResponse,
                prevKey = if (pageIndex == 1) null else pageIndex.minus(1),
                nextKey = if (response.size == params.loadSize) pageIndex + (params.loadSize / pageSize) else null
            )
        } catch (e: IOException) {
            LoadState.Error(e)
            delay(1000L)
            dbResponse = db.userRepoDao.getUserRepo(
                userLogin = userLogin,
                limit = params.loadSize,
                offset = offset
            )
            if (dbResponse.isEmpty()) {
                _apiRequestErrorFlow.value = true
            }

            LoadResult.Page(
                data = dbResponse,
                prevKey = if (pageIndex == 1) null else pageIndex.minus(1),
                nextKey = if (dbResponse.size == params.loadSize) pageIndex + (params.loadSize / pageSize) else null
            )

        } catch (e: Exception) {
            if (dbResponse.isEmpty()) {
                apiRequestErrorFlow.value = true
            }
            Log.e(TAG, "load exception: $e")
            LoadResult.Error(e)
        }
    }

    companion object {
        private const val TAG = "UserRepoApiPagingSource"

        private val _apiRequestErrorFlow = MutableStateFlow(false)
        val apiRequestErrorFlow: MutableStateFlow<Boolean> = _apiRequestErrorFlow
    }

}