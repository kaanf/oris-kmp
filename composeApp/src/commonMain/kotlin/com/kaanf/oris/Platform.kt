package com.kaanf.oris

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
