package optimized

import org.junit.Assert.*
import org.junit.Test
import java.io.File
import java.lang.Exception
import kotlin.test.assertFails
import kotlin.test.assertFailsWith

private const val EMPTY_FILE = "src/test/resources/invalidInput/empty.txt"
private const val FIRST_LINE_INVALID_FILE = "src/test/resources/invalidInput/first_line_invalid.txt"
private const val SECOND_LINE_INVALID_FILE = "src/test/resources/invalidInput/second_line_invalid.txt"
private const val THIRD_LINE_INVALID_FILE = "src/test/resources/invalidInput/third_line_invalid.txt"
private const val PAINTS_DATA_INVALID_FILE = "src/test/resources/invalidInput/paints_data_invalid.txt"
private const val PAINTS_DATA_INCORRECT_FORMAT = "src/test/resources/invalidInput/paints_data_incorrect_format.txt"
private const val PAINTS_DATA_INCORRECT_PAINT_NUMBER = "src/test/resources/invalidInput/paints_data_incorrect_paint_number.txt"
private const val PAINTS_DATA_INCORRECT_PAINT_TYPE = "src/test/resources/invalidInput/paints_data_incorrect_paint_type.txt"

class IOUtilsTest {

    @Test
    fun `getArguments with no arguments throws exception with no tests message`() {
        assertFailsWith<Exception>(NO_TEST_CASES) {
            getArguments(emptyArray())
        }
    }

    @Test
    fun `getArguments with more than 2 arguments throws exception with too many arguments message`() {
        assertFailsWith<Exception>(TOO_MANY_ARGUMENTS) {
            getArguments(arrayOf("first", "second", "third"))
        }
    }

    @Test
    fun `getArguments with 1 argument returns Arguments with #inputFilePath specified`() {
        val argument1 = "first"
        val result = getArguments(arrayOf(argument1))
        val expected = Arguments(argument1, null)
        assertEquals(expected, result)
    }

    @Test
    fun `getArguments with 2 argument returns Arguments with #inputFilePath and #outputFilePath specified`() {
        val argument1 = "first"
        val argument2 = "second"
        val result = getArguments(arrayOf(argument1, argument2))
        val expected = Arguments(argument1, argument2)
        assertEquals(expected, result)
    }

    @Test
    fun `parseInput throws exception if there is empty file`() {
        assertFails {
            parseInput(File(EMPTY_FILE))
        }
    }

    @Test
    fun `parseInput throws exception if first line is not a integer`() {
        assertFailsWith<Exception>(NO_CASES_QTY) {
            parseInput(File(FIRST_LINE_INVALID_FILE))
        }
    }

    @Test
    fun `parseInput throws exception if second line is not a integer`() {
        assertFailsWith<Exception>(NO_PAINTS_QTY) {
            parseInput(File(SECOND_LINE_INVALID_FILE))
        }
    }

    @Test
    fun `parseInput throws exception if third line is not an integer`() {
        assertFailsWith<Exception>(NO_CUSTOMERS_QTY) {
            parseInput(File(THIRD_LINE_INVALID_FILE))
        }
    }

    @Test
    fun `parseInput throws exception if paints data line contains not integers`() {
        assertFailsWith<Exception>(INCORRECT_PAINTS_DATA) {
            parseInput(File(PAINTS_DATA_INVALID_FILE))
        }
    }

    @Test
    fun `parseInput throws exception if paints data has incorrect format`() {
        assertFailsWith<Exception>(PAINTS_DATA_INCORRECT_FORMAT) {
            parseInput(File(PAINTS_DATA_INVALID_FILE))
        }
    }

    @Test
    fun `parseInput throws exception if paintId is incorrect`() {
        val message = incorrectPaintNumber(2, 3)
        assertFailsWith<Exception>(message) {
            parseInput(File(PAINTS_DATA_INCORRECT_PAINT_NUMBER))
        }
    }

    @Test
    fun `parseInput throws exception if paint type is incorrect`() {
        assertFailsWith<Exception>(INCORRECT_PAINT_TYPE) {
            parseInput(File(PAINTS_DATA_INCORRECT_PAINT_TYPE))
        }
    }
}