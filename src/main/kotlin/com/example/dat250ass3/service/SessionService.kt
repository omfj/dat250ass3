package com.example.dat250ass3.service

import com.example.dat250ass3.factory.IdFactory
import com.example.dat250ass3.factory.UnixTime
import com.example.dat250ass3.model.domain.Session
import com.example.dat250ass3.model.domain.UserWithoutPassword
import com.example.dat250ass3.model.domain.withoutPassword
import com.example.dat250ass3.storage.KeyValueStorage
import org.springframework.stereotype.Service

@Service
class SessionService(
    private val sessionStorage: KeyValueStorage<Session>,
    private val userService: UserService,
) {
    fun validateSessionAndGetUser(sessionId: String): UserWithoutPassword? {
        val session = sessionStorage.get(sessionId) ?: return null

        val user =
            userService.getUsers().find {
                it.id == session.userId
            }

        return user?.withoutPassword()
    }

    fun createSession(userId: String): Session {
        val sessionId = IdFactory.generateId()
        val expiresAt = UnixTime.now() + 60 * 60 * 24 * 7

        val session =
            Session(
                id = sessionId,
                userId = userId,
                expiresAt = expiresAt,
            )

        sessionStorage.put(session.id, session)

        return session
    }
}
