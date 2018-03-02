import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.Semaphore;

public class Library {

    static Random rnd = new Random(); //  declarations
    static Semaphore semLib;
    static Semaphore semDoor = new Semaphore(1);

    static void libraryDoor(int person, boolean direction) throws InterruptedException {
        int time = 500;

        if (direction) {
            System.out.println(person + "-й человек подошел к двери с улицы");
            semDoor.acquire();
            System.out.println(person + "-й человек проходит через дверь внутрь");
            Thread.sleep(time);
            System.out.println(person + "-й человек прошел через дверь внутрь");
            semDoor.release();
            new Thread(() -> {
                try {
                    visitLibrary(person);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        } else {
            System.out.println(person + "-й человек подошел к двери изнутри");
            semDoor.acquire();
            System.out.println(person + "-й человек проходит через дверь наружу");
            Thread.sleep(time);
            System.out.println(person + "-й человек прошел через дверь наружу");
            semDoor.release();
        }

    }

    static void visitLibrary(int person) throws InterruptedException {
        System.out.println(person + "-й человек" + " пришел ко входу в библиотеку.");
        if (semLib.availablePermits() == 0)
            System.out.println(person + "-й человек ждет входа в библиотеку");

        semLib.acquire();
        System.out.println(person + "-й человек вошел в библиотеку");
        int time = rnd.nextInt(4000) + 1000;
        System.out.println(person + "-й человек читает книгу ");
        Thread.sleep(time);
        System.out.println(person + "-й человек вышел из библиотеки");
        semLib.release();
        new Thread(() -> {
            try {
                libraryDoor(person, false);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите количество людей: ");
        int peopleCount = scanner.nextInt();
        System.out.println("Введите вместимость библиотеки: ");
        int maxAmount = scanner.nextInt();
        semLib = new Semaphore(maxAmount);

        for(int i = 0; i < peopleCount; i++) {
            int person = i + 1;
            new Thread(() -> {
                try {
                    libraryDoor(person, true);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
