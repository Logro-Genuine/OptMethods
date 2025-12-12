package lab2

import lab2.mathUtils.DoubleVector

class NDExtremumMethods {
    data class Result(
        val result: Double,
        val point: DoubleVector,
        val iterations: Int,
        val functionCalls: Int,
        val precision: Double
    ) {
        override fun toString(): String {
            return """
        |Extremum search result:
        |Point: $point
        |Function value: $result
        |Iterations: $iterations
        |Function calls: $functionCalls
        |Achieved precision: $precision
        """.trimMargin()
        }
    }

    fun dichotomy(
        func: (DoubleVector) -> Double,
        lhs: DoubleVector, rhs: DoubleVector,
        eps: Double,
        maxIters: Int
    ): Result {
        var lhs_copy = DoubleVector(lhs)
        var rhs_copy = DoubleVector(rhs)
        var iterations = 0
        var functionCalls: Int
        var x_c: DoubleVector
        var x_l: DoubleVector
        var x_r: DoubleVector

        while ((DoubleVector.sub(rhs_copy, lhs_copy).magnitude() >= 2 * eps) && (iterations < maxIters)) {
            // dir = (rhs - lhs).normalized() * (eps * 0.1)
            val dir = DoubleVector.sub(rhs_copy, lhs_copy)
                .normalized()
                .mul(eps * 0.1)

            // x_c = (lhs + rhs) / 2
            x_c = DoubleVector.add(lhs_copy, rhs_copy)
                .div(2.0)

            // x_lr = x_c +- dir
            x_l = DoubleVector.sub(x_c, dir)
            x_r = DoubleVector.add(x_c, dir)

            if (func(x_l) > func(x_r)) {
                lhs_copy = DoubleVector(x_l)
            } else {
                rhs_copy = DoubleVector(x_r)
            }
            iterations++
        }

        val res_point = DoubleVector.add(lhs_copy, rhs_copy).div(2.0)
        val res_value = func(res_point)
        functionCalls = iterations * 2

        return Result(
            res_value,
            res_point,
            iterations,
            functionCalls,
            DoubleVector.sub(rhs_copy, lhs_copy).magnitude() * 0.5
        )
    }

    fun goldenRatio(
        func: (DoubleVector) -> Double,
        lhs: DoubleVector,
        rhs: DoubleVector,
        eps: Double,
        maxIters: Int
    ): Result {
        val phi = 0.61803398874989484820

        // текущие границы отрезка
        var lhs = DoubleVector(lhs)
        var rhs = DoubleVector(rhs)

        // внутренние точки отрезка
        var xR = DoubleVector.add(lhs, DoubleVector.sub(rhs, lhs).mul(phi))   // rhs‑phi*(rhs‑lhs)
        var xL = DoubleVector.sub(rhs, DoubleVector.sub(rhs, lhs).mul(phi)) // lhs+phi*(rhs‑lhs)

        var fL = func(xL)
        var fR = func(xR)

        var iter = 0
        var functionCalls = 2   // первые два вызова

        while (iter < maxIters && DoubleVector.sub(rhs, lhs).magnitude() > 2 * eps) {
            if (fL > fR) {
                // минимум находится в правой части
                lhs = DoubleVector(xL)
                xL = DoubleVector(xR)               // сдвигаем левую точку
                fL = fR
                // новая правая точка
                xR = DoubleVector.add(lhs, DoubleVector.sub(rhs, lhs).mul(phi))
                fR = func(xR)
            } else {
                // минимум находится в левой части
                rhs = DoubleVector(xR)
                xR = DoubleVector(xL)                // сдвигаем правую точку
                fR = fL
                // новая левая точка
                xL = DoubleVector.sub(rhs, DoubleVector.sub(rhs, lhs).mul(phi))
                fL = func(xL)
            }
            iter++
            functionCalls += 1   // каждый цикл делает один дополнительный вызов
        }

        // окончательная точка – середина оставшегося отрезк

        val point = DoubleVector.add(lhs, rhs).div(2.0)
        val value = func(point)

        return Result(
            result = value,
            point = point,
            iterations = iter,
            functionCalls = functionCalls,
            precision = DoubleVector.sub(rhs, lhs).magnitude() * 0.5
        )
    }

    fun fibonacci(
        func: (DoubleVector) -> Double,
        lhs: DoubleVector,
        rhs: DoubleVector,
        eps: Double
    ): Result {
        // текущие границы отрезка
        var lhs = DoubleVector(lhs)
        var rhs = DoubleVector(rhs)

        val pres: Double = DoubleVector.sub(rhs, lhs).magnitude() * 0.5 / eps

        // Вычисляем необходимое количество итераций (n)
        var fib_prev = 1.0
        var fib_curr = 1.0
        var n = 2

        while (fib_curr <= pres) {
            val temp = fib_curr
            fib_curr += fib_prev
            fib_prev = temp
            n++
        }

        // Корректируем n для использования в алгоритме
        n -= 2 // вычитаем начальные два числа Фибоначчи

        // внутренние точки отрезка
        var fib_n = fib_curr
        var fib_n_minus_1 = fib_prev

        var xR = DoubleVector.add(lhs, DoubleVector.sub(rhs, lhs).mul(fib_n_minus_1 / fib_n))
        var xL = DoubleVector.add(lhs, DoubleVector.sub(rhs, lhs).mul((fib_n - fib_n_minus_1) / fib_n))
        var fL = func(xL)
        var fR = func(xR)

        val iter = n
        var functionCalls = 2   // первые два вызова

        while (n >= 1) {
            functionCalls += 1   // каждый цикл делает один дополнительный вызов
            n--

            // Обновляем числа Фибоначчи для текущей итерации
            val fib_n_minus_2 = fib_n - fib_n_minus_1

            if (fL > fR) {
                // минимум находится в правой части
                lhs = DoubleVector(xL)
                xL = DoubleVector(xR)               // сдвигаем левую точку
                fL = fR
                // новая правая точка
                xR = DoubleVector.add(lhs, DoubleVector.sub(rhs, lhs).mul(fib_n_minus_2 / fib_n_minus_1))
                fR = func(xR)
            } else {
                // минимум находится в левой части
                rhs = DoubleVector(xR)
                xR = DoubleVector(xL)                // сдвигаем правую точку
                fR = fL
                // новая левая точка
                xL = DoubleVector.add(
                    lhs,
                    DoubleVector.sub(rhs, lhs).mul((fib_n_minus_1 - fib_n_minus_2) / fib_n_minus_1)
                )
                fL = func(xL)
            }

            // Обновляем числа Фибоначчи для следующей итерации
            fib_n = fib_n_minus_1
            fib_n_minus_1 = fib_n_minus_2
        }

        // окончательная точка – середина оставшегося отрезка
        val point = DoubleVector.add(xR, xL).div(2.0)
        val value = func(point)

        return Result(
            result = value,
            point = point,
            iterations = iter,
            functionCalls = functionCalls,
            precision = DoubleVector.sub(rhs, lhs).magnitude() * 0.5
        )
    }

    fun coordDescent(
        func: (DoubleVector) -> Double,
        xStart: DoubleVector,
        lambda: Double,
        eps: Double,
        iterations: Int): Result {
        // текущая точка поиска
        var i = 0
        var xCurr = DoubleVector(xStart)
        var functionCalls = 0

        // предыдущая точка для проверки сходимости
        var xPrev = DoubleVector(xCurr)

        // основной цикл по количеству итераций
        while (i < iterations) {
            i += 1
            // сохраняем предыдущее значение точки
            xPrev = DoubleVector(xCurr)

            // цикл по всем координатам
            for (i in 0 until xCurr.size()) {
                // создаем единичный вектор по текущей координате
                val ei = DoubleVector(xCurr.size(), 0.0)
                ei.set(i, 1.0)

                // вычисляем точки слева и справа от текущей
                val xL = DoubleVector.sub(xCurr, ei.mul(lambda))
                val xR = DoubleVector.add(xCurr, ei.mul(lambda))

                // вычисляем значения функции в этих точках
                val fL = func(xL)
                val fR = func(xR)
                functionCalls += 2

                // выбираем направление поиска минимума
                if (fL > fR) {
                    // минимум находится справа, ищем на отрезке [xCurr, xR]
                    val result = this.fibonacci(
                        func = func,
                        lhs = xCurr,
                        rhs = xR,
                        eps = eps
                    )
                    xCurr = result.point
                    functionCalls += result.functionCalls
                } else {
                    // минимум находится слева, ищем на отрезке [xL, xCurr]
                    val result = this.fibonacci(
                        func = func,
                        lhs = xL,
                        rhs = xCurr,
                        eps = eps
                    )
                    xCurr = result.point
                    functionCalls += result.functionCalls
                }
            }

            // проверяем условие остановки по достижении точности
            if (DoubleVector.sub(xCurr, xPrev).magnitude() < eps * 2)
                break
        }

        // возвращаем результат поиска
        return Result(
            result = func(xCurr),
            point = xCurr,
            iterations = i,
            functionCalls = functionCalls,
            precision = DoubleVector.sub(xCurr, xPrev).magnitude() * 0.5
        )
    }
}