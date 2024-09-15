package com.example.dat250ass3.exception

class PollVoteOptionNotFoundException(pollId: String, voteOptionId: String) :
    RuntimeException("Poll with id $pollId does not have vote option with id $voteOptionId")
