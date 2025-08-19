package com.google.unittestingsample

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform