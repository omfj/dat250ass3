package com.example.dat250ass3.config

import com.example.dat250ass3.model.domain.Poll
import com.example.dat250ass3.model.domain.Session
import com.example.dat250ass3.model.domain.User
import com.example.dat250ass3.storage.KeyValueStorage
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class StorageConfig {
    @Value("\${storage.polls.namespace}")
    private lateinit var pollNamespace: String

    @Value("\${storage.users.namespace}")
    private lateinit var userNamespace: String

    @Value("\${storage.sessions.namespace}")
    private lateinit var sessionNamespace: String

    @Bean
    fun pollStorage() = KeyValueStorage(pollNamespace, Poll::class.java)

    @Bean
    fun userStorage() = KeyValueStorage(userNamespace, User::class.java)

    @Bean
    fun sessionStorage() = KeyValueStorage(sessionNamespace, Session::class.java)
}
