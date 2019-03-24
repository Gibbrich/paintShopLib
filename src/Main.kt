import java.util.*
import kotlin.collections.ArrayList

fun main(args: Array<String>) {
    val cases = LinkedList<Case>()
    val casesQty = readLineNotNull().toInt()

    for (c in 0 until casesQty) {
        val paintsQty = readLineNotNull().toInt()
        val customersQty = readLineNotNull().toInt()

        val customers = ArrayList<Customer>(customersQty)

        for (i in 0 until customersQty) {
            val data = readLineNotNull().split(' ').map { it.toInt() }
            val paints = mutableMapOf<Int, Int>()
            for (j in (1..data.lastIndex).step(2)) {
                paints.put(data[j] - 1, data[j + 1])
            }

            customers.add(
                Customer(paints)
            )
        }

        cases.add(
            Case(paintsQty, customers)
        )
    }

    solveCases(cases).forEach(::println)
}