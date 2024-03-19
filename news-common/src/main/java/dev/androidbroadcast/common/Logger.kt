package dev.androidbroadcast.common

import android.util.Log

interface Logger {
    fun d(
        tag: String,
        message: String
    )

    fun e(
        tag: String,
        message: String
    )
}

fun AndroidLogcatLogger(): Logger =
    object : Logger {
        override fun d(
            tag: String,
            message: String
        ) {
            Log.d(tag, message)
        }

        override fun e(
            tag: String,
            message: String
        ) {
            Log.e(tag, message)
        }
    }
