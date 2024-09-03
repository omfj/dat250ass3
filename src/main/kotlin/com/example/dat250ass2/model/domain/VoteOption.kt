package com.example.dat250ass2.model.domain

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize

@JsonSerialize
@JsonDeserialize
data class VoteOption(
    val id: String,
    val caption: String,
    val order: Number,
)
