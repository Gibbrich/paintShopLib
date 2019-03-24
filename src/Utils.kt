import java.lang.Exception
import java.lang.StringBuilder

fun IntArray.toContentString(): String {
    val sb = StringBuilder()
    this.forEach { sb.append("$it ") }

    if (sb.isNotEmpty()) {
        sb.deleteCharAt(sb.lastIndex)
    }

    return sb.toString()
}

fun readLineNotNull() = readLine() ?: throw Exception("Input line must exist")