package com.example.dat250ass2.model.input

import com.example.dat250ass2.factory.IdFactory
import com.example.dat250ass2.factory.UnixTime
import com.example.dat250ass2.model.domain.Poll
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize

@JsonSerialize
@JsonDeserialize
data class AddPollInput(
    val question: String,
    val ownerId: String,
    val expiresAt: Int,
    val options: List<VoteOptionInput>,
)

fun AddPollInput.toPoll(): Poll {
    val id = IdFactory.generateId()
    val publishedAt = UnixTime.now()
    val options = options.map { it.toVoteOption() }
    return Poll(id, question, ownerId, publishedAt, expiresAt, options)
}
