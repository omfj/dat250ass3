package com.example.dat250ass2.controller

import com.example.dat250ass2.factory.IdFactory
import com.example.dat250ass2.model.domain.User
import com.example.dat250ass2.model.input.AddUserInput
import com.example.dat250ass2.model.input.toUser
import com.example.dat250ass2.service.UserService
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "User")
@RestController
@RequestMapping("/users")
class UserController(
    private val userService: UserService,
) {
    @GetMapping
    fun getUsers(): List<User> {
        return userService.getUsers()
    }

    @PostMapping
    fun addUser(
        @RequestBody user: AddUserInput,
    ): User {
        val id = IdFactory.generateId()
        val userToInsert = user.toUser(id)
        userService.createUser(userToInsert)
        return userToInsert
    }
}
