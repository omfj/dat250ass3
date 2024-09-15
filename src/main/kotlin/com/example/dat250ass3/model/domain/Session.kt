package com.example.dat250ass3.model.domain

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize

@JsonSerialize
@JsonDeserialize
data class Session(
    val id: String,
    val userId: String,
    val expiresAt: Int,
)
