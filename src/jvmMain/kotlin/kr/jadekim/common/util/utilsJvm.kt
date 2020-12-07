package kr.jadekim.common.util

import java.io.File
import java.io.InputStream
import java.util.Properties
import java.util.UUID
import kotlin.concurrent.thread

actual fun generateUUID(): String = UUID.randomUUID().toString()

actual val currentTimeMillis: Long
    get() = System.currentTimeMillis()

fun shutdownHook(block: () -> Unit) {
    Runtime.getRuntime().addShutdownHook(thread(start = false, block = block))
}

fun loadPropertiesFile(propertyFiles: List<File>, properties: Properties = Properties()): Properties {
    return propertyFiles
        .filter { it.canRead() }
        .map { it.inputStream() }
        .let { loadProperties(it, properties) }
}

fun loadProperties(sources: List<InputStream>, properties: Properties = Properties()): Properties {
    properties.putAll(System.getProperties())
    properties.putAll(System.getenv())

    sources.forEach {
        it.use {
            properties.load(it)
        }
    }

    return properties
}