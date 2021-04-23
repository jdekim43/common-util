package kr.jadekim.common.util.ext

fun String.addQueryParameter(key: String, value: String): String = buildString(length + key.length + value.length + 2) {
    append(this)
    if (lastIndexOf('?', ignoreCase = true) >= 0) {
        if (!(endsWith('&') || endsWith('?'))) {
            append('&')
        }
    } else {
        append('?')
    }
    append(key)
    append('=')
    append(value)
}