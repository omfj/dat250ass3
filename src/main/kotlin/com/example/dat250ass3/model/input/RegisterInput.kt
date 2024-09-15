package com.example.dat250ass3.model.input

import com.example.dat250ass3.factory.IdFactory
import com.example.dat250ass3.model.domain.User

data class RegisterInput(
    val email: String,
    val name: String,
    val password: String,
)

fun RegisterInput.toUser(): User {
    val id = IdFactory.generateId()
    return User(
        id = id,
        email = email,
        name = name,
        password = password,
    )
}
