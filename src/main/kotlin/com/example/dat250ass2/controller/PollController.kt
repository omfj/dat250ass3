package com.example.dat250ass2.controller

import com.example.dat250ass2.exception.PollExpiredException
import com.example.dat250ass2.exception.PollVoteOptionNotFoundException
import com.example.dat250ass2.factory.UnixTime
import com.example.dat250ass2.model.domain.Poll
import com.example.dat250ass2.model.input.AddPollInput
import com.example.dat250ass2.model.input.AddVoteInput
import com.example.dat250ass2.model.input.toPoll
import com.example.dat250ass2.service.PollService
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Poll")
@RestController
@RequestMapping("/polls")
class PollController(
    private val pollService: PollService,
) {
    @GetMapping
    fun getPolls(): List<Poll> {
        return pollService.getPolls()
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun addPoll(
        @RequestBody poll: AddPollInput,
    ): Poll {
        val pollToInsert = poll.toPoll()

        if (pollToInsert.expiresAt <= UnixTime.now()) {
            throw PollExpiredException()
        }

        pollService.addPoll(pollToInsert)
        return pollToInsert
    }

    @GetMapping("/{id}")
    fun getPoll(
        @PathVariable id: String,
    ): Poll {
        return pollService.getPoll(id, true)
    }

    @DeleteMapping("/{id}")
    fun deletePoll(
        @PathVariable id: String,
    ): Poll {
        return pollService.deletePoll(id)
    }

    @PostMapping("/{id}/vote")
    @ResponseStatus(HttpStatus.CREATED)
    fun addVote(
        @PathVariable id: String,
        @RequestBody vote: AddVoteInput,
    ): Poll {
        val poll = pollService.getPoll(id, checkExpiration = true)

        if (poll.expiresAt <= UnixTime.now()) {
            throw PollExpiredException(id)
        }

        if (!poll.options.any { it.id == vote.optionId }) {
            throw PollVoteOptionNotFoundException(poll.id, vote.optionId)
        }

        pollService.addVote(id, vote.optionId, vote.userId)

        return pollService.getPoll(id)
    }

    @DeleteMapping("/{id}/vote")
    fun deleteVote(
        @PathVariable id: String,
        @RequestBody vote: AddVoteInput,
    ): Poll {
        val poll = pollService.getPoll(id, checkExpiration = true)

        if (!poll.options.any { it.id == vote.optionId }) {
            throw PollVoteOptionNotFoundException(poll.id, vote.optionId)
        }

        pollService.deleteVote(id, vote.userId)

        return pollService.getPoll(id)
    }
}
