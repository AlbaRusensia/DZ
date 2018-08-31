package actors;

import resources.Book;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import static java.lang.Thread.sleep;

public class Reader implements Runnable {

    static boolean ready = true;
    private String name;
    private BufferedReader reader;

    public Reader(String name) {
        this.name = name;
    }

    @Override
    public void run() {

        /*я не знаю, как сделать, чтобы он начал читать с начала*/

//        synchronized (this) {
        while (!ready) {
            try {
                sleep(500);
            } catch (InterruptedException e) {
                System.out.println("Ошбка при чтении фала: Проблемы со сном.");
            }
        }
        while (ready) {
            Book book = Book.getBook();
            try {
                reader = new BufferedReader(new FileReader(book));
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
                reader.close();
                ready = true;
                break;
            } catch (FileNotFoundException e) {
                System.out.println("Ошибка при чтении файла: Файл не найден.");
            } catch (IOException e) {
                System.out.println("Ошибка при чтении файла!");
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        System.out.println("Ошибка при чтении файла: Я не дочиталю");
                    }
                }
            }
        }
//    }
    }
}

