package lab2.mathUtils.functionalInterfaces;

@FunctionalInterface
public interface IFillFunction<T>{
    T call(int index);
}
