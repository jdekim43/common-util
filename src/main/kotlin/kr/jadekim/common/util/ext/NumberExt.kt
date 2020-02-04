package kr.jadekim.common.util.ext

import java.math.BigDecimal
import java.math.BigInteger
import java.text.DecimalFormat
import java.text.Format

private val formatterMap = mutableMapOf<Int, Format>()

private fun format(value: Any, scale: Int): String {
    return formatterMap.getOrPut(scale) {
        if (scale == 0) {
            DecimalFormat("#,##0")
        } else {
            val builder = StringBuilder()

            for (i in IntRange(1, scale)) {
                builder.append("#")
            }

            DecimalFormat("#,##0.$builder")
        }
    }.format(value)
}

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

fun Long.unapplyScale(scaleFactor: Long) = BigDecimal(this) / BigDecimal(scaleFactor)

fun BigDecimal.applyScale(scaleFactor: Long) = (this * BigDecimal(scaleFactor)).toLong()

fun Double.applyScale(scaleFactor: Long) = (this * scaleFactor).toLong()

fun Int.numberFormat(scale: Int = 8) = format(this, scale)

fun Long.numberFormat(scale: Int = 8) = format(this, scale)

fun Float.numberFormat(scale: Int = 8) = format(this, scale)

fun Double.numberFormat(scale: Int = 8) = format(this, scale)

fun BigInteger.numberFormat(scale: Int = 8) = format(this, scale)

fun BigDecimal.numberFormat(scale: Int = 8) = format(this, scale)

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