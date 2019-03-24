import java.util.*

fun solve(customers: List<Customer>): Set<Solution> {
    if (customers.isEmpty()) {
        return emptySet()
    }

    var solutions = customers[0].paints
        .map {
            val map = TreeMap<Int, Int>().apply {
                put(it.key, it.value)
            }
            Solution(map)
        }
        .toSet()

    for (i in 1..customers.lastIndex) {
        val currentSolutions = mutableSetOf<Solution>()
        for (paint in customers[i].paints) {
            for (previousSolution in solutions) {
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

        solutions = currentSolutions
    }

    return solutions
}

fun pickOptimalSolution(case: Case): IntArray? =
    solve(case.customers)
        .minBy {
            // todo - optimize evaluation by using SolutionNew class
            it.paints.values.sum()
        }
        ?.let {
            getIntArray(it, case.paintsQty)
        }

fun solveCases(cases: List<Case>): List<String> =
    cases
        .map(::pickOptimalSolution)
        .mapIndexed(::getStringAnswer)

private fun getStringAnswer(index: Int, solution: IntArray?): String {
    val answer = solution
        ?.let(IntArray::toContentString)
        ?: "IMPOSSIBLE"

    return "Case #${index + 1}: $answer"
}