package com.example.jungleconsultingtesttask.data.local.userrepo

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.jungleconsultingtesttask.data.local.user.UserDbEntity

@Entity(tableName = "user_details")
data class UserRepoDbEntity (
    @PrimaryKey(autoGenerate = false) val id: Int,
    val name: String,
    val description: String?,
    @Embedded(prefix = "owner_") val owner: UserDbEntity
)