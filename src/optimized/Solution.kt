package optimized

fun process(case: Case): Batches? {
    val batches = Batches(case.paintsQty)

    var isBatchesSatisfyAllCustomers: Boolean

    do {
        isBatchesSatisfyAllCustomers = isBatchesSatisfyAllCustomers(batches, case.customers)
                ?: return null
    } while (isBatchesSatisfyAllCustomers.not())

    return batches
}

private fun isBatchesSatisfyAllCustomers(batches: Batches, customers: List<Customer>): Boolean? {
    var isBatchesSatisfyAllCustomers = true
    for (customer in customers) {
        if (customer.isBatchesSatisfy(batches).not()) {
            val matteId = customer.matteId ?: return null
            batches.setMatte(matteId)
            isBatchesSatisfyAllCustomers = false
        }
    }
    return isBatchesSatisfyAllCustomers
}

private fun Customer.isBatchesSatisfy(batches: Batches): Boolean {
    val isMatteColorSatisfied = matteId
        ?.let { batches.get(it) == 1 }
        ?: false

    val isGlossyColorsSatisfied = glossyWishList.any { batches.get(it) == 0 }

    return isGlossyColorsSatisfied || isMatteColorSatisfied
}