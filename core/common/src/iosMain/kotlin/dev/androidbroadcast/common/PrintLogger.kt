package dev.androidbroadcast.common

import platform.Foundation.NSLog

public class PrintLogger(
) : Logger {
    private fun printMessage(
        level: String,
        tag: String,
        message: String
    ) {
        NSLog("$level[$tag]: $message")
    }

    override fun d(
        tag: String,
        message: String
    ) {
        printMessage("D", tag, message)
    }

    override fun e(
        tag: String,
        message: String
    ) {
        printMessage("E", tag, message)
    }
}
