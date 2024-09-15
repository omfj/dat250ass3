package com.example.dat250ass3.model.input

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize

@JsonSerialize
@JsonDeserialize
data class AddVoteInput(
    val optionId: String,
)
