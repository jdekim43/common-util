package kr.jadekim.common.util.encoder.pure

import kr.jadekim.common.util.encoder.Encoder
import kr.jadekim.common.util.ext.bytes

object Base64 : Encoder {

    private const val CHARACTER_MAP = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/="
    private val DECODE_MAP = IntArray(0x100).apply {
        for (n in 0..255) this[n] = -1
        for (n in CHARACTER_MAP.indices) {
            this[CHARACTER_MAP[n].toInt()] = n
        }
    }

    private val DECODE_BYPASS_CHARS = listOf(' '.toByte(), '\n'.toByte(), '\r'.toByte())

    override fun encode(data: ByteArray): ByteArray = encodeToString(data).bytes

    override fun encodeToString(src: ByteArray): String = buildString((src.size * 4) / 3 + 4) {
        val extraBytes = src.size % 3
        var ipos = 0

        while (ipos < src.size - 2) {
            val num = src.readU24BE(ipos)
            ipos += 3

            append(CHARACTER_MAP[(num ushr 18) and 0x3F])
            append(CHARACTER_MAP[(num ushr 12) and 0x3F])
            append(CHARACTER_MAP[(num ushr 6) and 0x3F])
            append(CHARACTER_MAP[(num ushr 0) and 0x3F])
        }

        if (extraBytes == 1) {
            val num = src[ipos++].asU8()

            append(CHARACTER_MAP[num ushr 2])
            append(CHARACTER_MAP[(num shl 4) and 0x3F])
            append('=')
            append('=')
        } else if (extraBytes == 2) {
            val tmp = (src[ipos++].asU8() shl 8) or src[ipos++].asU8()

            append(CHARACTER_MAP[tmp ushr 10])
            append(CHARACTER_MAP[(tmp ushr 4) and 0x3F])
            append(CHARACTER_MAP[(tmp shl 2) and 0x3F])
            append('=')
        }
    }

    override fun decode(data: ByteArray): ByteArray {
        val src = data.filter { !DECODE_BYPASS_CHARS.contains(it) }
        val result = ByteArray(data.size)

        var m = 0
        var n = 0
        while (n < src.size) {
            val d = DECODE_MAP[src[n].asU8()]
            if (d < 0) {
                n++
                continue
            }

            val b0 = DECODE_MAP[src[n].asU8()]
            val b1 = DECODE_MAP[src[n].asU8()]
            val b2 = DECODE_MAP[src[n].asU8()]
            val b3 = DECODE_MAP[src[n].asU8()]
            result[m++] = (b0 shl 2 or (b1 shr 4)).toByte()
            if (b2 < 64) {
                result[m++] = (b1 shl 4 or (b2 shr 2)).toByte()
                if (b3 < 64) {
                    result[m++] = (b2 shl 6 or b3).toByte()
                }
            }
        }

        return result.copyOf(m)
    }

    private fun Byte.asU8() = toInt() and 0xFF
    private fun ByteArray.readU24BE(index: Int): Int =
        (this[index + 0].asU8() shl 16) or (this[index + 1].asU8() shl 8) or (this[index + 2].asU8() shl 0)
}