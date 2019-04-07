package optimized

class Batches(paintsQty: Int) {
    private val array = IntArray(paintsQty)

    fun get(paintId: Int): Int {
        return array[paintId]
    }

    fun set(paintId: Int, paint: Int) {
        array[paintId] = paint
    }

    override fun toString(): String = array.toContentString()
}