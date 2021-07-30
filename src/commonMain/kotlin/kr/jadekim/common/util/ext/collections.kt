package kr.jadekim.common.util.ext

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

@Suppress("NOTHING_TO_INLINE")
@OptIn(ExperimentalContracts::class)
inline fun <T> Collection<T>?.hasValue(): Boolean {
    contract {
        returns(true) implies (this@hasValue != null)
    }

    return !hasNotValue()
}

@Suppress("NOTHING_TO_INLINE")
@OptIn(ExperimentalContracts::class)
inline fun <T> Collection<T>?.hasNotValue(): Boolean {
    contract {
        returns(false) implies (this@hasNotValue != null)
    }

    return isNullOrEmpty()
}

fun <T, K> List<T>.sequentialGroupBy(keySelector: (T) -> K): List<Pair<K, List<T>>> {
    val result = mutableListOf<Pair<K, List<T>>>()

    var prevKey: K? = null
    var prevItems = mutableListOf<T>()

    for (each in this) {
        val key = keySelector(each)
        if (prevKey == key) {
            prevItems.add(each)
            continue
        }
        prevKey = key
        prevItems = mutableListOf(each)
        result.add(key to prevItems)
    }

    return result.toList()
}

fun <T : Comparable<T>> ClosedRange<T>.iterable(next: (T) -> T?): Iterable<T> = object: Iterable<T> {

    override fun iterator(): Iterator<T> = object : Iterator<T> {
        private var isFirst = true
        private var nextValue: T? = start

        override fun hasNext(): Boolean {
            if (isFirst) {
                isFirst = false
                return true
            }

            if (nextValue == null) {
                return false
            }

            nextValue = next(nextValue!!)

            return nextValue != null && nextValue!! in this@iterable
        }

        override fun next(): T = nextValue!!
    }
}

fun <K, V> Iterable<K>.withValue(
    values: Iterable<V>,
    keySelector: (V) -> K,
    defaultValue: (K) -> V
): Iterable<V> = object : Iterable<V> {

    override fun iterator(): Iterator<V> = object : Iterator<V> {

        private val keyIterator = this@withValue.iterator()
        private val valueIterator = values.iterator()

        private var valueTemp: V? = null

        override fun hasNext(): Boolean = keyIterator.hasNext() || valueIterator.hasNext()

        override fun next(): V {
            if (!keyIterator.hasNext()) {
                return valueTemp ?: valueIterator.next()
            }

            val key = keyIterator.next()
            val value = valueTemp ?: if(valueIterator.hasNext()) valueIterator.next() else defaultValue(key)

            return if (key == keySelector(value)) {
                valueTemp = null
                value
            } else {
                valueTemp = value
                defaultValue(key)
            }
        }
    }
}

fun <K, V> Iterable<K>.withValue(
    values: Map<K, V>,
    defaultValue: (K) -> V
): Iterable<V> = object : Iterable<V> {

    override fun iterator(): Iterator<V> = object : Iterator<V> {

        private val iterator = this@withValue.iterator()

        override fun hasNext(): Boolean = iterator.hasNext()

        override fun next(): V {
            val key = iterator.next()
            return values[key] ?: defaultValue(key)
        }
    }
}