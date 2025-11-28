import lab2.NDExtremumMethods
import lab2.mathUtils.DoubleVector

const val PSI = 0.61803398874989484820
fun main() {
    val methods = NDExtremumMethods()

    // Test 1: dichotomy for 2D function f(x,y) = x^2 + y^2
    println("Test 1: Dichotomy for f(x,y)=x^2 + y^2")
    val result1 = methods.dichotomy(
        func = { v -> v[0] * v[0] + v[1] * v[1] },
        lhs = DoubleVector(-3.0, -3.0),
        rhs = DoubleVector(5.0, 5.0),
        eps = 1e-6,
        maxIters = 1000
    )
    println(result1)
    println("Expected result: point near (0.0, 0.0)\n")

    // Test 2: dichotomy for 2D function f(x,y) = (x-2)^2 + (y+1)^2 + 3
    println("Test 2: Dichotomy for f(x,y)=(x-2)^2 + (y+1)^2 + 3")
    val result2 = methods.dichotomy(
        func = { v -> (v[0] - 2) * (v[0] - 2) + (v[1] + 1) * (v[1] + 1) + 3 },
        lhs = DoubleVector(0.0, -5.0),
        rhs = DoubleVector(5.0, 2.0),
        eps = 1e-6,
        maxIters = 1000
    )
    println(result2)
    println("Expected result: point near (2.0, -1.0)")
    println()


    // Test 1: goldenRatio for 2D function f(x,y) = x^2 + y^2
    println("Test 1: goldenRatio for f(x,y)=x^2 + y^2")
    val resultg1 = methods.goldenRatio(
        func = { v -> v[0] * v[0] + v[1] * v[1] },
        lhs = DoubleVector(-3.0, -3.0),
        rhs = DoubleVector(5.0, 5.0),
        eps = 1e-6,
        maxIters = 1000
    )
    println(resultg1)
    println("Expected result: point near (0.0, 0.0)\n")

    // Test 2: goldenRatio for 2D function f(x,y) = (x-2)^2 + (y+1)^2 + 3
    println("Test 2: goldenRatio for f(x,y)=(x-2)^2 + (y+1)^2 + 3")
    val resultg2 = methods.goldenRatio(
        func = { v -> (v[0] - 2) * (v[0] - 2) + (v[1] + 1) * (v[1] + 1) + 3 },
        lhs = DoubleVector(0.0, -5.0),
        rhs = DoubleVector(5.0, 2.0),
        eps = 1e-6,
        maxIters = 1000
    )
    println(resultg2)
    println("Expected result: point near (2.0, -1.0)")
    println()

    // Test 1: fibonacci for 2D function f(x,y) = x^2 + y^2
    println("Test 1: fibonacci for f(x,y)=x^2 + y^2")
    val resultf1 = methods.fibonacci(
        func = { v -> v[0] * v[0] + v[1] * v[1] },
        lhs = DoubleVector(-3.0, -3.0),
        rhs = DoubleVector(5.0, 5.0),
        eps = 1e-6
    )
    println(resultf1)
    println("Expected result: point near (0.0, 0.0)\n")

    // Test 2: fibonacci for 2D function f(x,y) = (x-2)^2 + (y+1)^2 + 3
    println("Test 2: fibonacci for f(x,y)=(x-2)^2 + (y+1)^2 + 3")
    val resultf2 = methods.fibonacci(
        func = { v -> (v[0] - 2) * (v[0] - 2) + (v[1] + 1) * (v[1] + 1) + 3 },
        lhs = DoubleVector(0.0, -5.0),
        rhs = DoubleVector(5.0, 2.0),
        eps = 1e-6
    )
    println(resultf2)
    println("Expected result: point near (2.0, -1.0)")
    println()

    //Test 1: coord descend for 2D function f(x,y) = x^2 + y^2
    println("Test 1: coord descend for f(x,y)=x^2 + y^2")
    val resultd1 = methods.coordDescent(
        func = { v -> v[0] * v[0] + v[1] * v[1] },
        xStart = DoubleVector(3.0, 3.0),
        lambda = 1.0,
        eps = 1e-6,
        iterations = 1000
    )
    println(resultd1)
    println("Expected result: point near (0.0, 0.0)\n")

    //Test 2: coord descend for 2D function f(x,y) = (x-2)^2 + (y+1)^2 + 3
    println("Test 2: coord descend for f(x,y)=(x-2)^2 + (y+1)^2 + 3")
    val resultd2 = methods.coordDescent(
        func = { v -> (v[0] - 2) * (v[0] - 2) + (v[1] + 1) * (v[1] + 1) + 3 },
        xStart = DoubleVector(3.0, 3.0),
        lambda = 1.0,
        eps = 1e-6,
        iterations = 1000
    )
    println(resultd2)
    println("Expected result: point near (2.0, -1.0)")
    println()
}
