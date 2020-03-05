package kr.jadekim.common.util

import java.io.File
import java.util.*
import kotlin.concurrent.thread

private const val RANDOM_STRING_START_ASCII = 97
private const val RANDOM_STRING_END_ASCII = 122
private const val RANDOM_RANGE_LENGTH = (RANDOM_STRING_END_ASCII - RANDOM_STRING_START_ASCII + 1)
private const val RANDOM_STRING_LENGTH = 8

val randomString: String
    get() = randomString()

fun randomString(length: Int = RANDOM_STRING_LENGTH): String {
    val builder = StringBuilder(length)
    val random = Random()

    for (i in 0 until length) {
        val randomLimitedInt = (RANDOM_STRING_START_ASCII + (random.nextInt() % RANDOM_RANGE_LENGTH))
        builder.append(randomLimitedInt.toChar())
    }

    return builder.toString()
}

fun shutdownHook(block: () -> Unit) {
    Runtime.getRuntime().addShutdownHook(thread(start = false, block = block))
}

fun loadProperties(propertyFiles: List<File>, properties: Properties = Properties()): Properties {
    properties.putAll(System.getProperties())
    properties.putAll(System.getenv())

    propertyFiles
        .filter { it.canRead() }
        .map { it.inputStream() }
        .forEach {
            it.use {
                properties.load(it)
            }
        }

    return properties
}

fun parseArgument(vararg args: String): Map<String, List<String>> {
    val result = mutableMapOf<String, MutableList<String>>()

    for (arg in args) {
        val tokens = arg.split("=")

        if (tokens.size != 2) {
            continue
        }

        val key = tokens[0]
        val value = tokens[1]

        if (key.startsWith("--")) {
            result.getOrPut(key.substring(2)) { mutableListOf() }.add(value)
            continue
        }

        if (key.startsWith("-")) {
            result.getOrPut(key.substring(1)) { mutableListOf() }.add(value)
            continue
        }
    }

    return result
}