package com.example.dat250ass2.controller

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
class UserControllerTest {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var userStorage: KeyValueStorage<User>

    @AfterEach
    fun cleanup() {
        userStorage.clear()
    }

    @Test
    fun `it lists no users`() {
        mockMvc.perform(MockMvcRequestBuilders.get("/users"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().json("[]"))
    }

    @Test
    fun `it lists users`() {
        givenIHaveUsers()

        mockMvc.perform(MockMvcRequestBuilders.get("/users"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().json("[{\"id\":\"123\",\"name\":\"Test User\",\"email\":\"test@user.com\"}]"))
    }

    @Test
    fun `it creates a user`() {
        mockMvc.perform(
            MockMvcRequestBuilders.post("/users")
                .contentType("application/json")
                .content("{\"name\": \"Test User\", \"email\": \"test@test.com\"}"),
        )
            .andExpect(MockMvcResultMatchers.status().isCreated)
    }

    private fun givenIHaveUsers() {
        val user = User("123", "Test User", "test@user.com")
        userStorage.put(user.id, user)
    }
}
