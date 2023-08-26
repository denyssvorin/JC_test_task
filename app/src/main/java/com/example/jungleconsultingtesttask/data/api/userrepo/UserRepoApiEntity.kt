package com.example.jungleconsultingtesttask.data.api.userrepo

import com.example.jungleconsultingtesttask.data.api.user.UserApiEntity

data class UserRepoApiEntity (
    val id: Int,
    val name: String,
    val description: String?,
    val owner: UserApiEntity
)