package com.example.dat250ass2.service

import com.example.dat250ass2.model.domain.User
import com.example.dat250ass2.storage.KeyValueStorage
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userStorage: KeyValueStorage<User>,
) {
    fun createUser(user: User) {
        userStorage.put(user.id, user)
    }

    fun getUsers(): List<User> {
        return userStorage.list()
    }
}
