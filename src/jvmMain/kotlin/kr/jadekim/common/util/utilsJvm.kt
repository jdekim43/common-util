package kr.jadekim.common.util

import java.io.File
import java.util.*
import kotlin.concurrent.thread

actual fun generateUUID(): String = UUID.randomUUID().toString()

actual val currentTimeMillis: Long
    get() = System.currentTimeMillis()

fun shutdownHook(block: () -> Unit) {
    Runtime.getRuntime().addShutdownHook(thread(start = false, block = block))
}

fun loadProperties(propertyFiles: List<File>, properties: Properties = Properties()): Properties {
    properties.putAll(System.getProperties())
    properties.putAll(System.getenv())

    propertyFiles
            .filter { it.canRead() }
            .map { it.inputStream() }
            .forEach {
                it.use {
                    properties.load(it)
                }
            }

    return properties
}