package com.example.dat250ass2.model.domain

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize

@JsonSerialize
@JsonDeserialize
data class Poll(
    val id: String,
    val question: String,
    val ownerId: String,
    val publishedAt: Int,
    val expiresAt: Int,
    val options: List<VoteOption>,
    val votes: List<Vote> = emptyList(),
)
