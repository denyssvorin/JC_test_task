package com.example.jungleconsultingtesttask.data.local.user

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users_table")
data class UserDbEntity(
    @PrimaryKey(autoGenerate = false) val login: String,
    val id: Int,
    val avatar_url: String
)