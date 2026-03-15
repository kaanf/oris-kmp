package com.kaanf.core.domain.logging

interface OrisLogger {
    fun debug(message: String)

    fun info(message: String)

    fun warn(message: String)

    fun error(
        message: String,
        throwable: Throwable? = null,
    )
}
