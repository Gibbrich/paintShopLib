package com.github.gibbrich.paintFactory.optimized

import java.io.File

fun main(args: Array<String>) {
    val input = File(args[0])
    val cases = parseInput(input)

    val answers = getAnswers(cases)

    answers.forEach(::println)
}

private fun getAnswers(cases: List<Case>): List<String> =
    cases.map(::process).mapIndexed { index, batches ->
        val answer = batches?.toString() ?: "IMPOSSIBLE"
        "Case #${index + 1}: $answer"
    }

private fun parseInput(file: File): List<Case> {
    val reader = file.bufferedReader()

    val casesQty = reader.readLine().toInt()
    val cases = mutableListOf<Case>()

    for (c in 0 until casesQty) {
        val paintQty = reader.readLine().toInt()
        val customerQty = reader.readLine().toInt()

        val customers = mutableListOf<Customer>()

        for (i in 0 until customerQty) {
            val paintsData = reader.readLine().split(' ').map(String::toInt)
            val glossyWishList = HashSet<Int>()
            var matteId: Int? = null
            for (j in (1..paintsData.lastIndex).step(2)) {
                val paintId = paintsData[j] - 1
                if (paintsData[j + 1] == 0) {
                    glossyWishList.add(paintId)
                } else {
                    matteId = paintId
                }
            }

            customers.add(
                Customer(matteId, glossyWishList)
            )
        }

        cases.add(
            Case(paintQty, customers)
        )
    }

    reader.close()

    return cases
}