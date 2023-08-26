package com.example.jungleconsultingtesttask.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.jungleconsultingtesttask.data.local.user.UserDbEntity
import com.example.jungleconsultingtesttask.data.local.user.UserDao
import com.example.jungleconsultingtesttask.data.local.userrepo.UserRepoDao
import com.example.jungleconsultingtesttask.data.local.userrepo.UserRepoDbEntity

@Database(entities = [UserDbEntity::class, UserRepoDbEntity::class], version = 1)
abstract class UserDatabase: RoomDatabase() {
    abstract val  userDao: UserDao
    abstract val userRepoDao: UserRepoDao
}