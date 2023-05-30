package fadavidos;
import org.junit.jupiter.api.Test;
import fadavidos.model.Employee;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Function;
import java.util.function.BiFunction;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class FPTest {

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

    @Test
    void closure(){
        NoArguments<NoArguments<String>> printer = () ->{
            String name = "fadavidos";
            return () -> String.format("Hello %s", name);
        };
        NoArguments<String> greeter = printer.apply();
        assertEquals("Hello fadavidos", greeter.apply());
        // greeter function has access to name variable inside the printer function. It is closure
    }

    @Test
    void higherOrderFunctions(){
        BiFunction<Float, Float, Float> divide = (x, y) -> x / y;

        Function<BiFunction<Float, Float, Float>, BiFunction<Float, Float, Float>> secondArgIsntZeroCheck =
                (func) -> (x, y) -> {
                    if(y == 0f) {
                        return 0f;
                    }
                    return func.apply(x, y);
                };

        BiFunction<Float, Float, Float> divideSafe = secondArgIsntZeroCheck.apply(divide);
        assertEquals(0, divideSafe.apply(5f, 0f));
        assertEquals(2, divideSafe.apply(10f, 5f));
    }

    @Test
    void testPartialApplication(){
        ThreeArguments<Integer, Integer, Integer, Integer> add =
                (x, y, z) -> x + y + z;

        Function<Integer, BiFunction<Integer, Integer, Integer>> addPartial =
                (x) -> (y, z) -> add.apply(x, y, z);

        BiFunction<Integer, Integer, Integer> add5 = addPartial.apply(5);

        assertEquals(18, add5.apply(6, 7));
    }

    @Test
    void testCompositionTwoFunctions(){
        Function<Integer, Integer> timesTwo = x -> x * 2;
        Function<Integer, Integer> minusOne = x -> x - 1;

        Function<Integer, Integer> timesTwoMinusOne = timesTwo.andThen(minusOne);
        Function<Integer, Integer> timesTwoMinusOneV2 = minusOne.compose(timesTwo);

        assertEquals(19, timesTwoMinusOne.apply(10));
        assertEquals(19, timesTwoMinusOneV2.apply(10));
    }

    Employee[] employeesArr = {
            new Employee("John", 34, "developer", 80000f),
            new Employee("Hannah", 24, "developer", 95000f),
            new Employee("Bart", 50, "sales executive", 100000f),
            new Employee("Sophie", 49, "construction worker", 45600f),
            new Employee("Darren", 38, "writer", 50000f),
            new Employee("Nancy", 29, "developer", 75000f),
    };

    @Test
    void testCompositionThreeFunctions(){
        ArrayList<Employee> employees = new ArrayList<>(Arrays.asList(employeesArr));

        Function<Employee, String> getName = emp -> emp.name();
        Function<String, String> reverse = str -> new StringBuilder(str).reverse().toString();
        Function<String, String> toUpperCase = String::toUpperCase;

        Function<Employee, String> getNameReversedUpperCase = getName.andThen(reverse).andThen(toUpperCase);

        assertEquals("YCNAN", getNameReversedUpperCase.apply(employees.get(5)));
    }


}
