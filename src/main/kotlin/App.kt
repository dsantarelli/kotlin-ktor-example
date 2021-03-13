package com.ktor.example

import config.setupServer

class App {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            setupServer().start(wait = true)
        }
    }
}