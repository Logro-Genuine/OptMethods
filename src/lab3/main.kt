package lab3

import lab2.mathUtils.DoubleVector

fun main() {
    val methods = HOMDSearchMethods()

    // Test 1: gradientDescent for 2D function f(x,y) = x^2 + y^2
    println("Test 1: gradientDescent for f(x,y)=x^2 + y^2")
    val result1 = methods.gradientDescent(
        func = { v -> v[0] * v[0] + v[1] * v[1] },
        start = DoubleVector(3.0, 4.0),
        maxIterations = 1000,
        eps = 1e-6
    )
    println(result1)
    println("Expected result: point near (0.0, 0.0)\n")

    // Test 2: gradientDescent for 2D function f(x,y) = (x-2)^2 + (y+1)^2 + 3
    println("Test 2: gradientDescent for f(x,y)=(x-2)^2 + (y+1)^2 + 3")
    val result2 = methods.gradientDescent(
        func = { v -> (v[0] - 2) * (v[0] - 2) + (v[1] + 1) * (v[1] + 1) + 3 },
        start = DoubleVector(0.0, 0.0),
        maxIterations = 1000,
        eps = 1e-6
    )
    println(result2)
    println("Expected result: point near (2.0, -1.0)\n")

    // Test 1: conjugateGradient for 2D function f(x,y) = x^2 + y^2
    println("Test 1: conjugateGradient for f(x,y)=x^2 + y^2")
    val result3 = methods.conjugateGradient(
        func = { v -> v[0] * v[0] + v[1] * v[1] },
        start = DoubleVector(3.0, 4.0),
        maxIterations = 1000,
        eps = 1e-6
    )
    println(result3)
    println("Expected result: point near (0.0, 0.0)\n")

    // Test 2: conjugateGradient for 2D function f(x,y) = (x-2)^2 + (y+1)^2 + 3
    println("Test 2: conjugateGradient for f(x,y)=(x-2)^2 + (y+1)^2 + 3")
    val result4 = methods.conjugateGradient(
        func = { v -> (v[0] - 2) * (v[0] - 2) + (v[1] + 1) * (v[1] + 1) + 3 },
        start = DoubleVector(0.0, 0.0),
        maxIterations = 1000,
        eps = 1e-6
    )
    println(result4)
    println("Expected result: point near (2.0, -1.0)\n")

    // Test 1: newtonRaphsonMethod for 2D function f(x,y) = x^2 + y^2
    println("Test 1: newtonRaphsonMethod for f(x,y)=x^2 + y^2")
    val result5 = methods.newtonRaphsonMethod(
        func = { v -> v[0] * v[0] + v[1] * v[1] },
        start = DoubleVector(3.0, 4.0),
        maxIterations = 1000,
        eps = 1e-6
    )
    println(result5)
    println("Expected result: point near (0.0, 0.0)\n")

    // Test 2: newtonRaphsonMethod for 2D function f(x,y) = (x-2)^2 + (y+1)^2 + 3
    println("Test 2: newtonRaphsonMethod for f(x,y)=(x-2)^2 + (y+1)^2 + 3")
    val result6 = methods.newtonRaphsonMethod(
        func = { v -> (v[0] - 2) * (v[0] - 2) + (v[1] + 1) * (v[1] + 1) + 3 },
        start = DoubleVector(0.0, 0.0),
        maxIterations = 1000,
        eps = 1e-6
    )
    println(result6)
    println("Expected result: point near (2.0, -1.0)\n")

    // Test 1: internalPenalty for constrained optimization
    println("Test 1: internalPenalty for f(x,y)=x^2 + y^2 with x <= 1, y <= 1")
    val result7 = methods.internalPenalty(
        func = { v -> v[0] * v[0] + v[1] * v[1] },
        constraints = listOf(
            { v -> v[0] - 1.0 }, // x <= 1
            { v -> v[1] - 1.0 }  // y <= 1
        ),
        start = DoubleVector(0.5, 0.5),
        maxIterations = 1000,
        eps = 1e-6
    )
    println(result7)
    println("Expected result: point near (0.0, 0.0) within constraints\n")

    // Test 2: internalPenalty for constrained optimization
    println("Test 2: internalPenalty for f(x,y)=(x-2)^2 + (y+1)^2 with x >= 0, y <= 0")
    val result8 = methods.internalPenalty(
        func = { v -> (v[0] - 2) * (v[0] - 2) + (v[1] + 1) * (v[1] + 1) },
        constraints = listOf(
            { v -> -v[0] }, // x <= 0
            { v -> v[1] }   // y <= 0
        ),
        start = DoubleVector(1.0, -0.5),
        maxIterations = 1000,
        eps = 1e-6
    )
    println(result8)
    println("Expected result: point near (2.0, -1.0) but constrained\n")

    // Test 1: externalPenalty for constrained optimization
    println("Test 1: externalPenalty for f(x,y)=x^2 + y^2 with x <= 1, y <= 1")
    val result9 = methods.externalPenalty(
        func = { v -> v[0] * v[0] + v[1] * v[1] },
        inequalityConstraints = listOf(
            { v -> v[0] - 1.0 }, // x <= 1
            { v -> v[1] - 1.0 }  // y <= 1
        ),
        equalityConstraints = listOf(),
        start = DoubleVector(2.0, 2.0), // Starting outside feasible region
        maxIterations = 1000,
        eps = 1e-6
    )
    println(result9)
    println("Expected result: point near (1.0, 1.0) or (0.0, 0.0) within constraints\n")

    // Test 2: externalPenalty for constrained optimization with equality
    println("Test 2: externalPenalty for f(x,y)=(x-2)^2 + (y+1)^2 with x = 1")
    val result10 = methods.externalPenalty(
        func = { v -> (v[0] - 2) * (v[0] - 2) + (v[1] + 1) * (v[1] + 1) },
        inequalityConstraints = listOf(),
        equalityConstraints = listOf(
            { v -> v[0] - 1.0 } // x = 1
        ),
        start = DoubleVector(0.0, 0.0),
        maxIterations = 1000,
        eps = 1e-6
    )
    println(result10)
    println("Expected result: point near (1.0, -1.0) satisfying x = 1")
}