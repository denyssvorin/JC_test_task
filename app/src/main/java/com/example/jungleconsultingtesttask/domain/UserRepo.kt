package com.example.jungleconsultingtesttask.domain

data class UserRepo(
    val id: Int,
    val name: String,
    val description: String?,
    val owner: User
)
