package dev.androidbroadcast.common

import android.util.Log

public class AndroidLogcatLogger: Logger {

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
