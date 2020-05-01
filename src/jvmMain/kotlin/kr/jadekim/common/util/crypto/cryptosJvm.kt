package kr.jadekim.common.util.crypto

import java.io.ByteArrayOutputStream
import java.security.KeyFactory
import java.security.SecureRandom
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

actual val AES = Crypto(
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

private val secureRandom = SecureRandom()

actual val AES_RANDOM_IV = Crypto(
        encrypt = { data, key, iv ->
            val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
            val initialVector = iv ?: ByteArray(cipher.blockSize).apply { secureRandom.nextBytes(this) }

            cipher.init(Cipher.ENCRYPT_MODE, SecretKeySpec(key, "AES"), IvParameterSpec(initialVector))

            val cipherBytes = cipher.doFinal(data)
            val cipherBytesWithIv = ByteArray(initialVector.size + cipherBytes.size)

            System.arraycopy(initialVector, 0, cipherBytesWithIv, 0, initialVector.size)
            System.arraycopy(cipherBytes, 0, cipherBytesWithIv, initialVector.size, cipherBytes.size)

            cipherBytesWithIv
        },
        decrypt = { data, key, iv ->
            val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
            val cipherBytes = data.copyOfRange(cipher.blockSize, data.size)
            val ivParameterSpec = iv?.let { IvParameterSpec(it) }
                    ?: IvParameterSpec(data, 0, cipher.blockSize)

            cipher.init(Cipher.DECRYPT_MODE, SecretKeySpec(key, "AES"), ivParameterSpec)

            val original = cipher.doFinal(cipherBytes)

            var lastLength = original.size
            for (i in original.size - 1 downTo original.size - cipher.blockSize + 1) {
                if (original[i] == 0.toByte()) {
                    lastLength--
                } else {
                    break
                }
            }

            original.sliceArray(0 until lastLength)
        }
)

actual val RSA_2048 = Crypto(
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