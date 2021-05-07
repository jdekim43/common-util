package kr.jadekim.common.util

import kr.jadekim.common.util.ext.chars
import kotlin.math.log
import kotlin.math.log10
import kotlin.math.pow
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

private val UNIQUE_STRING_CHARACTERS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".chars
private val UNIQUE_STRING_CHARACTERS_SIZE = UNIQUE_STRING_CHARACTERS.size

val uniqueString: String
    get() = uniqueString()

fun uniqueString(length: Int = 8): String {
    val now = currentTimeMillis
    val nowDouble = now.toDouble()

    val nowEncodeLength = log(nowDouble, UNIQUE_STRING_CHARACTERS_SIZE.toDouble()).toInt() + 1
    if (nowEncodeLength > length) {
        throw IllegalArgumentException("Must greater than $nowEncodeLength")
    }

    val nowLength = log10(nowDouble).toInt() + 1

    val randomStart = UNIQUE_STRING_CHARACTERS_SIZE.toDouble().pow(length - 1).toLong()
    val randomEnd = UNIQUE_STRING_CHARACTERS_SIZE.toDouble().pow(length).toLong()
    var random = Random.nextLong(randomStart, randomEnd)
    random -= (random % (10.0.pow(nowLength))).toLong()

    return (random + now).radix(UNIQUE_STRING_CHARACTERS.size, UNIQUE_STRING_CHARACTERS)
}

val strictUniqueString: String
    get() = strictUniqueString()

fun strictUniqueString(randomSize: Int = 2): String {
    val now = currentTimeMillis
    val offset = 10.0.pow(randomSize).toLong()
    val random = Random.nextLong(offset / 10, offset - 1)

    return ((now * offset) + random).radix(UNIQUE_STRING_CHARACTERS.size, UNIQUE_STRING_CHARACTERS)
}

private val DEFAULT_RADIX_CHARACTERS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ".chars

fun Int.radix(radix: Int, characters: CharArray = DEFAULT_RADIX_CHARACTERS) = buildString {
    if (radix > characters.size) {
        throw IllegalArgumentException("Too large radix (support max ${characters.size})")
    }

    var value = this@radix

    while (value != 0) {
        append(characters[value % radix])
        value /= radix
    }

    reverse()
}

fun Long.radix(radix: Int, characters: CharArray = DEFAULT_RADIX_CHARACTERS) = buildString {
    if (radix > characters.size) {
        throw IllegalArgumentException("Too large radix (support max ${characters.size})")
    }

    var value = this@radix

    while (value != 0L) {
        append(characters[(value % radix).toInt()])
        value /= radix
    }

    reverse()
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

@Deprecated("Use currentTimeMillis()", ReplaceWith("kr.jadekim.common.util.currentTimeMillis()"))
expect val currentTimeMillis: Long

expect fun currentTimeMillis(): Long