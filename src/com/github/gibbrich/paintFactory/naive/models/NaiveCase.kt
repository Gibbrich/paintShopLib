package naive.models

data class NaiveCase(
    val paintsQty: Int,
    val customers: List<NaiveCustomer>
)