package optimized

import optimized.businessLogic.OptimizedSolver
import optimized.models.ColorType
import optimized.models.OptimizedCase
import optimized.models.OptimizedCustomer
import utils.toContentString
import java.io.File
import java.lang.Exception
import java.lang.IllegalArgumentException
import java.lang.IllegalStateException

const val NO_TEST_CASES = "You should specify file with test cases"
const val TOO_MANY_ARGUMENTS = "You should specify at most 2 arguments: input file path and output file path"
const val NO_CASES_QTY = "Line must contain number of test cases"
const val NO_PAINTS_QTY = "Line must contain number of paints in test case"
const val NO_CUSTOMERS_QTY = "Line must contain number of customers in test case"
const val INCORRECT_PAINTS_DATA = "Paints data must contain only integers"
const val INCORRECT_PAINT_TYPE = "Paint type must be either ${ColorType.GLOSSY} or ${ColorType.MATTE}"
fun incorrectNumberOfPairs(pairsQty: Int, paintsData: List<Int>) = "Customer wishlist must contain $pairsQty pairs of [paint; paintType]. Input wishlist is ${paintsData.toContentString()}"
fun incorrectPaintNumber(paintQty: Int, number: Int) = "Paint number must be in [1; $paintQty]. Input paint number is $number."

internal data class Arguments(
    val inputFilePath: String,
    val outputFilePath: String?
)

internal fun writeOutput(outputFilePath: String, answers: List<String>) {
    val outputFile = File(outputFilePath)
    val text = answers.joinToString("\n")
    outputFile.writeText(text)
}

internal fun getArguments(args: Array<String>): Arguments {
    if (args.isEmpty()) {
        throw IllegalArgumentException(NO_TEST_CASES)
    }

    if (args.size > 2) {
        throw IllegalArgumentException(TOO_MANY_ARGUMENTS)
    }

    val inputFilePath = args[0]

    val outputFilePath = if (args.size == 2) {
        args[1]
    } else {
        null
    }

    return Arguments(inputFilePath, outputFilePath)
}

internal fun getInputFile(inputFilePath: String): File {
    val input = File(inputFilePath)
    if (input.exists().not()) {
        throw IllegalStateException("Input file does not exists")
    }

    if (input.canRead().not()) {
        throw IllegalStateException("Can't read input file")
    }

    return input
}

internal fun getAnswers(cases: List<OptimizedCase>): List<String> =
    cases.map(OptimizedSolver::process).mapIndexed { index, batches ->
        val answer = batches?.toString() ?: "IMPOSSIBLE"
        "Case #${index + 1}: $answer"
    }

internal fun parseInput(file: File): List<OptimizedCase> {
    val reader = file.bufferedReader()
    val casesQty = getCasesQty(reader.readLine())
    val cases = mutableListOf<OptimizedCase>()

    for (c in 0 until casesQty) {
        val paintQty = getPaintsQty(reader.readLine())
        val customerQty = getCustomersQty(reader.readLine())
        val customers = mutableListOf<OptimizedCustomer>()

        for (i in 0 until customerQty) {
            val paintsData = getPaintsData(reader.readLine())
            val glossyWishList = HashSet<Int>()
            var matteId: Int? = null
            for (j in (1..paintsData.lastIndex).step(2)) {
                val paintId = getPaintId(paintsData, paintQty, j)
                val paintType = getPaintType(paintsData, j)

                if (paintType == ColorType.GLOSSY) {
                    glossyWishList.add(paintId)
                } else {
                    matteId = paintId
                }
            }

            customers.add(
                OptimizedCustomer(matteId, glossyWishList)
            )
        }

        cases.add(
            OptimizedCase(paintQty, customers)
        )
    }

    reader.close()

    return cases
}

private fun getCasesQty(line: String): Int {
    try {
        return line.toInt()
    } catch (e: Exception) {
        throw IllegalArgumentException(NO_CASES_QTY)
    }
}

private fun getPaintsQty(line: String): Int {
    try {
        return line.toInt()
    } catch (e: Exception) {
        throw IllegalArgumentException(NO_PAINTS_QTY)
    }
}

private fun getCustomersQty(line: String): Int {
    try {
        return line.toInt()
    } catch (e: Exception) {
        throw IllegalArgumentException(NO_CUSTOMERS_QTY)
    }
}

private fun getPaintType(paintsData: List<Int>, j: Int): Byte {
    val paintType = paintsData[j + 1].toByte()

    if (paintType == ColorType.GLOSSY || paintType == ColorType.MATTE) {
        return paintType
    } else {
        throw IllegalArgumentException(INCORRECT_PAINT_TYPE)
    }
}

private fun getPaintsData(line: String): List<Int> {
    val paintsData = try {
        line.split(' ').map(String::toInt)
    } catch (e: Exception) {
        throw IllegalArgumentException(INCORRECT_PAINTS_DATA)
    }

    val pairsQty = paintsData[0]

    if (paintsData.size - 1 != pairsQty * 2) {
        throw IllegalArgumentException(incorrectNumberOfPairs(pairsQty, paintsData))
    }

    return paintsData
}

private fun getPaintId(paintsData: List<Int>, paintQty: Int, j: Int): Int {
    val paintId = paintsData[j] - 1

    if (paintId < 0 || paintId > paintQty - 1) {
        throw IllegalArgumentException(incorrectPaintNumber(paintQty, paintsData[j]))
    }

    return paintId
}