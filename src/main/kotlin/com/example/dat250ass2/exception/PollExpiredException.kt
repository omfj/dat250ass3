package com.example.dat250ass2.exception

class PollExpiredException(id: String) : RuntimeException("Poll with id $id has expired")
