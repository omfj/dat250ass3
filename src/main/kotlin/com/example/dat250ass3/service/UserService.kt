package com.example.dat250ass3.service

import com.example.dat250ass3.model.domain.User
import com.example.dat250ass3.storage.KeyValueStorage
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userStorage: KeyValueStorage<User>,
) {
    fun createUser(user: User): User {
        userStorage.put(user.id, user)
        return user
    }

    fun getUsers(): List<User> {
        return userStorage.list()
    }

    fun getUserByEmail(email: String): User? {
        return getUsers().find { it.email == email }
    }
}
