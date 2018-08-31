package resources;

import java.io.File;
import java.io.IOException;

public class Book extends File {

    private static String FILE_NAME = "book.txt";
    private static volatile Book book = null;

    private Book() {
        super(FILE_NAME);
        try {
            createNewFile();
        } catch (IOException e) {
            System.out.println("Ошибка: файл не создан!");
        }
    }

    public static Book getBook() {
        if (book == null) {
            book = new Book();
        }
        return book;
    }
}
