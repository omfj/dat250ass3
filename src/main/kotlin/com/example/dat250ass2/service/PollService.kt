package com.example.dat250ass2.service

import com.example.dat250ass2.exception.PollNotFoundException
import com.example.dat250ass2.factory.UnixTime
import com.example.dat250ass2.model.domain.Poll
import com.example.dat250ass2.model.domain.Vote
import com.example.dat250ass2.storage.KeyValueStorage
import org.springframework.stereotype.Service

@Service
class PollService(
    private val pollStorage: KeyValueStorage<Poll>,
) {
    fun addPoll(poll: Poll) {
        pollStorage.put(poll.id, poll)
    }

    fun getPolls(): List<Poll> {
        return pollStorage.list()
    }

    fun getPoll(
        id: String,
        checkExpiration: Boolean = false,
    ): Poll {
        val poll = pollStorage.get(id) ?: throw PollNotFoundException(id)
        if (checkExpiration && poll.expiresAt <= UnixTime.now()) {
            throw PollNotFoundException(id)
        }
        return poll
    }

    fun deletePoll(id: String): Poll {
        return pollStorage.delete(id) ?: throw PollNotFoundException(id)
    }

    fun addVote(
        pollId: String,
        optionId: String,
        userId: String,
    ) {
        val poll = getPoll(pollId)
        val updatedVotes = poll.votes.toMutableList()
        updatedVotes.add(Vote(optionId, userId))
        val updatedPoll = poll.copy(votes = updatedVotes)
        addPoll(updatedPoll)
    }

    fun deleteVote(
        pollId: String,
        userId: String,
    ) {
        val poll = getPoll(pollId)
        val updatedVotes = poll.votes.filter { it.userId != userId }
        val updatedPoll = poll.copy(votes = updatedVotes)
        addPoll(updatedPoll)
    }
}
