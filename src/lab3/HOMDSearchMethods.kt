package lab3

import lab2.mathUtils.DoubleMatrix
import lab2.mathUtils.DoubleVector
import kotlin.math.pow
import lab2.NDExtremumMethods
import kotlin.math.max

class HOMDSearchMethods {
    data class Result(
        val result: Double,
        val point: DoubleVector,
        val iterations: Int
    ) {
        override fun toString(): String {
            return """
        |Extremum search result:
        |Point: $point
        |Function value: $result
        |Iterations: $iterations
        """.trimMargin()
        }
    }

    fun gradientDescent (func: (DoubleVector) -> Double,
                         start: DoubleVector,
                         maxIterations: Int,
                         eps: Double): Result {
        var iterations = 1
        var startPoint = DoubleVector(start)
        var gradient = DoubleVector.gradient(func, startPoint, eps)
        var nextPoint = DoubleVector.sub(startPoint, gradient)
        nextPoint = NDExtremumMethods().dichotomy(func, startPoint, nextPoint, eps, maxIterations).point

        while ((DoubleVector.distance(nextPoint, startPoint) > 2*eps) && (iterations < maxIterations)){
            startPoint = DoubleVector(nextPoint)
            gradient = DoubleVector.gradient(func, startPoint, eps)
            nextPoint = DoubleVector.sub(startPoint, gradient)
            nextPoint = NDExtremumMethods().dichotomy(func, startPoint, nextPoint, eps, maxIterations).point
            iterations++
        }

        return Result(func(nextPoint), nextPoint, iterations)
    }

    fun conjugateGradient(func: (DoubleVector) -> Double,
                              start: DoubleVector,
                              maxIterations: Int,
                              eps: Double): Result{
        val lambda = 1.0
        var iterations = 1
        var startPoint = DoubleVector(start)
        var s0: DoubleVector = DoubleVector.mul(
            DoubleVector.gradient(func, startPoint),
            -1.0
        )
        var nextPoint = DoubleVector.add(startPoint, DoubleVector.mul(s0, lambda))
        nextPoint = NDExtremumMethods().dichotomy(func, startPoint, nextPoint, eps, maxIterations).point

        var omega = DoubleVector.gradient(func, nextPoint).magnitude().pow(2.0) /
                DoubleVector.gradient(func, startPoint).magnitude().pow(2.0)

        var s1 = DoubleVector.add(
            DoubleVector.mul(
                DoubleVector.gradient(func, nextPoint),
                -1.0
            ),
            DoubleVector.mul(s0, omega)
        )

        while ((DoubleVector.distance(nextPoint, startPoint) > 2 * eps) && (iterations < maxIterations)) {
            startPoint = DoubleVector(nextPoint)
            s0 = DoubleVector(s1)
            nextPoint = DoubleVector.add(startPoint, DoubleVector.mul(s0, lambda))
            nextPoint = NDExtremumMethods().dichotomy(func, startPoint, nextPoint, eps, maxIterations).point

            omega = DoubleVector.gradient(func, nextPoint).magnitude().pow(2.0) /
                    DoubleVector.gradient(func, startPoint).magnitude().pow(2.0)
            s1 = DoubleVector.add(
                DoubleVector.mul(
                    DoubleVector.gradient(func, nextPoint),
                    -1.0
                ),
                DoubleVector.mul(s0, omega)
            )
            iterations++
        }
        return Result(func(nextPoint), DoubleVector(nextPoint), iterations)
    }

    fun newtonRaphsonMethod(func: (DoubleVector) -> Double,
                            start: DoubleVector,
                            maxIterations: Int,
                            eps: Double): Result{
        var startPoint = start
        var iterations = 1
        var invHessian: DoubleMatrix = DoubleMatrix.invert(DoubleMatrix.hessian(func, startPoint, eps))
        var nextPoint = DoubleVector.sub(startPoint, DoubleMatrix.mul(invHessian, DoubleVector.gradient(func, startPoint, eps)))

        while ((DoubleVector.distance(nextPoint, startPoint) > 2*eps) && (iterations < maxIterations)){
            startPoint = nextPoint
            invHessian = DoubleMatrix.invert(DoubleMatrix.hessian(func, startPoint, eps))
            nextPoint = DoubleVector.sub(startPoint, DoubleMatrix.mul(invHessian, DoubleVector.gradient(func, startPoint, eps)))
            iterations++
        }

        return Result(func(nextPoint), nextPoint, iterations)
    }


    fun internalPenalty(
        func: (DoubleVector) -> Double,
        constraints: List<(DoubleVector) -> Double>,
        start: DoubleVector,
        maxIterations: Int,
        eps: Double
    ): Result {
        // стартовая точка должна находится в множестве подходящих точек
        for (constraint in constraints) {
            if (constraint(start) > eps) {
                throw IllegalArgumentException("Начальная точка не удовлетворяет ограничениям")
            }
        }
        var lambda = 1.0
        var currentPoint = start
        var iterations = 0
        while (iterations < maxIterations && lambda > 1e-10) {
            // функция с внутренним штрафом (логарифмический барьер)
            val penaltyFunc = { x: DoubleVector ->
                var penalty = 0.0
                for (constraint in constraints) {
                    val g = constraint(x)
                    // Для ограничения g(x) <= 0
                    if (g < -eps) {
                        penalty -= kotlin.math.ln(-g)
                    } else {
                        penalty += 1e6
                    }
                }
                func(x) + lambda * penalty
            }
            val result = gradientDescent(penaltyFunc, currentPoint, 100, eps/10)
            currentPoint = result.point
            // уменьшаем штрафа
            lambda *= 0.5
            iterations++
        }

        return Result(func(currentPoint), currentPoint, iterations)
    }

    fun externalPenalty(
        func: (DoubleVector) -> Double,
        inequalityConstraints: List<(DoubleVector) -> Double>,
        equalityConstraints: List<(DoubleVector) -> Double>,
        start: DoubleVector,
        maxIterations: Int,
        eps: Double
    ): Result {
        var lambda = 1.0
        var currentPoint = start
        var iterations = 0

        while (iterations < maxIterations && lambda < 1e8) {
            // функция с внешним штрафом (квадратичный штраф)
            val penaltyFunc = { x: DoubleVector ->
                var penalty = 0.0

                // неравенства вида g(x) <= 0
                for (constraint in inequalityConstraints) {
                    val g = constraint(x)
                    penalty += max(0.0, g).pow(2)
                }

                // равенства вида h(x) = 0
                for (constraint in equalityConstraints) {
                    val h = constraint(x)
                    penalty += h.pow(2)
                }
                func(x) + lambda * penalty
            }
            val result = gradientDescent(penaltyFunc, currentPoint, 100, eps/10)
            currentPoint = result.point

            // увеличиваем штраф
            lambda *= 5.0
            iterations++
        }

        return Result(func(currentPoint), currentPoint, iterations)
    }
}