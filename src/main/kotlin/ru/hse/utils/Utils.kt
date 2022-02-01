package ru.hse.utils

import ru.hse.charset.HseshCharsets
import java.io.OutputStream
import java.lang.System.lineSeparator

fun OutputStream.write(message: String?) {
    message?.let { write(it.toByteArray(HseshCharsets.default)) }
}

fun OutputStream.writeln(message: String?) {
    message?.let {
        write(it.toByteArray(HseshCharsets.default))
        write(lineSeparator().toByteArray(HseshCharsets.default))
    }
}

fun <T> failure(message: String): Result<T> {
    return Result.failure(RuntimeException(message))
}

fun String.trimMarginCrossPlatform(marginPrefix: String = "|"): String {
    return trimMargin(marginPrefix).replace("\n", lineSeparator())
}

fun String.trimIndentCrossPlatform(): String {
    return trimIndent().replace("\n", lineSeparator())
}