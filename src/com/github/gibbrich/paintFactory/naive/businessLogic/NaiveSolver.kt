package naive.businessLogic

import naive.models.NaiveCase
import naive.models.NaiveCustomer
import naive.models.Solution
import utils.getIntArray
import utils.toContentString
import java.util.*

@Deprecated("Use OptimizedSolver instead")
object NaiveSolver {
    fun solveCases(cases: List<NaiveCase>): List<String> =
            cases
                    .map(::pickOptimalSolution)
                    .mapIndexed(::getStringAnswer)

    /**
     * Iterates through all the customers; for each color from customer wishlist remember
     * it as a possible solution. In such way combines solutions for next customer.
     * This algorithm is ineffective, as it's complexity growth exponentially.
     */
    private fun solve(customers: List<NaiveCustomer>): Set<Solution> {
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
                    val value = previousSolution.paints.get(paint.key)
                    if (value != null) {
                        if (value == paint.value) {
                            currentSolutions.add(previousSolution)
                        }
                    } else {
                        val solution =
                            Solution(previousSolution.paints.plus(paint.key to paint.value).toSortedMap())
                        currentSolutions.add(solution)
                    }
                }
            }

            solutions = currentSolutions
            println(i)
        }

        return solutions
    }

    private fun pickOptimalSolution(case: NaiveCase): IntArray? =
        solve(case.customers)
            .minBy {
                it.paints.values.sum()
            }
            ?.let {
                getIntArray(it, case.paintsQty)
            }

    private fun getStringAnswer(index: Int, solution: IntArray?): String {
        val answer = solution
            ?.let(IntArray::toContentString)
            ?: "IMPOSSIBLE"

        return "Case #${index + 1}: $answer"
    }
}