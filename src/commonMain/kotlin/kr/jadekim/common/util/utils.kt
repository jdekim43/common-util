package kr.jadekim.common.util

import kotlin.random.Random

private const val RANDOM_STRING_START_ASCII = 97
private const val RANDOM_STRING_END_ASCII = 122
private const val RANDOM_STRING_LENGTH = 8

val randomString: String
    get() = randomString()

fun randomString(length: Int = RANDOM_STRING_LENGTH): String {
    val builder = StringBuilder(length)

    for (i in 0 until length) {
        val randomLimitedInt = Random.nextInt(RANDOM_STRING_START_ASCII, RANDOM_STRING_END_ASCII)
        builder.append(randomLimitedInt.toChar())
    }

    return builder.toString()
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

expect fun generateUUID(): String

expect val currentTimeMillis: Long