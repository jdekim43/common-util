package kr.jadekim.common.util.encoder

import java.util.Base64 as JavaBase64

actual val BASE64 = object : Encoder {

    private val encoder = JavaBase64.getEncoder()
    private val decoder = JavaBase64.getDecoder()

    override fun encode(data: ByteArray): ByteArray = encoder.encode(data)

    override fun decode(data: ByteArray): ByteArray = decoder.decode(data)
}