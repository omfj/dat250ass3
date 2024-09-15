package com.example.dat250ass3.factory

object UnixTime {
    fun now() = (System.currentTimeMillis() / 1000).toInt()
}
