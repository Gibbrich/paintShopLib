# paintShopLib

## How to run program
    ./gradlew run --args=‘arg1 arg2’
Where:
* arg1 - path to file with input (mandatory)
* arg2 - path to file, where program will write results (optional). 

If program will run with 1 argument, output will be printed in the console.

## How to test
    ./gradlew test
Launches all unit tests

## Algorithm
There is 2 ways of solving this problem.

First - iterate through all the customers; for each color from customer wishlist remember it as a possible solution. Then 
combine all possible solutions of one customer with all possible solutions of other customer. So in general, this algorithm is
a modification of dinamyc programming approach. In fact, this can be done via reduce operation and even parallelize, 
using Java8 parallel streams, which potentially can improve performance. Unfortunately,
the complexity of this solution growth exponentially, so I switched to another algoritm. This algorithm is deprecated and
should be used only for acquaintance with approach. This one presented in class "naive.NaiveSolver".

Second - start with array of 0, then iterate all customers and check, whether current paints set satisfy every customer.
If customer was not satisfied (i. e. neither glossies nor matte paint were not included in set), try to put customer's matte
color to batches. If customer doesn't have matte, it means, there is no solution. The complexity of this algorithm is O(nm),
where n - number of customers, m - average number of paints in customer wishlist. This algorithm should bu used in production.
It presented in class "optimized.OptimizedSolver".
