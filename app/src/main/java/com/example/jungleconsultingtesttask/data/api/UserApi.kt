package com.example.jungleconsultingtesttask.data.api

import com.example.jungleconsultingtesttask.data.api.user.UserApiEntity
import com.example.jungleconsultingtesttask.data.api.userrepo.UserRepoApiEntity
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface UserApi {

    @GET("users")
    suspend fun getUsers(
        @Query("since") sinceId: Int,
        @Query("per_page") pageCount: Int
    ): List<UserApiEntity>

    @GET("users/{login}/repos")
    suspend fun getUserRepos(
        @Path("login") userLogin: String,
        @Query("per_page") pageCount: Int,
        @Query("page") page: Int
    ): List<UserRepoApiEntity>

    companion object {
        const val BASE_URL = "https://api.github.com/"
    }
}