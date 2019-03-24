import java.util.*

fun solve(
    customers: List<Customer>,
    customerId: Int = customers.lastIndex
): Set<Solution> {
    if (customers.isEmpty()) {
        return emptySet()
    }

    if (customerId == 0) {
        return customers[customerId].paints
            .map {
                val map = TreeMap<Int, Int>().apply {
                    put(it.key, it.value)
                }
                Solution(map)
            }
            .toSet()
    }

    val previousSolutions = solve(customers, customerId - 1)
    val currentSolutions = mutableSetOf<Solution>()
    for (paint in customers[customerId].paints) {
        for (previousSolution in previousSolutions) {
            previousSolution.paints.get(paint.key)?.let {
                if (it == paint.value) {
                    currentSolutions.add(previousSolution)
                }
            } ?: run {
                previousSolution.paints.put(paint.key, paint.value)
                currentSolutions.add(previousSolution)
            }
        }
    }

    return currentSolutions
}

fun pickOptimalSolution(case: Case): IntArray? =
    solve(case.customers)
        .minBy {
            // todo - optimize evaluation by using SolutionNew class
            it.paints.values.sum()
        }
        ?.let {
            it.toIntArray(case.paintsQty)
        }

fun solveCases(cases: List<Case>): List<String> =
    cases
        .map(::pickOptimalSolution)
        .mapIndexed { index, solution ->
            val solutionString = solution
                ?.let(IntArray::toContentString)
                ?: "IMPOSSIBLE"

            "Case #${index + 1}: $solutionString"
        }