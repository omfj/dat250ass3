package com.example.dat250ass3.controller

import com.example.dat250ass3.exception.NotSignedInException
import com.example.dat250ass3.exception.PollExpiredException
import com.example.dat250ass3.exception.PollVoteOptionNotFoundException
import com.example.dat250ass3.exception.UnauthorizedException
import com.example.dat250ass3.factory.UnixTime
import com.example.dat250ass3.model.domain.Poll
import com.example.dat250ass3.model.domain.UserWithoutPassword
import com.example.dat250ass3.model.input.AddPollInput
import com.example.dat250ass3.model.input.AddVoteInput
import com.example.dat250ass3.model.input.toPoll
import com.example.dat250ass3.service.PollService
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.security.core.annotation.AuthenticationPrincipal
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
@RequestMapping("/api/polls")
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
        @AuthenticationPrincipal user: UserWithoutPassword?,
    ): Poll {
        if (user == null) {
            throw NotSignedInException()
        }

        val pollToInsert = poll.toPoll(user.id)

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
        @AuthenticationPrincipal user: UserWithoutPassword?,
    ): Poll {
        if (user == null) {
            throw NotSignedInException()
        }

        val poll = pollService.getPoll(id)

        if (poll.ownerId != user.id) {
            throw UnauthorizedException()
        }

        return pollService.deletePoll(id)
    }

    @PostMapping("/{id}/vote")
    @ResponseStatus(HttpStatus.CREATED)
    fun addVote(
        @PathVariable id: String,
        @RequestBody vote: AddVoteInput,
        @AuthenticationPrincipal user: UserWithoutPassword?,
    ): Poll {
        val poll = pollService.getPoll(id, checkExpiration = true)

        if (!poll.options.any { it.id == vote.optionId }) {
            throw PollVoteOptionNotFoundException(poll.id, vote.optionId)
        }

        if (user == null) {
            throw NotSignedInException()
        }

        pollService.addVote(id, vote.optionId, user.id)

        return pollService.getPoll(id)
    }

    @DeleteMapping("/{id}/vote")
    fun deleteVote(
        @PathVariable id: String,
        @RequestBody vote: AddVoteInput,
        @AuthenticationPrincipal user: UserWithoutPassword?,
    ): Poll {
        val poll = pollService.getPoll(id, checkExpiration = true)

        if (!poll.options.any { it.id == vote.optionId }) {
            throw PollVoteOptionNotFoundException(poll.id, vote.optionId)
        }

        if (user == null) {
            throw NotSignedInException()
        }

        pollService.deleteVote(id, user.id)

        return pollService.getPoll(id)
    }
}
