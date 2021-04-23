package kr.jadekim.common.util.ext

fun Int.dayToHour() = this * 24

fun Int.hourToMinute() = this * 60

fun Int.minuteToSecond() = this * 60

fun Int.secondToMillisecond() = this * 1000

fun Int.toBoolean() = this != 0

fun Long.dayToHour() = this * 24

fun Long.hourToMinute() = this * 60

fun Long.minuteToSecond() = this * 60

fun Long.secondToMillisecond() = this * 1000

fun <T : Comparable<T>> min(a: T, b: T) = if (a < b) a else b

fun <T : Comparable<T>> max(a: T, b: T) = if (a > b) a else b

@Deprecated("Use littleEndian", ReplaceWith("littleEndian"))
fun Int.toByteArray() = byteArrayOf(
    (this and 0xFF).toByte(),
    ((this ushr 8) and 0xFF).toByte(),
    ((this ushr 16) and 0xFF).toByte(),
    ((this ushr 24) and 0xFF).toByte()
)

val Int.bigEndian: ByteArray
    get() = byteArrayOf(
        ((this shr 24) and 0xFF).toByte(),
        ((this shr 16) and 0xFF).toByte(),
        ((this shr 8) and 0xFF).toByte(),
        (this and 0xFF).toByte()
    )

val Int.littleEndian: ByteArray
    get() = byteArrayOf(
        (this and 0xFF).toByte(),
        ((this shr 8) and 0xFF).toByte(),
        ((this shr 16) and 0xFF).toByte(),
        ((this shr 24) and 0xFF).toByte()
    )

@Deprecated("Use littleEndianToInt", ReplaceWith("littleEndianToInt"))
fun ByteArray.toInt(): Int {
    var result = 0

    for (i in indices) {
        result = result or ((this[i].toInt() and 0xFF) shl 8 * i)
    }

    return result
}

fun ByteArray.bigEndianToInt(): Int {
    var result = 0

    for (i in indices) {
        result = result or ((this[i].toInt() and 0xFF) shl (24 - 8 * i))
    }

    return result
}

fun ByteArray.littleEndianToInt(): Int {
    var result = 0

    for (i in indices) {
        result = result or ((this[i].toInt() and 0xFF) shl 8 * i)
    }

    return result
}

@Deprecated("Use littleEndian", ReplaceWith("littleEndian"))
fun Long.toByteArray() = byteArrayOf(
    (this and 0xFF).toByte(),
    ((this ushr 8) and 0xFF).toByte(),
    ((this ushr 16) and 0xFF).toByte(),
    ((this ushr 24) and 0xFF).toByte(),
    ((this ushr 32) and 0xFF).toByte(),
    ((this ushr 40) and 0xFF).toByte(),
    ((this ushr 48) and 0xFF).toByte(),
    ((this ushr 56) and 0xFF).toByte()
)

val Long.bigEndian: ByteArray
    get() = byteArrayOf(
        ((this shr 56) and 0xFF).toByte(),
        ((this shr 48) and 0xFF).toByte(),
        ((this shr 40) and 0xFF).toByte(),
        ((this shr 32) and 0xFF).toByte(),
        ((this shr 24) and 0xFF).toByte(),
        ((this shr 16) and 0xFF).toByte(),
        ((this shr 8) and 0xFF).toByte(),
        (this and 0xFF).toByte()
    )

val Long.littleEndian: ByteArray
    get() = byteArrayOf(
        (this and 0xFF).toByte(),
        ((this shr 8) and 0xFF).toByte(),
        ((this shr 16) and 0xFF).toByte(),
        ((this shr 24) and 0xFF).toByte(),
        ((this shr 32) and 0xFF).toByte(),
        ((this shr 40) and 0xFF).toByte(),
        ((this shr 48) and 0xFF).toByte(),
        ((this shr 56) and 0xFF).toByte()
    )

@Deprecated("Use littleEndianToLong", ReplaceWith("littleEndianToLong"))
fun ByteArray.toLong(): Long {
    var result = 0L

    for (i in indices) {
        result = result or ((this[i].toInt() and 0xFF) shl 8 * i).toLong()
    }

    return result
}

fun ByteArray.bigEndianToLong(): Long {
    var result = 0L

    for (i in indices) {
        result = result or ((this[i].toInt() and 0xFF) shl (56 - 8 * i)).toLong()
    }

    return result
}

fun ByteArray.littleEndianToLong(): Long {
    var result = 0L

    for (i in indices) {
        result = result or ((this[i].toInt() and 0xFF) shl 8 * i).toLong()
    }

    return result
}