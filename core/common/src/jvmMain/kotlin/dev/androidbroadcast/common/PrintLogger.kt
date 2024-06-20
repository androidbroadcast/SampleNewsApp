package dev.androidbroadcast.common

import java.io.PrintStream

public class PrintLogger(
    private val outStream: PrintStream = System.out
) : Logger {
    private fun printMessage(
        level: String,
        tag: String,
        message: String
    ) {
        outStream.println("$level[$tag]: $message")
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
