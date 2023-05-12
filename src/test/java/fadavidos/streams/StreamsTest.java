package fadavidos.streams;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StreamsTest {

    // Integer Stream
    @Test
    void testCreateAIntStream() {
        List<Integer> numbers = IntStream.range(1, 10)
                .boxed()
                .toList();

        assertEquals(9, numbers.size());

    }

    // Integer Stream with skip
    @Test
    void testSkipStream() {
        List<Integer> numbers = IntStream
                .range(1, 10)
                .skip(5)
                .boxed()
                .toList();
        assertEquals(4, numbers.size());
    }

    // Integer Stream with sum
    @Test
    void testSumStream(){
        Integer total = IntStream
                .range(1, 5)
                .sum();
        assertEquals(10, total);
    }

    //Stream.of, sorted and findFirst
    @Test
    void testOfSortedAndFindFirst(){
        AtomicReference<String> firstName = new AtomicReference<>("ZZZ");
        Stream.of("Ava", "Aneri", "Alberto")
                .sorted()
                .findFirst()
                .ifPresent(name -> {
                    firstName.set(name.toUpperCase());
                });
        assertEquals("ALBERTO", firstName.get());
    }

    //Stream from Array, sort and filter
    @Test
    void testStreamFromArray(){
        String[] names = {"Al", "Ankit", "Kushal", "Brent", "Sarika", "amanda", "Hans", "Shivika", "Sarah"};
        List<String> filteredList = Arrays.stream(names)
                .filter(name -> name.startsWith("S"))
                .sorted()
                .toList();
        assertEquals(true, filteredList.contains("Sarika"));
        assertEquals(false, filteredList.contains("Kushal"));
    }

}
