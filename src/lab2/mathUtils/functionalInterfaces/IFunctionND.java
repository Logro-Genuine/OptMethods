package lab2.mathUtils.functionalInterfaces;

import lab2.mathUtils.DoubleVector;

@FunctionalInterface
public interface IFunctionND{
    double call(final DoubleVector arg);
}
