package kr.jadekim.common.util.ext

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

expect val String.bytes: ByteArray

expect val String.chars: CharArray

expect val ByteArray.asString: String

fun String.equalsTrue(): Boolean = equals("true", true)

@Suppress("NOTHING_TO_INLINE")
@OptIn(ExperimentalContracts::class)
inline fun String?.hasValue(blankIsValue: Boolean = false): Boolean {
    contract {
        returns(true) implies (this@hasValue != null)
    }

    return !hasNotValue(blankIsValue)
}

@Suppress("NOTHING_TO_INLINE")
@OptIn(ExperimentalContracts::class)
inline fun String?.hasNotValue(blankIsValue: Boolean = false): Boolean {
    contract {
        returns(false) implies (this@hasNotValue != null)
    }

    return if (blankIsValue) isNullOrEmpty() else isNullOrBlank()
}