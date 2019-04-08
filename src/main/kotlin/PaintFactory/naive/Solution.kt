package main.kotlin.PaintFactory.naive

import java.util.*

data class Solution(
    val paints: SortedMap<Int, Int>
)

data class SolutionNew(
    private val paints: TreeMap<Int, Int>
): Comparable<SolutionNew> {
    private var matteCount = 0

    override fun compareTo(other: SolutionNew): Int = Integer.compare(matteCount, other.matteCount)

    fun put(key: Int, value: Int) {
        if (value == 1 && paints.containsKey(key).not()) {
            matteCount++
        }

        paints.put(key, value)
    }
}