package fadavidos;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;


public class NewFeatures {

    enum DaysWeek {
        MONDAY,
        TUESDAY,
        WEDNESDAY,
        THURSDAY,
        FRIDAY,
        SATURDAY,
        SUNDAY
    }

    @Test
    void localVariableTypeInference(){
        var name = new String("Anna");
        var number = 1D;
        assertEquals(name, "Anna");
        assertEquals(number, 1D);
    }

    @Test
    void switchExpressions(){

        int oldNumLetters;
        var day = DaysWeek.FRIDAY;
        switch (day) {
            case MONDAY:
            case FRIDAY:
            case SUNDAY:
                oldNumLetters = 6;
                break;
            case TUESDAY:
                oldNumLetters = 7;
                break;
            case THURSDAY:
            case SATURDAY:
                oldNumLetters = 8;
                break;
            case WEDNESDAY:
                oldNumLetters = 9;
                break;
            default:
                throw new IllegalArgumentException("Not a day " + day);
        }
        assertEquals(oldNumLetters, 6);

        var newNumLetter = switch (day) {
            case MONDAY, FRIDAY, SUNDAY -> 6;
            case TUESDAY -> 7;
            case THURSDAY, SATURDAY -> 8;
            case WEDNESDAY -> 9;
        };
        assertEquals(newNumLetter, oldNumLetters);
    }

    @Test
    void textBlocks(){
        var name = "Peter";
        var lastName = "Vik";
        var text = "your name is " + name + ".\n" +
                "your last name is " + lastName + ".";

        var textBlock = """
                your name is %s.
                your last name is %s.""".formatted(name, lastName);

        assertEquals(textBlock, text);
    }

    void patterMatchingForInstanceOf(){
        class Father {
            public String name;
        }

        class Son extends Father {
            public String age;
        }

        Father someone = null;

        if (someone instanceof Son s) {

        }
    }
}
