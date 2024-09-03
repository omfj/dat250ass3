package com.example.dat250ass2.model.input

import com.example.dat250ass2.model.domain.User
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize

@JsonSerialize
@JsonDeserialize
data class AddUserInput(val email: String, val name: String)

fun AddUserInput.toUser(id: String): User {
    return User(id, name, email)
}
