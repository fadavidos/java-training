package fadavidos.streams;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
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

    // Stream rows from text file and save to List
    @Test
    void testStreamRowsToList() throws IOException {
        List<String> bands = Files.lines(Paths.get("src/test/resources/bands.txt"))
                .filter(x -> x.contains("jit"))
                .toList();
        assertEquals(1, bands.size());
    }

    // Stream rows from CSV file and count
    @Test
    void testStreamRowsFromCSVFilesCount() throws IOException{
        Stream<String> rows = Files.lines(Paths.get("src/test/resources/data.txt"));
        int rowCount = (int) rows
                .map(x -> x.split(","))
                .filter(x -> x.length == 3)
                .count();
        assertEquals(5, rowCount);
    }

    // Stream rows from CSV file, parse data from rows
    @Test
    void testStreamRowsFromCSVParseData() throws IOException {

        Stream<String> rows = Files.lines(Paths.get("src/test/resources/data.txt"));
        List<String> result = rows.map(x -> x.split(","))
                .filter(x -> x.length == 3)
                .map(x -> String.format("%s %s %s", x[0], x[1], x[2]))
                .toList();
        rows.close();
        assertEquals("A 12 3.7", result.stream().findFirst().orElse("NA"));
    }


    // Stream rows from CSV file store fields in HashMap
    @Test
    void testStreamRowCSVFileToHashMap() throws IOException {
        Stream<String> rows = Files.lines(Paths.get("src/test/resources/data.txt"));
        Map<String, Integer> map = new HashMap<>();
        map = rows
                .map(x -> x.split(","))
                .filter(x -> x.length == 3)
                .filter(x -> Integer.parseInt(x[1]) > 15)
                .collect(Collectors.toMap(
                        key -> key[0],
                        value -> Integer.parseInt(value[1])
                        ));
        assertEquals(17, map.get("B"));
        assertEquals(18, map.get("F"));
    }

    // Reduction - sum
    @Test
    void testStreamReduction() {
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        double total = Stream.of(7.3, 1.5, 4.8)
                .reduce(0.0, (a, b) -> a + b);
        assertEquals(13.6, Double.parseDouble(decimalFormat.format(total)));
    }

    // Reduction - summary statistics
    @Test
    void testStreamReductionStatistics() {
        IntSummaryStatistics summary = IntStream.of(7, 2, 19, 88, 73, 4, 10)
                .summaryStatistics();
        assertEquals(7, summary.getCount());
        assertEquals(203, summary.getSum());
        assertEquals(2, summary.getMin());
        assertEquals(88, summary.getMax());
        assertEquals(29, summary.getAverage());
    }




}
