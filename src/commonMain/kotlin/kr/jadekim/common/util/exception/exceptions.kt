package kr.jadekim.common.util.exception

typealias Language = String
typealias ErrorCode = String

typealias MessageMap = MutableMap<ErrorCode, MutableMap<Language, String>>

expect var messageMap: MessageMap

private const val LANGUAGE_KOREA = "ko"

var defaultLanguage: Language = LANGUAGE_KOREA

fun setMessages(locale: Language, messages: Map<ErrorCode, String>) {
    messages.forEach { (errorCode, message) ->
        val languageMap = messageMap.getOrPut(errorCode) { mutableMapOf() }
        languageMap[locale] = message
    }
}

fun MessageMap.getErrorMessage(errorCode: String, language: Language = defaultLanguage): String {
    val localeMap = messageMap.getOrPut(errorCode) {
        messageMap["default"] ?: mutableMapOf(LANGUAGE_KOREA to "기타오류가 발생했습니다.")
    }

    return localeMap[language]
            ?: localeMap[defaultLanguage]
            ?: localeMap.values.firstOrNull()
            ?: "기타오류가 발생했습니다."
}

enum class ExceptionLevel {
    ERROR,
    WARNING,
    DEBUG;
}

open class FriendlyException(
        val code: String,
        val data: Any? = null,
        cause: Throwable? = null,
        message: String? = cause?.message,
        val level: ExceptionLevel = ExceptionLevel.WARNING
) : RuntimeException("$code : $message", cause) {

    open fun getFriendlyMessage(language: Language? = defaultLanguage) = messageMap.getErrorMessage(code, language ?: defaultLanguage)
}

class UnknownException(
        cause: Throwable,
        message: String? = null
) : FriendlyException(
        code = "COM-1",
        cause = cause,
        message = message ?: cause.message ?: "Unknown Exception",
        level = ExceptionLevel.ERROR
)

class ProgrammingException(
        message: String,
        cause: Throwable? = null
) : FriendlyException(
        code = "COM-2",
        message = message,
        cause = cause,
        level = ExceptionLevel.ERROR
)

class AssertException(
        message: String
) : FriendlyException(
        code = "COM-3",
        message = message,
        level = ExceptionLevel.ERROR
)