package com.example.dat250ass2.model.domain

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize

@JsonSerialize
@JsonDeserialize
data class User(
    val id: String,
    val name: String,
    val email: String,
)
