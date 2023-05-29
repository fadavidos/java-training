package fadavidos;
import org.junit.jupiter.api.Test;

import java.util.function.Function;
import java.util.function.BiFunction;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class FunctionalProgrammingTest {

    static class MyMath{
        public static Integer triple(Integer x){
            return x * 3;
        }
    }

    @Test
    void functionInterface(){
        Function<Integer, Integer> myTriple = MyMath::triple;
        assertEquals(15, myTriple.apply(5));
    }

    @Test
    void lambdaExpressionsWithBody(){
        Function<Integer, Integer> absoluteValue = (x) -> {
            if(x < 0) {
                return -x;
            } else {
                return x;
            }
        };

        assertEquals(23, absoluteValue.apply(-23));
    }

    @Test
    void lambdaExpressionsWithoutBody(){
        Function<Integer, Integer> absoluteValue = x -> x < 0 ? -x : x;
        assertEquals(26, absoluteValue.apply(-26));
        assertEquals(5, absoluteValue.apply(5));
    }

    @Test
    void biFunction(){
        BiFunction<Integer, Integer, Integer> add = (x, y) -> x + y;
        assertEquals(44, add.apply(20, 24));
    }

    public interface ThreeArguments<A, B ,C ,R> {
        R apply(A a, B b, C c);
    }

    @Test
    void manyArgumentsInLambdaExpression(){
        ThreeArguments<Integer, Integer, Integer, Integer> addThree =
                (a, b, c) -> a + b + c;
        assertEquals(10, addThree.apply(5, 4, 1));
    }

    public interface NoArguments<R> {
        R apply();
    }

    @Test
    void noArgumentsInLambdaExpression(){
        NoArguments<String> sayHello = () -> "hello";
        assertEquals("hello", sayHello.apply());
    }

    public class MyMath2 {
        public static Integer add(Integer a, Integer b) {
            return a + b;
        }

        public static Integer subtract(Integer a, Integer b) {
            return a - b;
        }

        public static Integer combine(BiFunction<Integer, Integer, Integer> combineFunc) {
            return combineFunc.apply(2, 3);
        }
    }

    @Test
    void passingFunctionsAsArguments() {
        assertEquals(5, MyMath2.combine(MyMath2::add));
        assertEquals(-1, MyMath2.combine(MyMath2::subtract));
        assertEquals(10, MyMath2.combine((x, y ) -> x * 2 + y * 2));
    }

    public class Multiplier{
        public static Function<Integer, Integer> multiplier(Integer x) {
            return (Integer y) -> y * x;
        }
    }

    @Test
    void returningFunctions(){
        Function<Integer, Integer> times2 = Multiplier.multiplier(2);
        Function<Integer, Integer> times3 = Multiplier.multiplier(3);
        Function<Integer, Integer> times4 = Multiplier.multiplier(4);

        assertEquals(6, times2.apply(3));
        assertEquals(9, times3.apply(3));
        assertEquals(12, times4.apply(3));
    }
}
