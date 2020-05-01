package kr.jadekim.common.util.hash

import kr.jadekim.common.util.encoder.asHex
import kr.jadekim.common.util.encoder.asHexString
import kr.jadekim.common.util.ext.bytes

interface HashFunction {

    fun hash(data: ByteArray, key: ByteArray? = null): ByteArray

    fun hash(data: String, key: ByteArray? = null): ByteArray = hash(data.bytes, key)

    fun hashHex(data: ByteArray, key: ByteArray? = null): ByteArray = hash(data, key).asHex

    fun hashHex(data: String, key: ByteArray? = null): ByteArray = hash(data, key).asHex

    fun hashString(data: ByteArray, key: ByteArray? = null): String = hash(data, key).asHexString

    fun hashString(data: String, key: ByteArray? = null): String = hash(data, key).asHexString
}

class HashException(cause: Throwable?) : RuntimeException(cause?.message, cause)

@Suppress("FunctionName")
internal fun HashFunction(body: (ByteArray, ByteArray?) -> ByteArray) = object : HashFunction {

    override fun hash(data: ByteArray, key: ByteArray?): ByteArray = try {
        body(data, key)
    } catch (e: Exception) {
        throw HashException(e)
    }
}

expect val MD5: HashFunction

expect val HMAC_SHA_1: HashFunction

expect val HMAC_SHA_512: HashFunction

expect val SHA_256: HashFunction