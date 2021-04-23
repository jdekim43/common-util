package kr.jadekim.common.util.ext

import java.util.Properties

fun Properties.getInt(key: String): Int {
    return getProperty(key)?.toInt() ?: throw IllegalArgumentException("Does not have $key in properties")
}

fun Properties.getInt(key: String, defaultValue: Int): Int {
    return getProperty(key, defaultValue.toString()).toInt()
}

fun Properties.getIntSafe(key: String): Int? {
    return getProperty(key)?.toIntOrNull()
}

fun Properties.getBoolean(key: String): Boolean {
    return getProperty(key)?.toBoolean() ?: throw IllegalArgumentException("Does not have $key in properties")
}

fun Properties.getBoolean(key: String, defaultValue: Boolean): Boolean {
    return getProperty(key, defaultValue.toString())!!.toBoolean()
}

fun Properties.getBooleanSafe(key: String): Boolean? {
    val value = getProperty(key)
    return when {
        value.equals("true", true) -> true
        value.equals("false", false) -> false
        else -> null
    }
}

fun Properties.getString(key: String): String {
    return getProperty(key) ?: throw IllegalArgumentException("Does not have $key in properties")
}

fun Properties.getString(key: String, defaultValue: String): String {
    return getProperty(key, defaultValue)
}

fun Properties.getStringSafe(key: String): String? {
    return getProperty(key)
}
