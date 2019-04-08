package com.github.gibbrich.paintFactory.optimized

class Batches(paintsQty: Int) {
    private val array = IntArray(paintsQty)

    fun get(paintId: Int): Int {
        return array[paintId]
    }

    fun setMatte(paintId: Int) {
        array[paintId] = 1
    }

    override fun toString(): String = array.toContentString()
}