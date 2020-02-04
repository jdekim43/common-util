package kr.jadekim.common.util.crypto

import kr.jadekim.common.util.encoder.BASE64
import java.io.ByteArrayOutputStream
import java.security.KeyFactory
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

interface Crypto {

    fun encrypt(data: ByteArray, key: ByteArray, iv: ByteArray? = null): ByteArray

    fun encrypt(data: String, key: ByteArray, iv: ByteArray? = null): ByteArray = encrypt(data.toByteArray(), key, iv)

    fun decrypt(data: ByteArray, key: ByteArray, iv: ByteArray? = null): ByteArray

    fun decrypt(data: String, key: ByteArray, iv: ByteArray? = null): ByteArray = decrypt(data.toByteArray(), key, iv)

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
        return String(decrypt(BASE64.decode(data), key, iv))
    }

    fun decryptString(data: String, key: ByteArray, iv: ByteArray? = null): String {
        return String(decrypt(BASE64.decode(data), key, iv))
    }
}

class CryptoException(cause: Throwable?) : RuntimeException(cause?.message, cause)

@Suppress("FunctionName")
private fun Crypto(
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

val AES = Crypto(
    encrypt = { data, key, iv ->
        val initialVector = iv ?: if (key.size > 16) key.sliceArray(IntRange(0, 15)) else key
        val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")

        cipher.init(Cipher.ENCRYPT_MODE, SecretKeySpec(key, "AES"), IvParameterSpec(initialVector))

        cipher.doFinal(data)
    },
    decrypt = { data, key, iv ->
        val initialVector = iv ?: if (key.size > 16) key.sliceArray(IntRange(0, 15)) else key
        val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")

        cipher.init(Cipher.DECRYPT_MODE, SecretKeySpec(key, "AES"), IvParameterSpec(initialVector))

        cipher.doFinal(data)
    }
)

val RSA_2048 = Crypto(
    encrypt = { data, key, _ ->
        val cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding")

        val keyFactory = KeyFactory.getInstance("RSA");

        val publicKeySpec = X509EncodedKeySpec(key);
        val publicKey = keyFactory.generatePublic(publicKeySpec);

        cipher.init(Cipher.ENCRYPT_MODE, publicKey)

        val input = data.inputStream()
        val output = ByteArrayOutputStream()

        val inputBuffer = ByteArray(245)

        var len: Int = input.read(inputBuffer)
        while (len != -1) {
            output.write(cipher.doFinal(inputBuffer, 0, len))

            len = input.read(inputBuffer)
        }

        output.toByteArray()
    },
    decrypt = { data, key, _ ->
        val cipher = Cipher.getInstance("RSA")

        val keyFactory = KeyFactory.getInstance("RSA");

        val privateKeySpec = PKCS8EncodedKeySpec(key)
        val privateKey = keyFactory.generatePrivate(privateKeySpec)

        cipher.init(Cipher.DECRYPT_MODE, privateKey)

        val input = data.inputStream()
        val output = ByteArrayOutputStream()

        val inputBuffer = ByteArray(256)

        var len: Int = input.read(inputBuffer)
        while (len != -1) {
            output.write(cipher.doFinal(inputBuffer, 0, len))

            len = input.read(inputBuffer)
        }

        output.toByteArray()
    }
)