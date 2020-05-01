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

fun Int.toByteArray() = byteArrayOf(
        (this and 0xFF).toByte(),
        ((this ushr 8) and 0xFF).toByte(),
        ((this ushr 16) and 0xFF).toByte(),
        ((this ushr 24) and 0xFF).toByte()
)

fun ByteArray.toInt(): Int {
    var result = 0

    for (i in indices) {
        result = result or ((this[i].toInt() and 0xFF) shl 8 * i)
    }

    return result
}

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

fun ByteArray.toLong(): Long {
    var result = 0L

    for (i in indices) {
        result = result or ((this[i].toInt() and 0xFF) shl 8 * i).toLong()
    }

    return result
}