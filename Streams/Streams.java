package Streams;

import java.io.IOException;
import java.util.stream.IntStream;
public class Streams {

    public static void main(String[] args) throws IOException {

        IntStream.range(1, 10)
                .forEach(System.out::print);

    }
}
