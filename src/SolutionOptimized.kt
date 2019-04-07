import java.io.File

fun process(case: CaseNew): Batches? {
    val batches = Batches(case.paintsQty)

    var isBatchesSatisfyAllCustomers: Boolean

    do {
        isBatchesSatisfyAllCustomers = isBatchesSatisfyAllCustomers(batches, case.customers)
                ?: return null
    } while (isBatchesSatisfyAllCustomers.not())

    return batches
}

private fun isBatchesSatisfyAllCustomers(batches: Batches, customers: List<CustomerNew>): Boolean? {
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

private fun CustomerNew.isBatchesSatisfy(batches: Batches): Boolean =
    isGlossyColorsSatisfied(batches) || isMatteColorSatisfied(batches)

private fun CustomerNew.isMatteColorSatisfied(batches: Batches): Boolean = matteId
    ?.let {
        batches.get(it) == 1
    }
    ?: false

private fun CustomerNew.isGlossyColorsSatisfied(batches: Batches): Boolean =
    glossyWishList.any {
        batches.get(it) == 0
    }

fun main(args: Array<String>) {
    val input = File(args[0])
    val cases = parseInput(input)

    val answers = getAnswers(cases)

    answers.forEach(::println)
}

private fun getAnswers(cases: List<CaseNew>): List<String> =
    cases.map(::process).mapIndexed { index, batches ->
        val answer = batches?.toString() ?: "IMPOSSIBLE"
        "Case #${index + 1}: $answer"
    }

private fun parseInput(file: File): List<CaseNew> {
    val reader = file.bufferedReader()

    val casesQty = reader.readLine().toInt()
    val cases = mutableListOf<CaseNew>()

    for (c in 0 until casesQty) {
        val paintQty = reader.readLine().toInt()
        val customerQty = reader.readLine().toInt()

        val customers = mutableListOf<CustomerNew>()

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
                CustomerNew(matteId, glossyWishList)
            )
        }

        cases.add(
            CaseNew(paintQty, customers)
        )
    }

    reader.close()

    return cases
}