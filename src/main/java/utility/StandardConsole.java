package utility;

import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * для ввода и вывода результата
 */

public class StandardConsole implements Console{
    private static final String P = "$ ";
    private static Scanner scanner = new Scanner(System.in);
    private static Scanner tmpScanner = scanner;

    /**
     * выводит obj.toString() в консоль
     * @param obj - объект для печати
     */
    @Override
    public void print(Object obj) {
        System.out.print(obj);
    }

    /**
     * выводит obj.toString() в консоль и переносит на следующую строку
     * @param obj - объект для печати
     */
    @Override
    public void println(Object obj) {
        System.out.println(obj);
    }

    /**
     * выводит ошибку obj.toString в консоль
     * @param obj - ошибка для печати
     */
    @Override
    public void printError(Object obj) {
        System.err.println("Error(err): " + obj);
        System.out.println("Error(out): " + obj);
    }

    @Override
    public String readln() throws NoSuchElementException, IllegalStateException {
        return scanner.nextLine();
    }

    @Override
    public boolean isCanReadln() throws IllegalStateException {
        return scanner.hasNextLine();
    }

    /**
     * выводит таблицу из двух строк
     * @param elementLeft - левый элемент колонки
     * @param elementRight - правый элемент колонки
     */
    @Override
    public void printTable(Object elementLeft, Object elementRight) {
        System.out.printf(" %-35s%-1s%n", elementLeft, elementRight);
    }

    /**
     * выводит приглашение к вводу команд
     */
    @Override
    public void prompt() {
        print(P);
    }

    /**
     * @return приглашение к вводу команд
     */
    @Override
    public String getPrompt() {
        return P;
    }

    @Override
    public void selectFileScanner(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public void selectConsoleScanner() {
        this.scanner = tmpScanner;
    }

}
