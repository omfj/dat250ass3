package com.example.dat250ass3.factory

import java.util.UUID

object IdFactory {
    fun generateId() = UUID.randomUUID().toString()
}
