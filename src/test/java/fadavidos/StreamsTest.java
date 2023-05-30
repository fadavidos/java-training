package fadavidos;

import fadavidos.model.Employee;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class StreamsTest {

    @Test
    void mapFunction(){
        Integer[] intArray = {1, 2, 3, 4, 5};
        ArrayList<Integer> listOfIntegers = new ArrayList<>(Arrays.asList(intArray));
        Function<Integer, Integer> timesTwo = (number) -> number * 2;
        List<Integer> doubled = listOfIntegers
                .stream()
                .map(timesTwo)
                .toList();
        assertEquals(10, doubled.get(4));
        assertEquals(6, doubled.get(2));
    }

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
        Predicate<String> startWithS = (name) -> name.startsWith("S");
        String[] names = {"Al", "Ankit", "Kushal", "Brent", "Sarika", "amanda", "Hans", "Shivika", "Sarah"};
        List<String> filteredList = Arrays.stream(names)
                .filter(startWithS)
                .sorted()
                .toList();
        assertTrue(filteredList.contains("Sarika"));
        assertFalse(filteredList.contains("Kushal"));
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
        Predicate<String> startWithA = (str) -> str.startsWith("a");
        List<String> people = Arrays.asList("Al", "Ankit", "Brent", "Sarika", "amanda", "Hans", "Shivika", "Sarah");
        List<String> filteredPeople = people.stream()
                .map(String::toLowerCase)
                .filter(startWithA)
                .toList();
        assertTrue(filteredPeople.contains("al"));
        assertFalse(filteredPeople.contains("brent"));
    }

    @Test
    void testStreamRowsFromTextFile() throws IOException {
        Predicate<String> isLongerThan13 = (str) -> str.length() > 13;
        Stream<String> bands = Files.lines(Paths.get("src/test/resources/bands.txt"));
        List<String> bands13 = bands
                .sorted()
                .filter(isLongerThan13)
                .toList();
        assertEquals(true, bands13.contains("Mumford and Sons"));
        assertEquals(false, bands13.contains("Elvis"));
    }

    // Stream rows from text file and save to List
    @Test
    void testStreamRowsToList() throws IOException {
        Predicate<String> containsJIT = (str) -> str.contains("jit");
        List<String> bands = Files.lines(Paths.get("src/test/resources/bands.txt"))
                .filter(containsJIT)
                .toList();
        assertEquals(1, bands.size());
    }

    // Stream rows from CSV file and count
    @Test
    void testStreamRowsFromCSVFilesCount() throws IOException{
        Predicate<String[]> isGatherThan13 = (x) -> x.length == 3;
        Stream<String> rows = Files.lines(Paths.get("src/test/resources/data.txt"));
        int rowCount = (int) rows
                .map(x -> x.split(","))
                .filter(isGatherThan13)
                .count();
        assertEquals(5, rowCount);
    }

    // Stream rows from CSV file, parse data from rows
    @Test
    void testStreamRowsFromCSVParseData() throws IOException {
        Predicate<String[]> isEqualsTo3 = (x) -> x.length == 3;
        Stream<String> rows = Files.lines(Paths.get("src/test/resources/data.txt"));
        List<String> result = rows.map(x -> x.split(","))
                .filter(isEqualsTo3)
                .map(x -> String.format("%s %s %s", x[0], x[1], x[2]))
                .toList();
        rows.close();
        assertEquals("A 12 3.7", result.stream().findFirst().orElse("NA"));
    }


    // Stream rows from CSV file store fields in HashMap
    @Test
    void testStreamRowCSVFileToHashMap() throws IOException {
        Predicate<String[]> isEqualsTo3 = (x) -> x.length == 3;
        Predicate<String[]> isGatherThan15 = (x) -> Integer.parseInt(x[1]) > 15;
        Stream<String> rows = Files.lines(Paths.get("src/test/resources/data.txt"));
        Map<String, Integer> map = new HashMap<>();
        map = rows
                .map(x -> x.split(","))
                .filter(isEqualsTo3)
                .filter(isGatherThan15)
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
        BinaryOperator<Double> getSum = Double::sum;
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        double total = Stream.of(7.3, 1.5, 4.8)
                .reduce(0.0, getSum);
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

    @Test
    void testCollectorsGroupingBy(){
        String[] names = {"one", "two", "three", "four", "five", "six"};
        ArrayList<String> namesList = new ArrayList<>(Arrays.asList(names));

        Map<Integer, List<String>> mapNamesByLength = namesList
                .stream()
                .collect(Collectors.groupingBy(String::length));

        assertNull(mapNamesByLength.get(1));
        assertNull(mapNamesByLength.get(2));
        assertEquals(3, mapNamesByLength.get(3).size());
        assertEquals(2, mapNamesByLength.get(4).size());
        assertEquals(1, mapNamesByLength.get(5).size());
    }

    @Test
    void testCollectorsPartitioningBy(){
        Integer[] numbers = {1, 2, 3, 4, 5, 6, 7};
        ArrayList<Integer> numbersList = new ArrayList<>(Arrays.asList(numbers));

        Predicate<Integer> numberIsEven = (num) -> num % 2 == 0;

        Map<Boolean, List<Integer>> result = numbersList
                .stream()
                .collect(Collectors.partitioningBy(numberIsEven));
        assertEquals(3 , result.get(true).size());
        assertEquals(4 , result.get(false).size());
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
    void testCombiningTwoResultList(){
        List<Employee> employees = new ArrayList<>(Arrays.asList(employeesArr));

        Predicate<Employee> isDeveloper = (emp) -> emp.jobTitle() == "developer";
        Function<Employee, Float> getSalary = (emp) -> emp.salary();
        BinaryOperator<Float> sumSalaries = Float::sum;


        Float totalDevelopersSalary = employees
                .stream()
                .filter(isDeveloper)
                .map(getSalary)
                .reduce(0F, sumSalaries);

        Long numberOfDevelopers = employees
                .stream()
                .filter(isDeveloper)
                .count();

        Float averageDeveloperSalaries =  totalDevelopersSalary / numberOfDevelopers;
        assertEquals(83333.336f, averageDeveloperSalaries);
    }

    @Test
    void testCombiningMultiplesResults(){
        List<Employee> employees = new ArrayList<>(Arrays.asList(employeesArr));

        Function<Employee, Float> getSalary = (emp) -> emp.salary();
        BinaryOperator<Float> sumSalaries = Float::sum;

        Map<String, Float> groupedByJobTitle = employees
                .parallelStream()
                .collect(Collectors.groupingBy((emp) -> emp.jobTitle()))
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        (grouped) -> {
                            Float sumSalary =  grouped
                                    .getValue()
                                    .parallelStream()
                                    .map(getSalary)
                                    .reduce(0F, sumSalaries);
                            Integer total = grouped.getValue().size();
                            return sumSalary / total;
                }));

        assertEquals(45600.0F, groupedByJobTitle.get("construction worker"));
        assertEquals(83333.336F, groupedByJobTitle.get("developer"));
        assertEquals(50000.0F, groupedByJobTitle.get("writer"));
        assertEquals(100000.0F, groupedByJobTitle.get("sales executive"));
    }

    @Test
    void testParallelStreamsFilter(){
        String[] wordsArray = {"hello", "functional", "world", "is", "cool"};
        ArrayList<String> words = new ArrayList<>(Arrays.asList(wordsArray));

        Predicate<String> isLongerThan5 = (word) -> {
            System.out.println(String.format("Filtering word: %s", word));
            // The list of words will be printed non-sequentially
            return word.length() > 2;
        };

        Function<String, String> toUpperCase = (word) -> {
            System.out.println(String.format("toUpperCase word: %s", word));
            // The list of words will be printed non-sequentially
            return word.toUpperCase();
        };

        Function<String, String> addExclamationPoint = (word) -> {
            System.out.println(String.format("adding ! word: %s", word));
            // The list of words will be printed non-sequentially
            return String.format("%s!", word);
        };

        // IMPORTANT: At the end the original order will be preserved.
        List<String> filteredWords = words
                .parallelStream()
                .filter(isLongerThan5)
                .map(toUpperCase)
                .map(addExclamationPoint)
                .toList();

        assertEquals(4, filteredWords.size());

        /*
        For example, it could generate something like this:
            Filtering word: hello
            toUpperCase word: hello
            adding ! word: HELLO
            Filtering word: functional
            Filtering word: cool
            Filtering word: is
            Filtering word: world
            toUpperCase word: cool
            toUpperCase word: functional
            adding ! word: COOL
            toUpperCase word: world
            adding ! word: FUNCTIONAL
            adding ! word: WORLD
         */
    }
}
