package kr.jadekim.common.util.ext

import java.util.*

fun Properties.getInt(key: String): Int? {
    return getProperty(key)?.toInt()
}

fun Properties.getInt(key: String, defaultValue: Int): Int {
    return getProperty(key, defaultValue.toString()).toInt()
}

fun Properties.getBoolean(key: String): Boolean? {
    return getProperty(key)?.toBoolean()
}

fun Properties.getBoolean(key: String, defaultValue: Boolean): Boolean {
    return getProperty(key, defaultValue.toString())!!.toBoolean()
}

fun Properties.getString(key: String): String? {
    return getProperty(key)
}

fun Properties.getString(key: String, defaultValue: String): String {
    return getProperty(key, defaultValue)
}