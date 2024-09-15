package com.example.dat250ass3.model.input

import com.example.dat250ass3.factory.IdFactory
import com.example.dat250ass3.model.domain.VoteOption
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize

@JsonSerialize
@JsonDeserialize
data class VoteOptionInput(
    val caption: String,
    val order: Number,
)

fun VoteOptionInput.toVoteOption(): VoteOption {
    val id = IdFactory.generateId()
    return VoteOption(id, caption, order)
}
