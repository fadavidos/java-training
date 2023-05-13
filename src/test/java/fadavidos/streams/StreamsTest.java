package fadavidos.streams;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.OptionalDouble;
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

    // Average of squares of an int array
    @Test
    void testAverage() {
        OptionalDouble average = Arrays.stream(new int[] {2, 4, 6, 8 ,10})
                .map( x -> x * x)
                .average();
        assertEquals(44D, average.orElse(0D));
    }

    //Stream form List and filter
    @Test
    void testStreamFromList(){
        List<String> people = Arrays.asList("Al", "Ankit", "Brent", "Sarika", "amanda", "Hans", "Shivika", "Sarah");
        List<String> filteredPeople = people.stream()
                .map(String::toLowerCase)
                .filter(x -> x.startsWith("a"))
                .toList();
        assertEquals(true, filteredPeople.contains("al"));
        assertEquals(false, filteredPeople.contains("brent"));
    }

    @Test
    void testStreamRowsFromTextFile() throws IOException {
        Stream<String> bands = Files.lines(Paths.get("src/test/resources/bands.txt"));
        List<String> bands13 = bands
                .sorted()
                .filter(x -> x.length() > 13)
                .toList();
        assertEquals(true, bands13.contains("Mumford and Sons"));
        assertEquals(false, bands13.contains("Elvis"));
    }



}
