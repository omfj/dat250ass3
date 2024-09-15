package com.example.dat250ass3.controller

import com.example.dat250ass3.model.domain.User
import com.example.dat250ass3.service.UserService
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "User")
@RestController
@RequestMapping("/api/users")
class UserController(
    private val userService: UserService,
) {
    @GetMapping
    fun getUsers(): List<User> {
        return userService.getUsers()
    }
}
