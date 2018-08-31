package actors;

import resources.Book;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

import static java.lang.Thread.sleep;

public class Writer implements Runnable {

    private static Random rnd;
    private BufferedWriter writer;
    private String name;

    public Writer(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        if (rnd == null) {
            rnd = new Random();
        }

//        synchronized (this) {
        Book book = Book.getBook();
        try {
            sleep(rnd.nextInt(2000) + 1000);
        } catch (InterruptedException e) {
            System.out.println("Ошбка при записи в файл: Проблемы со сном.");
        }
        try {
            Reader.ready = false;
            StringBuilder str = new StringBuilder(name);
            int counter = 0;
            while (counter < rnd.nextInt(330) + 3) {
                str.append('a');
                counter++;
            }
            str.append("\n");
            writer = new BufferedWriter(new FileWriter(book, true));
            writer.write(String.valueOf(str));
//                notifyAll();
        } catch (IOException e) {
            System.out.println("Ошибка при записи в файл!");
        } finally {
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Reader.ready = true;

        }
    }
//    }

}
