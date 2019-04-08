package com.github.gibbrich.paintFactory.naive

import com.github.gibbrich.paintFactory.optimized.getIntArray
import com.github.gibbrich.paintFactory.optimized.toContentString
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

fun pickOptimalSolutionParallel(case: Case): IntArray? {
    return case.customers
//        .parallelStream()
        .map(::toSolutionsSet)
        .reduce(::combineSolutions)
        .minBy {
            // todo - optimize evaluation by using naive.SolutionNew class
            it.paints.values.sum()
        }
        ?.let {
            getIntArray(it, case.paintsQty)
        }
}

private fun toSolutionsSet(it: Customer): Set<Solution> {
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

fun pickOptimalSolutionParallel2(case: Case): IntArray? {
    val arr = case.customers
        .map(::toSolutionsSet)
        .toTypedArray()

    asdf(arr)

    return arr[arr.lastIndex].minBy {
        // todo - optimize evaluation by using naive.SolutionNew class
        it.paints.values.sum()
    }
        ?.let {
            getIntArray(it, case.paintsQty)
        }
}

fun asdf(array: Array<Set<Solution>>) {
    Arrays.parallelPrefix(array, ::combineSolutions)
}

fun pickOptimalSolution(case: Case): IntArray? =
    solve(case.customers)
        .minBy {
            // todo - optimize evaluation by using naive.SolutionNew class
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

    return "naive.Case #${index + 1}: $answer"
}