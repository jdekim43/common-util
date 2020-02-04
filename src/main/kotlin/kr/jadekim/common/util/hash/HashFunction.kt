package kr.jadekim.common.util.hash

import kr.jadekim.common.util.encoder.asHex
import kr.jadekim.common.util.encoder.asHexString
import java.security.MessageDigest
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

interface HashFunction {

    fun hash(data: ByteArray, key: ByteArray? = null): ByteArray

    fun hash(data: String, key: ByteArray? = null): ByteArray = hash(data.toByteArray(), key)

    fun hashHex(data: ByteArray, key: ByteArray? = null): ByteArray = hash(data, key).asHex

    fun hashHex(data: String, key: ByteArray? = null): ByteArray = hash(data, key).asHex

    fun hashString(data: ByteArray, key: ByteArray? = null): String = hash(data, key).asHexString

    fun hashString(data: String, key: ByteArray? = null): String = hash(data, key).asHexString
}

class HashException(cause: Throwable?) : RuntimeException(cause?.message, cause)

@Suppress("FunctionName")
private fun HashFunction(body: (ByteArray, ByteArray?) -> ByteArray) = object : HashFunction {

    override fun hash(data: ByteArray, key: ByteArray?): ByteArray = try {
        body(data, key)
    } catch (e: Exception) {
        throw HashException(e)
    }
}

val MD5 = HashFunction { data, _ ->
    val md = MessageDigest.getInstance("MD5")

    md.update(data)

    md.digest()
}

val HMAC_SHA_1 = HashFunction { data, key ->
    val mac = Mac.getInstance("HmacSHA1")

    val keySpec = SecretKeySpec(key, "HmacSHA1")
    mac.init(keySpec)

    mac.doFinal(data)
}

val HMAC_SHA_512 = HashFunction { data, key ->
    val mac = Mac.getInstance("HmacSHA512")

    val keySpec = SecretKeySpec(key, "HmacSHA512")
    mac.init(keySpec)

    mac.doFinal(data)
}