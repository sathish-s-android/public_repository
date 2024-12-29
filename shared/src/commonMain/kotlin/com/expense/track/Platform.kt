package com.expense.track

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform