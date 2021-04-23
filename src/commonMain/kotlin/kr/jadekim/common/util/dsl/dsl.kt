package kr.jadekim.common.util.dsl

inline fun <T> blockOrNull(predicate: Boolean, block: () -> T): T? = if (predicate) {
    block()
} else {
    null
}