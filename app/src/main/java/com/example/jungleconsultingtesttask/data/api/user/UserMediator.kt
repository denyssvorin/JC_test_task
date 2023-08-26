package com.example.jungleconsultingtesttask.data.api.user

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.jungleconsultingtesttask.data.api.UserApi
import com.example.jungleconsultingtesttask.data.local.UserDatabase
import com.example.jungleconsultingtesttask.data.local.user.UserDbEntity
import com.example.jungleconsultingtesttask.data.mappers.toUserDbEntity
import kotlinx.coroutines.delay
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class UserMediator(
    private val api: UserApi,
    private val db: UserDatabase
) : RemoteMediator<Int, UserDbEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, UserDbEntity>
    ): MediatorResult {
        return try {
            val lastItem = state.lastItemOrNull()
            val loadKey = when (loadType) {
                LoadType.REFRESH -> 0
                LoadType.PREPEND -> return MediatorResult.Success(
                    endOfPaginationReached = true
                )
                LoadType.APPEND -> {
                    if (lastItem == null) 1
                    else { lastItem.id.plus(1) ?: 0 }
                }
            }

            delay(2000)
            val users = api.getUsers(
                sinceId = loadKey,
                pageCount = state.config.pageSize
            )

            db.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    db.userDao.clearAllUsers()
                }
                val usersDbEntities = users.map { it.toUserDbEntity() }
                db.userDao.upsertAll(usersDbEntities)
            }

            MediatorResult.Success(
                endOfPaginationReached = users.isEmpty()
            )
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }
}
