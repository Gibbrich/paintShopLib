package optimized.businessLogic

import optimized.models.Batches
import optimized.models.ColorType
import optimized.models.OptimizedCase
import optimized.models.OptimizedCustomer

object OptimizedSolver {

    /**
     * Iterates through all customers, until every will be satisfied by paints set or
     * if there is no solution.
     *
     * @param case current case to solve
     * @return [Batches] if there is a solution, which satisfies all customers; null otherwise.
     */
    fun process(case: OptimizedCase): Batches? {
        val batches = Batches(case.paintsQty)

        var isBatchesSatisfyAllCustomers = false

        while (isBatchesSatisfyAllCustomers.not()) {
            isBatchesSatisfyAllCustomers = isBatchesSatisfyAllCustomers(
                batches,
                case.customers
            ) ?: return null
        }

        return batches
    }

    /**
     * Iterates over all customers and check, whether current batches satisfy every customer.
     * If customer was not satisfied (i. e. neither glossies nor matte paint were not included in [Batches]),
     * tries to put customers matte color to batches. If customer doesn't have matte, it means,
     * there is nos solution.
     *
     * @return false, if at least 1 customer was not satisfied by current paints set.
     * true, if all customers were satisfied by current paints set.
     * null, if there is no solution with given wishlists.
     */
    private fun isBatchesSatisfyAllCustomers(
        batches: Batches,
        customers: List<OptimizedCustomer>
    ): Boolean? {
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

    /**
     * @return true, if at least 1 glossy paint from [OptimizedCustomer.glossyWishList] or
     * matte paint is in [Batches]
     */
    private fun OptimizedCustomer.isBatchesSatisfy(batches: Batches): Boolean {
        val isMatteColorSatisfied = matteId
            ?.let { batches.getColorType(it) == ColorType.MATTE }
            ?: false

        val isGlossyColorsSatisfied = glossyWishList.any {
            batches.getColorType(it) == ColorType.GLOSSY
        }

        return isGlossyColorsSatisfied || isMatteColorSatisfied
    }
}