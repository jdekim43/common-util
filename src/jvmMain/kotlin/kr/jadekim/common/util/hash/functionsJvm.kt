package kr.jadekim.common.util.hash

import java.security.MessageDigest
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

actual val MD5 = HashFunction { data, _ ->
    val md = MessageDigest.getInstance("MD5")

    md.update(data)

    md.digest()
}

actual val HMAC_SHA_1 = HashFunction { data, key ->
    val mac = Mac.getInstance("HmacSHA1")

    val keySpec = SecretKeySpec(key, "HmacSHA1")
    mac.init(keySpec)

    mac.doFinal(data)
}

actual val HMAC_SHA_512 = HashFunction { data, key ->
    val mac = Mac.getInstance("HmacSHA512")

    val keySpec = SecretKeySpec(key, "HmacSHA512")
    mac.init(keySpec)

    mac.doFinal(data)
}

actual val SHA_256 = HashFunction { data, _ ->
    val md = MessageDigest.getInstance("SHA-256")

    md.update(data)

    md.digest()
}