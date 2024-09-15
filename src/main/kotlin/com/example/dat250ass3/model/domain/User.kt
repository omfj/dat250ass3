package com.example.dat250ass3.model.domain

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize

@JsonSerialize
@JsonDeserialize
data class User(
    val id: String,
    val name: String,
    val email: String,
    val password: String,
)

fun User.withoutPassword(): UserWithoutPassword {
    return UserWithoutPassword(
        id = id,
        name = name,
        email = email,
    )
}

@JsonSerialize
@JsonDeserialize
data class UserWithoutPassword(
    val id: String,
    val name: String,
    val email: String,
)
