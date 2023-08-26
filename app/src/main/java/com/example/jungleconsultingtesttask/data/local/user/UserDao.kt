package com.example.jungleconsultingtesttask.data.local.user

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface UserDao {

    @Query("SELECT * FROM users_table")
    fun getUsers(): PagingSource<Int, UserDbEntity>

    @Upsert
    suspend fun upsertAll(users: List<UserDbEntity>)

    @Query("DELETE FROM users_table")
    suspend fun clearAllUsers()
}