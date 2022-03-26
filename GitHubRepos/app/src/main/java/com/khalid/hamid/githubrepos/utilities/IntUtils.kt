package com.fairprice.common.utils

fun Int?.orZero(): Int = this ?: 0

/**
 * Convert server int value where result is mapped to true
 * when value == 1
 */
fun Int.toBoolean(): Boolean = this == 1

fun IntArray.exceptFirst(): IntArray {
    return this.slice(1 until size)
        .toIntArray()
}
