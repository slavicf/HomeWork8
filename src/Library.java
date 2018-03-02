import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.Semaphore;

public class Library {

    static Random rnd = new Random(); //  declarations
    static int peopleCount;
    static int maxAmount;
    static Semaphore semaphore = new Semaphore(maxAmount);

    static void visitLibrary() throws InterruptedException {
//        if (semaphore.availablePermits() == 0)
//            System.out.println(Thread.currentThread().getName() + "   ждет входа в библиотеку");
        semaphore.acquire();
        System.out.println(Thread.currentThread().getName() +"   вошел в библиотеку");

        int time = rnd.nextInt(4000) + 1000;
        System.out.println(Thread.currentThread().getName() +"   читает книгу " + ((float) time / 1000));
        Thread.sleep(time);

        semaphore.release();
        System.out.println(Thread.currentThread().getName() +"   вышел из библиотеки");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("\nВведите количество людей: ");
        peopleCount = scanner.nextInt();
        System.out.print("\nВведите вместимость библиотеки: ");
        maxAmount = scanner.nextInt();

        for(int i = 0; i < peopleCount; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        visitLibrary();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
            System.out.println(i+1 + " человек" + " пришел ко входу в библиотеку.");
        }

    }
}
