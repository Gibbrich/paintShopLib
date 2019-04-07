package optimized

import java.io.File

fun process(case: Case): Batches? {
    val batches = Batches(case.paintsQty)

    var isBatchesSatisfyAllCustomers: Boolean

    do {
        isBatchesSatisfyAllCustomers = isBatchesSatisfyAllCustomers(batches, case.customers)
                ?: return null
    } while (isBatchesSatisfyAllCustomers.not())

    return batches
}

private fun isBatchesSatisfyAllCustomers(batches: Batches, customers: List<Customer>): Boolean? {
    var isBatchesSatisfyAllCustomers = true
    for (customer in customers) {
        if (customer.isBatchesSatisfy(batches).not()) {
            customer.matteId
                ?.let {
                    batches.set(it, 1)
                }
                ?: return null
            isBatchesSatisfyAllCustomers = false
        }
    }
    return isBatchesSatisfyAllCustomers
}

private fun Customer.isBatchesSatisfy(batches: Batches): Boolean =
    isGlossyColorsSatisfied(batches) || isMatteColorSatisfied(batches)

private fun Customer.isMatteColorSatisfied(batches: Batches): Boolean = matteId
    ?.let {
        batches.get(it) == 1
    }
    ?: false

private fun Customer.isGlossyColorsSatisfied(batches: Batches): Boolean =
    glossyWishList.any {
        batches.get(it) == 0
    }

fun main(args: Array<String>) {
    val input = File(args[0])
    val cases = parseInput(input)

    val answers = getAnswers(cases)

    answers.forEach(::println)
}

private fun getAnswers(cases: List<Case>): List<String> =
    cases.map(::process).mapIndexed { index, batches ->
        val answer = batches?.toString() ?: "IMPOSSIBLE"
        "naive.Case #${index + 1}: $answer"
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