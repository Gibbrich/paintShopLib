package naive

import naive.businessLogic.NaiveSolver.solveCases
import naive.models.NaiveCase
import naive.models.NaiveCustomer
import utils.readLineNotNull
import java.util.*

fun main(args: Array<String>) {
    val cases = LinkedList<NaiveCase>()
    val casesQty = readLineNotNull().toInt()

    for (c in 0 until casesQty) {
        val paintsQty = readLineNotNull().toInt()
        val customersQty = readLineNotNull().toInt()

        val customers = ArrayList<NaiveCustomer>(customersQty)

        for (i in 0 until customersQty) {
            val data = readLineNotNull().split(' ').map { it.toInt() }
            val paints = mutableMapOf<Int, Int>()
            for (j in (1..data.lastIndex).step(2)) {
                paints.put(data[j] - 1, data[j + 1])
            }

            customers.add(
                NaiveCustomer(paints)
            )
        }

        cases.add(
            NaiveCase(paintsQty, customers)
        )
    }

    solveCases(cases).forEach(::println)
}