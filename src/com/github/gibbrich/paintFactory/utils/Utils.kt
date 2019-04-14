package utils

import naive.models.Solution
import java.lang.Exception
import java.lang.StringBuilder

internal fun IntArray.toContentString(): String {
    val sb = StringBuilder()
    this.forEach { sb.append("$it ") }

    if (sb.isNotEmpty()) {
        sb.deleteCharAt(sb.lastIndex)
    }

    return sb.toString()
}

internal fun ByteArray.toContentString(): String {
    val sb = StringBuilder()
    this.forEach { sb.append("$it ") }

    if (sb.isNotEmpty()) {
        sb.deleteCharAt(sb.lastIndex)
    }

    return sb.toString()
}

internal fun List<Int>.toContentString(): String {
    val sb = StringBuilder()
    this.forEach { sb.append("$it ") }

    if (sb.isNotEmpty()) {
        sb.deleteCharAt(sb.lastIndex)
    }

    return sb.toString()
}

internal fun getIntArray(solution: Solution, size: Int): IntArray {
    val result = IntArray(size)

    solution.paints.forEach { entry ->
        result[entry.key] = entry.value
    }

    return result
}

fun readLineNotNull() = readLine() ?: throw Exception("Input line must exist")