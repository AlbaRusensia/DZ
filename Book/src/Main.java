import actors.Reader;
import actors.Writer;

import java.util.ArrayList;
import java.util.List;

public class Main {

    static int counter;
    private static int NUMBER_OF_WRITERS = 2;
    private static int NUMBER_OF_READERS = 3;
    private static List<Thread> threads;

    public static void main(String[] args) {

        threads = new ArrayList<>();

        createWriters();
        createReaders();
        start();
    }

    private static void start() {
        for (Thread thread : threads) {
            thread.start();
        }
    }

    private static void createReaders() {
        counter = 0;
        while (counter < NUMBER_OF_READERS) {
            threads.add(new Thread(new Reader("Читатель #" + (counter + 1))));
            counter++;
        }
    }

    private static void createWriters() {
        counter = 0;
        while (counter < NUMBER_OF_WRITERS) {
            threads.add(new Thread(new Writer("Писатель #" + (counter + 1))));
            counter++;
        }
    }
}
