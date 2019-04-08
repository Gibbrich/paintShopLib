package com.github.gibbrich.paintFactory.naive

import kotlin.collections.HashMap

fun main(args: Array<String>) {
//    val cases = LinkedList<naive.Case>()
//    val casesQty = optimized.readLineNotNull().toInt()
//
//    for (c in 0 until casesQty) {
//        val paintsQty = optimized.readLineNotNull().toInt()
//        val customersQty = optimized.readLineNotNull().toInt()
//
//        val customers = ArrayList<naive.Customer>(customersQty)
//
//        for (i in 0 until customersQty) {
//            val data = optimized.readLineNotNull().split(' ').map { it.toInt() }
//            val paints = mutableMapOf<Int, Int>()
//            for (j in (1..data.lastIndex).step(2)) {
//                paints.put(data[j] - 1, data[j + 1])
//            }
//
//            customers.add(
//                naive.Customer(paints)
//            )
//        }
//
//        cases.add(
//            naive.Case(paintsQty, customers)
//        )
//    }
//
//    naive.solveCases(cases).forEach(::println)

//    naive.test()

    test3()
}

fun test() {
    val maps = (0 until 2000)
        .map {
            HashMap<Int, Int>().apply {
                put(it, 0)
            }
        }

    for (i in 1000 downTo 0) {
        maps[maps.lastIndex - i].put(i, 0)
    }

    val cases = maps.map(::Customer).run { listOf(Case(2000, this)) }
    solveCases(cases).forEach(::println)
}

fun test2() {
    listOf(
        Case(
            3,
            listOf(
                Customer(
                    mapOf(
                        0 to 0,
                        1 to 0,
                        2 to 0
                    )
                ),
                Customer(
                    mapOf(
                        0 to 0,
                        1 to 1,
                        2 to 0
                    )
                ),
                Customer(
                    mapOf(
                        2 to 1
                    )
                )
            )
        )

    ).run(::solveCases).forEach(::println)
}

fun test3() {
    listOf(
        Case(
            3,
            listOf(
                Customer(
                    mapOf(
                        0 to 1
                    )
                ),
                Customer(
                    mapOf(
                        1 to 0,
                        2 to 1
                    )
                ),
                Customer(
                    mapOf(
                        0 to 0,
                        1 to 1
                    )
                )
            )
        )

    ).run(::solveCases).forEach(::println)
}

fun test4() {
    listOf(
        Case(
            3,
            listOf(
                Customer(
                    mapOf(
                        0 to 1,
                        1 to 1,
                        2 to 0
                    )
                ),
                Customer(
                    mapOf(
                        0 to 0,
                        1 to 1,
                        2 to 1
                    )
                ),
                Customer(
                    mapOf(
                        0 to 1,
                        1 to 0,
                        2 to 1
                    )
                )
            )
        )

    ).run(::solveCases).forEach(::println)
}