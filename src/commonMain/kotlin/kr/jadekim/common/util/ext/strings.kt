package kr.jadekim.common.util.ext

expect val String.bytes: ByteArray

expect val String.chars: CharArray

expect val ByteArray.asString: String

fun String?.equalsTrue(): Boolean? = this?.equals("true", true)