package kr.jadekim.common.util.ext

import kotlinx.coroutines.*
import kr.jadekim.common.util.Penta
import kr.jadekim.common.util.Quadra
import kotlin.coroutines.CoroutineContext

suspend fun <T1, T2> parallel(
    block1: suspend () -> T1,
    block2: suspend () -> T2,
    context: CoroutineContext? = null,
    start: CoroutineStart = CoroutineStart.DEFAULT,
): Pair<T1, T2> {
    val statement: suspend CoroutineScope.() -> Pair<T1, T2> = {
        val block1Result = async(start = start) { block1() }
        val block2Result = async(start = start) { block2() }

        block1Result.await() to block2Result.await()
    }

    return if (context == null) {
        coroutineScope(statement)
    } else {
        withContext(context, statement)
    }
}

suspend fun <T1, T2, T3> parallel(
    block1: suspend () -> T1,
    block2: suspend () -> T2,
    block3: suspend () -> T3,
    context: CoroutineContext? = null,
    start: CoroutineStart = CoroutineStart.DEFAULT,
): Triple<T1, T2, T3> {
    val statement: suspend CoroutineScope.() -> Triple<T1, T2, T3> = {
        val block1Result = async(start = start) { block1() }
        val block2Result = async(start = start) { block2() }
        val block3Result = async(start = start) { block3() }

        Triple(block1Result.await(), block2Result.await(), block3Result.await())
    }

    return if (context == null) {
        coroutineScope(statement)
    } else {
        withContext(context, statement)
    }
}

suspend fun <T1, T2, T3, T4> parallel(
    block1: suspend () -> T1,
    block2: suspend () -> T2,
    block3: suspend () -> T3,
    block4: suspend () -> T4,
    context: CoroutineContext? = null,
    start: CoroutineStart = CoroutineStart.DEFAULT,
): Quadra<T1, T2, T3, T4> {
    val statement: suspend CoroutineScope.() -> Quadra<T1, T2, T3, T4> = {
        val block1Result = async(start = start) { block1() }
        val block2Result = async(start = start) { block2() }
        val block3Result = async(start = start) { block3() }
        val block4Result = async(start = start) { block4() }

        Quadra(block1Result.await(), block2Result.await(), block3Result.await(), block4Result.await())
    }

    return if (context == null) {
        coroutineScope(statement)
    } else {
        withContext(context, statement)
    }
}

suspend fun <T1, T2, T3, T4, T5> parallel(
    block1: suspend () -> T1,
    block2: suspend () -> T2,
    block3: suspend () -> T3,
    block4: suspend () -> T4,
    block5: suspend () -> T5,
    context: CoroutineContext? = null,
    start: CoroutineStart = CoroutineStart.DEFAULT,
): Penta<T1, T2, T3, T4, T5> {
    val statement: suspend CoroutineScope.() -> Penta<T1, T2, T3, T4, T5> = {
        val block1Result = async(start = start) { block1() }
        val block2Result = async(start = start) { block2() }
        val block3Result = async(start = start) { block3() }
        val block4Result = async(start = start) { block4() }
        val block5Result = async(start = start) { block5() }

        Penta(
            block1Result.await(),
            block2Result.await(),
            block3Result.await(),
            block4Result.await(),
            block5Result.await(),
        )
    }

    return if (context == null) {
        coroutineScope(statement)
    } else {
        withContext(context, statement)
    }
}

suspend fun <T> parallel(
    vararg blocks: suspend () -> T,
    context: CoroutineContext? = null,
    start: CoroutineStart = CoroutineStart.DEFAULT
): List<T> {
    val statement: suspend CoroutineScope.() -> List<T> = {
        blocks.map { async(start = start) { it() } }.awaitAll()
    }

    return if (context == null) {
        coroutineScope(statement)
    } else {
        withContext(context, statement)
    }
}