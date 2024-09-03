package com.example.dat250ass2.factory

import java.util.UUID

object IdFactory {
    fun generateId() = UUID.randomUUID().toString()
}
