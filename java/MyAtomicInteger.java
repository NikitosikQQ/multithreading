import java.util.concurrent.atomic.AtomicInteger;

public class MyAtomicInteger {
    static int counter = 0;

    public static void main(String[] args) throws InterruptedException {
        Thread thread1 = new Thread(new AtomicIncrementRunnable());
        Thread thread2 = new Thread(new AtomicIncrementRunnable());
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        System.out.println(counter);
    }

    public synchronized static void increment() {
        counter++;
    }

}

class AtomicIncrementRunnable implements Runnable {

    @Override
    public void run() {
        for (int i = 0; i < 10000; i++) {
            MyAtomicInteger.increment();
        }
    }
}
