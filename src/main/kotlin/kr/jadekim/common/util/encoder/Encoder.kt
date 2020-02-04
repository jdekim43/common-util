package kr.jadekim.common.util.encoder

import java.util.Base64 as JavaBase64

interface Encoder {

    fun encode(data: ByteArray): ByteArray

    fun encode(data: String): ByteArray = encode(data.toByteArray())

    fun encodeToString(data: ByteArray): String = String(encode(data))

    fun encodeToString(data: String): String = encodeToString(data.toByteArray())

    fun decode(data: ByteArray): ByteArray

    fun decode(data: String): ByteArray = decode(data.toByteArray())

    fun decodeToString(data: ByteArray): String = String(decode(data))

    fun decodeToString(data: String): String = String(decode(data))
}

val BASE64 = object : Encoder {

    private val encoder = JavaBase64.getEncoder()
    private val decoder = JavaBase64.getDecoder()

    override fun encode(data: ByteArray): ByteArray = encoder.encode(data)

    override fun decode(data: ByteArray): ByteArray = decoder.decode(data)
}

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

val HEX = object : Encoder {

    private val hexCode = "0123456789ABCDEF".toCharArray()

    override fun encode(data: ByteArray): ByteArray = encodeToString(data).toByteArray()

    override fun encodeToString(data: ByteArray): String {
        val result = StringBuilder(data.size * 2)

        for (b in data) {
            val byte = b.toInt()
            result.append(hexCode[byte shr 4 and 0xF])
            result.append(hexCode[byte and 0xF])
        }

        return result.toString()
    }

    override fun decode(data: ByteArray): ByteArray {
        throw IllegalStateException("Not implemented") //TODO:
    }
}

val ByteArray.asHex
    get() = HEX.encode(this)

val String.asHex
    get() = HEX.encode(this)

val ByteArray.asHexString
    get() = HEX.encodeToString(this)

val String.asHexString
    get() = HEX.encodeToString(this)