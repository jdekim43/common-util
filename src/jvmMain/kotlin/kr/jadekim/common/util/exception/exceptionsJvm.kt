package kr.jadekim.common.util.exception

import java.io.File
import java.util.*

//private const val defaultMessageResourcesPath = "kr.jadekim.common.util.exception.message"
//private val defaultMessageResourcesUri = FriendlyException::class.java.getResource(defaultMessageResourcesPath).toURI()
//private val defaultMessages = File(defaultMessageResourcesUri).readMessageResources()
//
//actual var messageMap: MessageMap = defaultMessages.fold(mutableMapOf()) { acc, messages ->
//    messages.loadMessagesTo(acc)
//}

actual var messageMap: MessageMap = mutableMapOf(
        "default" to mutableMapOf(
                "ko" to "기타오류가 발생했습니다.",
                "en" to "Exceptional errors have occurred."
        )
)

fun loadErrorMessage(directory: File) {
    directory.readMessageResources().forEach {
        it.loadMessagesTo(messageMap)
    }
}

fun loadErrorMessage(language: Language, file: File) {
    file.inputStream()
            .use { Properties().apply { load(it) } }
            .let { Pair(language, it) }
            .loadMessagesTo(messageMap)
}

private fun File.readMessageResources() = listFiles { file -> file.extension == "properties" }
        ?.map {
            it.name.toLanguage() to it.inputStream().use { Properties().apply { load(it) } }
        }
        ?.map { it.first to it.second }
        ?: emptyList()

private fun String.toLanguage(): Language = substringBefore(".", "")

private fun Pair<Language, Properties>.loadMessagesTo(messageMap: MessageMap): MessageMap {
    second.forEach { messageName, message ->
        val languageMap = messageMap.getOrPut(messageName.toString()) { mutableMapOf() }
        languageMap[first] = message.toString()
    }

    return messageMap
}