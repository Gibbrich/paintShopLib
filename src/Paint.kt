import java.lang.IllegalArgumentException

data class Paint(
    val id: Int,
    val type: PaintType
)

enum class PaintType {
    GLOSSY,
    MATTE
}

fun Int.toPaintType(): PaintType = when (this) {
    0 -> PaintType.GLOSSY
    1 -> PaintType.MATTE
    else -> throw IllegalArgumentException("value must be either 0 or 1")
}