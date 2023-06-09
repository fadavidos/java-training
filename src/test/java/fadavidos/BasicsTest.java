package fadavidos;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BasicsTest {

    @Test
    public void staticVariableAndMethod(){
        /*
        static: Used to declare a class member (method or variable)
        that belongs to the class itself rather than to instances
        of the class.
         */
        class TestClass{
            public static String hello = "hello";
            private String bye;

            public TestClass(String bye) {
                this.bye = bye;
            }

            public static String sayHello() {
                return hello;
            }

            /*
            You can not use instance variables in a static method.
            public static String sayBye(){
                return bye; // compile error
            }
            */
        }
        assertEquals("hello", TestClass.hello);
        assertEquals("hello", TestClass.sayHello());
    }


    @Test
    public void staticClass(){
        /*
        A static class is a nested class that is declared with the static keyword.
         */

        class OuterClass{
            private static String hello = "Hello";
            static class StaticNestedClass{
                public String sayHello(){
                    return hello;
                }
            }
        }

        var outerClass = new OuterClass.StaticNestedClass();
        assertEquals("Hello",outerClass.sayHello());
    }


    @Test
    public void finalVariable(){
        class TestClass {
            public final String hello = "Hello";

            // You may not give it an initial value, but you need
            // to initialize the variable in a constructor
            public final String bye;

            TestClass(String bye) {
                this.bye = bye;
            }

            /*
            Cannot assign a value to final variable 'hello'
            public void setHello(String hello){
                this.hello = hello;
            }
            */
        }
        var test = new TestClass("See you");
        assertEquals("Hello", test.hello);
        assertEquals("See you", test.bye);
    }

    @Test
    public void finalParameter() {
        /*
        Use final for method parameters when you want to indicate
        that the parameter value should not be modified within the method
         */
        class Test{
            public String sayHello(final int num){
                // num ++; // Cannot assign a value to final variable 'num'
                return String.format("Hello number: %s", num);
            }
        }

        var test = new Test();
        assertEquals("Hello number: 3", test.sayHello(3));
    }

    @Test
    public void finalMethod(){
        
    }

}
