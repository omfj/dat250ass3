package com.example.dat250ass3.exception

class PollExpiredException : RuntimeException {
    constructor(id: String) : super("Poll with id $id has expired")

    constructor() : super("Poll has expired")
}
