package com.example.jungleconsultingtesttask.data.local.userrepo

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface UserRepoDao {

    @Query("SELECT * FROM user_details WHERE owner_login LIKE '%' || :userLogin || '%' " +
            "ORDER BY LOWER(name) " +
            "LIMIT :limit OFFSET :offset")
    suspend fun getUserRepo(userLogin: String, limit: Int, offset: Int): List<UserRepoDbEntity>

    @Upsert
    suspend fun upsertAllUserRepo(users: List<UserRepoDbEntity>)

    @Query("DELETE FROM user_details WHERE owner_login LIKE '%' || :userLogin || '%'")
    suspend fun clearAllUserRepo(userLogin: String)
}