package com.example.jungleconsultingtesttask.data.mappers

import com.example.jungleconsultingtesttask.data.api.user.UserApiEntity
import com.example.jungleconsultingtesttask.data.api.userrepo.UserRepoApiEntity
import com.example.jungleconsultingtesttask.data.local.user.UserDbEntity
import com.example.jungleconsultingtesttask.data.local.userrepo.UserRepoDbEntity
import com.example.jungleconsultingtesttask.domain.User
import com.example.jungleconsultingtesttask.domain.UserRepo

fun UserApiEntity.toUserDbEntity(): UserDbEntity {
    return UserDbEntity(
        login = login,
        id = id,
        avatar_url = avatar_url
    )
}

fun UserDbEntity.toUser(): User {
    return User(
        login = login,
        id = id,
        avatar_url = avatar_url
    )
}

fun UserRepoApiEntity.toUserDbEntity(): UserRepoDbEntity {
    return UserRepoDbEntity(
        id = id,
        name = name,
        description = description,
        owner = owner.toUserDbEntity()
    )
}

fun UserRepoDbEntity.toUserRepo(): UserRepo {
    return UserRepo(
        id = id,
        name = name,
        description = description,
        owner = owner.toUser()
    )
}


