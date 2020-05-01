package kr.jadekim.common.util.crypto

import kr.jadekim.common.util.encoder.BASE64
import kr.jadekim.common.util.ext.asString
import kr.jadekim.common.util.ext.bytes

interface Crypto {

    fun encrypt(data: ByteArray, key: ByteArray, iv: ByteArray? = null): ByteArray

    fun encrypt(data: String, key: ByteArray, iv: ByteArray? = null): ByteArray = encrypt(data.bytes, key, iv)

    fun decrypt(data: ByteArray, key: ByteArray, iv: ByteArray? = null): ByteArray

    fun decrypt(data: String, key: ByteArray, iv: ByteArray? = null): ByteArray = decrypt(data.bytes, key, iv)

    fun encryptEncoded(data: ByteArray, key: ByteArray, iv: ByteArray? = null): ByteArray {
        return BASE64.encode(encrypt(data, key, iv))
    }

    fun encryptEncoded(data: String, key: ByteArray, iv: ByteArray? = null): ByteArray {
        return BASE64.encode(encrypt(data, key, iv))
    }

    fun decryptEncoded(data: ByteArray, key: ByteArray, iv: ByteArray? = null): ByteArray {
        return decrypt(BASE64.decode(data), key, iv)
    }

    fun decryptEncoded(data: String, key: ByteArray, iv: ByteArray? = null): ByteArray {
        return decrypt(BASE64.decode(data), key, iv)
    }

    fun encryptString(data: ByteArray, key: ByteArray, iv: ByteArray? = null): String {
        return BASE64.encodeToString(encrypt(data, key, iv))
    }

    fun encryptString(data: String, key: ByteArray, iv: ByteArray? = null): String {
        return BASE64.encodeToString(encrypt(data, key, iv))
    }

    fun decryptString(data: ByteArray, key: ByteArray, iv: ByteArray? = null): String {
        return decrypt(BASE64.decode(data), key, iv).asString
    }

    fun decryptString(data: String, key: ByteArray, iv: ByteArray? = null): String {
        return decrypt(BASE64.decode(data), key, iv).asString
    }
}

class CryptoException(cause: Throwable?) : RuntimeException(cause?.message, cause)

@Suppress("FunctionName")
internal fun Crypto(
    encrypt: (ByteArray, ByteArray, ByteArray?) -> ByteArray,
    decrypt: (ByteArray, ByteArray, ByteArray?) -> ByteArray
) = object : Crypto {

    override fun encrypt(data: ByteArray, key: ByteArray, iv: ByteArray?): ByteArray = try {
        encrypt(data, key, iv)
    } catch (e: Exception) {
        throw CryptoException(e)
    }

    override fun decrypt(data: ByteArray, key: ByteArray, iv: ByteArray?): ByteArray = try {
        decrypt(data, key, iv)
    } catch (e: Exception) {
        throw CryptoException(e)
    }
}

expect val AES: Crypto

expect val AES_RANDOM_IV: Crypto

expect val RSA_2048: Crypto