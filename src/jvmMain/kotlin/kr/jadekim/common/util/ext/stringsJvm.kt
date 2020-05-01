package kr.jadekim.common.util.ext

actual val String.bytes: ByteArray
    get() = toByteArray()

actual val String.chars: CharArray
    get() = toCharArray()

actual val ByteArray.asString: String
    get() = String(this)