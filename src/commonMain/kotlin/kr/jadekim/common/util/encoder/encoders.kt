package kr.jadekim.common.util.encoder

import kr.jadekim.common.util.encoder.pure.Hex
import kr.jadekim.common.util.ext.asString
import kr.jadekim.common.util.ext.bytes

interface Encoder {

    fun encode(data: ByteArray): ByteArray

    fun encode(data: String): ByteArray = encode(data.bytes)

    fun encodeToString(data: ByteArray): String = encode(data).asString

    fun encodeToString(data: String): String = encodeToString(data.bytes)

    fun decode(data: ByteArray): ByteArray

    fun decode(data: String): ByteArray = decode(data.bytes)

    fun decodeToString(data: ByteArray): String = data.asString

    fun decodeToString(data: String): String = decode(data).asString
}

//Base64 Encoder
expect val BASE64: Encoder

val ByteArray.asBase64
    get() = BASE64.encode(this)

val String.asBase64
    get() = BASE64.encode(this)

val ByteArray.asBase64String
    get() = BASE64.encodeToString(this)

val String.asBase64String
    get() = BASE64.encodeToString(this)

val ByteArray.decodedBase64
    get() = BASE64.decode(this)

val String.decodedBase64
    get() = BASE64.decode(this)

val ByteArray.decodedBase64String
    get() = BASE64.decodeToString(this)

val String.decodedBase64String
    get() = BASE64.decodeToString(this)

//HEX Encoder
val HEX = Hex

val ByteArray.asHex
    get() = HEX.encode(this)

val String.asHex
    get() = HEX.encode(this)

val ByteArray.asHexString
    get() = HEX.encodeToString(this)

val String.asHexString
    get() = HEX.encodeToString(this)