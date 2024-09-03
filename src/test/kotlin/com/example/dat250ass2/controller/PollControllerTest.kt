package com.example.dat250ass2.controller

import com.example.dat250ass2.factory.UnixTime
import com.example.dat250ass2.model.domain.Poll
import com.example.dat250ass2.model.domain.User
import com.example.dat250ass2.storage.KeyValueStorage
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
        mockMvc.perform(MockMvcRequestBuilders.get("/polls"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().json("[]"))
    }

    @Test
    fun `it lists polls`() {
        givenIHavePolls()

        mockMvc.perform(MockMvcRequestBuilders.get("/polls"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(
                MockMvcResultMatchers.content().json(
                    "[" +
                        "{" +
                        "\"id\":\"1\"," +
                        "\"question\":\"Question 1\"," +
                        "\"ownerId\":\"1\"," +
                        "\"publishedAt\":$time,\"expiresAt\":${time + 1000}," +
                        "\"options\":[]," +
                        "\"votes\":[]}" +
                        "]",
                ),
            )
    }

    // private fun givenIHaveUsers() {
    //     val user = User("1", "One", "one@example.com")
    //     userStorage.put(user.id, user)
    // }

    private fun givenIHavePolls() {
        val poll = Poll("1", "Question 1", "1", time, time + 1000, emptyList(), emptyList())
        pollStorage.put(poll.id, poll)
    }
}
