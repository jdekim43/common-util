package kr.jadekim.common.util.trace

import kr.jadekim.common.util.currentTimeMillis
import kr.jadekim.common.util.generateUUID
import kotlin.coroutines.AbstractCoroutineContextElement
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.coroutineContext

var traceLogger: TraceLogger = object : TraceLogger {

    override fun log(log: TransactionLog) {
        //do nothing
    }
}

interface TraceLogger {

    fun log(log: TransactionLog)
}

data class Transaction(
    val name: String,
    val id: String = generateUUID(),
    val segments: MutableList<Segment> = mutableListOf()
)

data class Segment(
    val name: String,
    val startTime: Long,
    val endTime: Long,
    val executeTime: Long = endTime - startTime
)

data class TransactionLog(
    val segmentName: String,
    val segmentStartTime: Long,
    val segmentEndTime: Long,
    val executeTime: Long = segmentEndTime - segmentStartTime,
    val transactionId: String? = null,
    val transactionName: String? = null
) {
    fun print() = traceLogger.log(this)
}

data class TransactionContext(
    val name: String,
    val transaction: Transaction = Transaction(name)
) : AbstractCoroutineContextElement(Key) {

    companion object Key : CoroutineContext.Key<TransactionContext>
}

fun <T> trace(
    segmentName: String,
    transactionId: String? = null,
    transactionName: String? = null,
    body: () -> T
): T {
    val startTime = currentTimeMillis

    return try {
        body()
    } finally {
        val endTime = currentTimeMillis

        TransactionLog(
            segmentName,
            startTime,
            endTime,
            transactionId = transactionId,
            transactionName = transactionName
        )
            .print()
    }
}

suspend fun <T> trace(
    segmentName: String,
    transactionId: String? = null,
    transactionName: String? = null,
    body: suspend () -> T
): T {
    val transaction = coroutineContext[TransactionContext]?.transaction
    val _transactionId = transactionId ?: transaction?.id
    val _transactionName = transactionName ?: transaction?.name

    val startTime = currentTimeMillis

    return try {
        body()
    } finally {
        val endTime = currentTimeMillis

        transaction?.segments?.apply {
            add(Segment(segmentName, startTime, endTime))
        }

        TransactionLog(
            segmentName,
            startTime,
            endTime,
            transactionId = _transactionId,
            transactionName = _transactionName
        )
            .print()
    }
}