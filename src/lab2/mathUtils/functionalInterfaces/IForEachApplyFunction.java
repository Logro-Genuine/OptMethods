package lab2.mathUtils.functionalInterfaces;

@FunctionalInterface
public interface IForEachApplyFunction<T>{
    T call(T element);
}
