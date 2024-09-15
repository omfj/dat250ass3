package com.example.dat250ass3.controller

import com.example.dat250ass3.factory.UnixTime
import com.example.dat250ass3.model.domain.Poll
import com.example.dat250ass3.model.domain.User
import com.example.dat250ass3.model.domain.VoteOption
import com.example.dat250ass3.storage.KeyValueStorage
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class PollControllerTest {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var userStorage: KeyValueStorage<User>

    @Autowired
    private lateinit var pollStorage: KeyValueStorage<Poll>

    private val time = UnixTime.now()

    @AfterEach
    fun cleanup() {
        userStorage.clear()
        pollStorage.clear()
    }

    @Test
    fun `it lists no polls`() {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/polls"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().json("[]"))
    }

    @Test
    fun `it lists polls`() {
        givenIHavePolls()

        mockMvc.perform(MockMvcRequestBuilders.get("/api/polls"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$").isArray)
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value("1"))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].question").value("Question 1"))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].options").isArray)
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].votes").isArray)
    }

    @Test
    fun `it allows user to vote on poll`() {
        givenIHaveUsers()
        givenIHavePolls()

        mockMvc.perform(
            MockMvcRequestBuilders.post("/api/polls/1/vote")
                .contentType("application/json")
                .content("{\"userId\": \"1\", \"optionId\": \"1\"}"),
        )
            .andExpect(MockMvcResultMatchers.status().isCreated)

        mockMvc.perform(MockMvcRequestBuilders.get("/api/polls/1"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.votes[0].id").value("1"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.votes[0].userId").value("1"))
    }

    @Test
    fun `it allows user to change vote on poll`() {
        givenIHaveUsers()
        givenIHavePolls()

        mockMvc.perform(
            MockMvcRequestBuilders.post("/api/polls/1/vote")
                .contentType("application/json")
                .content("{\"userId\": \"1\", \"optionId\": \"1\"}"),
        )
            .andExpect(MockMvcResultMatchers.status().isCreated)

        mockMvc.perform(
            MockMvcRequestBuilders.post("/api/polls/1/vote")
                .contentType("application/json")
                .content("{\"userId\": \"1\", \"optionId\": \"2\"}"),
        )
            .andExpect(MockMvcResultMatchers.status().isCreated)

        mockMvc.perform(MockMvcRequestBuilders.get("/api/polls/1"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.votes.length()").value(1))
            .andExpect(MockMvcResultMatchers.jsonPath("$.votes[0].id").value("2"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.votes[0].userId").value("1"))
    }

    @Test
    fun `it should return error if poll has expired`() {
        givenIHavePolls()

        mockMvc.perform(MockMvcRequestBuilders.get("/api/polls/2"))
            .andExpect(MockMvcResultMatchers.status().isNotFound)
    }

    @Test
    fun `it should not allow user to vote on expired poll`() {
        givenIHaveUsers()
        givenIHavePolls()

        mockMvc.perform(
            MockMvcRequestBuilders.post("/api/polls/2/vote")
                .contentType("application/json")
                .content("{\"userId\": \"1\", \"optionId\": \"1\"}"),
        )
            .andExpect(MockMvcResultMatchers.status().isNotFound)
    }

    @Test
    fun `it should delete poll`() {
        givenIHavePolls()

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/polls/1"))
            .andExpect(MockMvcResultMatchers.status().isOk)

        mockMvc.perform(MockMvcRequestBuilders.get("/polls"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$").isArray)
            .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(0))
    }

    @Test
    fun `it should not add expired poll`() {
        mockMvc.perform(
            MockMvcRequestBuilders.post("/api/polls")
                .contentType("application/json")
                .content("{\"question\": \"Question 1\", \"ownerId\": \"1\", \"expiresAt\": ${time - 1000}, \"options\": []}"),
        )
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
            .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Poll has expired"))
    }

    private fun givenIHaveUsers() {
        val user = User("1", "One", "one@example.com", "something")
        userStorage.put(user.id, user)
    }

    private fun givenIHavePolls() {
        val options = listOf(VoteOption("1", "Option 1", 0), VoteOption("2", "Option 2", 1))
        val poll = Poll("1", "Question 1", "1", time, time + 1000, options, emptyList())
        pollStorage.put(poll.id, poll)

        val poll2 = Poll("2", "Question 2", "1", time - 2000, time - 1000, options, emptyList())
        pollStorage.put(poll2.id, poll2)
    }
}
