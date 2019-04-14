package optimized.models

import utils.toContentString

class Batches(paintsQty: Int) {
    private val array = ByteArray(paintsQty)

    fun getColorType(paintId: Int): Byte {
        return array[paintId]
    }

    fun setMatte(paintId: Int) {
        array[paintId] = ColorType.MATTE
    }

    override fun toString(): String = array.toContentString()
}