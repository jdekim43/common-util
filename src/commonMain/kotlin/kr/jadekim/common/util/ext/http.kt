package kr.jadekim.common.util.ext

fun String.addQueryParameter(key: String, value: String): String = buildString(length + key.length + value.length + 2) {
    append(this@addQueryParameter)
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

fun String.putQueryParameter(key: String, value: String): String {
    val queryStartIndex = indexOf('?')
    if (queryStartIndex < 0) {
        return addQueryParameter(key, value)
    }

    var keyStartIndex = indexOf("?$key", queryStartIndex) + 1
    if (keyStartIndex < 1) {
        keyStartIndex = indexOf("&$key", queryStartIndex) + 1
    }
    if (keyStartIndex < 1) {
        return addQueryParameter(key, value)
    }

    val result = StringBuilder(this)

    val keyEndIndex = keyStartIndex + key.length - 1
    if (keyEndIndex >= length - 1) {
        result.append('=')
    } else if (get(keyEndIndex + 1) != '=') {
        result.insert(keyEndIndex + 1, '=')
    }

    val valueStartIndex = keyStartIndex + key.length + 1
    if (valueStartIndex > length - 1) {
        result.append(value)
        return result.toString()
    }

    var valueEndIndex = result.indexOf('&', valueStartIndex) - 1
    while(result.startsWith("&amp", valueEndIndex + 1)) {
        valueEndIndex = result.indexOf('&', valueEndIndex + 2) - 1
    }
    if (valueEndIndex < 0) {
        valueEndIndex = length - 1
    }

    if (valueStartIndex > valueEndIndex) {
        result.insert(valueStartIndex, value)
    } else {
        result.deleteRange(valueStartIndex, valueEndIndex + 1)
        result.insert(valueStartIndex, value)
    }

    return result.toString()
}

fun String?.escapeJsNull(): String? {
    if (isNullOrBlank()) {
        return this
    }

    if (equals("undefined", true) || equals("null", true)) {
        return null
    }

    return this
}