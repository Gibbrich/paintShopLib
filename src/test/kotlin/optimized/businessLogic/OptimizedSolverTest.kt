package optimized.businessLogic

import optimized.models.OptimizedCase
import optimized.models.OptimizedCustomer
import optimized.getAnswers
import optimized.parseInput
import org.junit.Assert.*
import org.junit.Test
import utils.toContentString
import java.io.File

private const val INPUT_BASE_PATH = "src/test/resources/input/"
private const val ANSWER_BASE_PATH = "src/test/resources/inputAnswer/"

private const val data1 = "data1.txt"
private const val data2 = "data2.txt"
private const val data3 = "data3.txt"
private const val data4 = "data4.txt"
private const val defaultCases = "default_cases.txt"
private const val exclusive = "exclusive.txt"

class OptimizedSolverTest {

    @Test
    fun `customer with glossy paint in wishlist is satisfied`() {
        testCustomerIsSatisfied("0", 1, null, setOf(0))
    }

    @Test
    fun `customer with matte paint in wishlist is satisfied`() {
        testCustomerIsSatisfied("1", 1, 0, emptySet())
    }

    @Test
    fun `customer with second glossy paint in wishlist is satisfied`() {
        testCustomerIsSatisfied("0 0", 2, null, setOf(1))
    }

    @Test
    fun `customer with second matte paint in wishlist is satisfied`() {
        testCustomerIsSatisfied("0 1", 2, 1, emptySet())
    }

    private fun testCustomerIsSatisfied(
            expected: String,
            paintsQty: Int,
            matteId: Int?,
            wishlist: Set<Int>
    ) {
        val customer = OptimizedCustomer(matteId, wishlist)
        val case = OptimizedCase(paintsQty, listOf(customer))
        val batches = OptimizedSolver.process(case)

        assertNotNull(batches)
        assertEquals(expected, batches.toString())
    }

    @Test
    fun `customer with empty wish list is not satisfied`() {
        val customer = OptimizedCustomer(null, emptySet())
        val case = OptimizedCase(1, listOf(customer))

        val batches = OptimizedSolver.process(case)
        assertNull(batches)
    }

    @Test
    fun `answers for files with input from resources equals corresponding files with answers`() {
        val casesAndAnswers = listOf(
                File(INPUT_BASE_PATH + data1) to File(ANSWER_BASE_PATH + data1),
                File(INPUT_BASE_PATH + data2) to File(ANSWER_BASE_PATH + data2),
                File(INPUT_BASE_PATH + data3) to File(ANSWER_BASE_PATH + data3),
                File(INPUT_BASE_PATH + data4) to File(ANSWER_BASE_PATH + data4),
                File(INPUT_BASE_PATH + defaultCases) to File(ANSWER_BASE_PATH + defaultCases),
                File(INPUT_BASE_PATH + exclusive) to File(ANSWER_BASE_PATH + exclusive)
        )

        casesAndAnswers.forEach {
            val result = getAnswers(parseInput(it.first)).joinToString("\n")
            val expected = it.second.readText()
            assertEquals(expected, result)
        }
    }

    @Test
    fun `big test`() {
        val maps = (0 until 2000)
                .map {
                    mutableSetOf(it)
                }

        for (i in 1000 downTo 0) {
            maps[maps.lastIndex - i].add(i)

        }

        val cases = maps
                .map { OptimizedCustomer(null, it) }
                .run {
                    listOf(
                            OptimizedCase(
                                    2000,
                                    this
                            )
                    )
                }

        val result = getAnswers(cases).joinToString("\n")
        val expected = "Case #1: ${IntArray(2000) { 0 }.toContentString()}"

        assertEquals(expected, result)
    }
}