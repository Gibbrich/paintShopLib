package naive.businessLogic

import naive.models.NaiveCase
import naive.models.NaiveCustomer
import naive.models.Solution
import utils.getIntArray
import utils.toContentString
import java.util.*

@Deprecated("Use OptimizedSolver instead")
object NaiveSolver {
    fun solve(customers: List<NaiveCustomer>): Set<Solution> {
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

    fun pickOptimalSolutionParallel(case: NaiveCase): IntArray? {
        return case.customers
//        .parallelStream()
            .map(NaiveSolver::toSolutionsSet)
            .reduce(NaiveSolver::combineSolutions)
            .minBy {
                it.paints.values.sum()
            }
            ?.let {
                getIntArray(it, case.paintsQty)
            }
    }

    private fun toSolutionsSet(it: NaiveCustomer): Set<Solution> {
        return it.paints
            .map {
                val map = TreeMap<Int, Int>().apply {
                    put(it.key, it.value)
                }
                Solution(map)
            }
            .toSet()
    }

    fun combineSolutions(currentSolutions: Set<Solution>, acc: Set<Solution>): Set<Solution> {
        if (acc.isEmpty() or currentSolutions.isEmpty()) {
            return acc
        }

        val result = mutableSetOf<Solution>()
        for (previousSolution in acc) {
            for (currentSolution in currentSolutions) {
                for (paint in currentSolution.paints) {
                    val value = previousSolution.paints.get(paint.key)

                    if (value != null) {
                        if (value == paint.value) {
                            result.add(previousSolution)
                        }
                    } else {
                        val solution =
                            Solution(previousSolution.paints.plus(paint.key to paint.value).toSortedMap())
                        result.add(solution)
                    }
                }
            }
        }

        println(result)

        return result
    }

    fun pickOptimalSolutionParallel2(case: NaiveCase): IntArray? {
        val arr = case.customers
            .map(NaiveSolver::toSolutionsSet)
            .toTypedArray()

        asdf(arr)

        return arr[arr.lastIndex].minBy {
            it.paints.values.sum()
        }
            ?.let {
                getIntArray(it, case.paintsQty)
            }
    }

    fun asdf(array: Array<Set<Solution>>) {
        Arrays.parallelPrefix(array, NaiveSolver::combineSolutions)
    }

    fun pickOptimalSolution(case: NaiveCase): IntArray? =
        solve(case.customers)
            .minBy {
                it.paints.values.sum()
            }
            ?.let {
                getIntArray(it, case.paintsQty)
            }

    fun solveCases(cases: List<NaiveCase>): List<String> =
        cases
            .map(NaiveSolver::pickOptimalSolution)
            .mapIndexed(NaiveSolver::getStringAnswer)

    private fun getStringAnswer(index: Int, solution: IntArray?): String {
        val answer = solution
            ?.let(IntArray::toContentString)
            ?: "IMPOSSIBLE"

        return "Case #${index + 1}: $answer"
    }
}