import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.Semaphore;

public class Library {

    static Random rnd = new Random(); //  declarations
    static Semaphore semaphore;

    static void visitLibrary(int person) throws InterruptedException {
        if (semaphore.availablePermits() == 0)
            System.out.println(person + "-й человек ждет входа в библиотеку");

        semaphore.acquire();
        System.out.println(person + "-й человек вошел в библиотеку");

        int time = rnd.nextInt(4000) + 1000;
        System.out.println(person + "-й человек читает книгу ");
        Thread.sleep(time);

        semaphore.release();
        System.out.println(person + "-й человек вышел из библиотеки");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите количество людей: ");
        int peopleCount = scanner.nextInt();
        System.out.println("Введите вместимость библиотеки: ");
        int maxAmount = scanner.nextInt();
        semaphore = new Semaphore(maxAmount);

        for(int i = 0; i < peopleCount; i++) {
            final int PERSON = i + 1;
            new Thread(() -> {
                try {
                    visitLibrary(PERSON);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
            System.out.println(i+1 + "-й человек" + " пришел ко входу в библиотеку.");
        }
    }
}
