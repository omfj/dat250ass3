package com.example.dat250ass2.factory

object UnixTime {
    fun now() = (System.currentTimeMillis() / 1000).toInt()
}
