import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class MyDeadlock {
    public static final Object lock1 = new Object();
    public static final Object lock2 = new Object();
    public static final CyclicBarrier cyclicBarrier = new CyclicBarrier(2);

    public static void main(String[] args) throws InterruptedException {
        ThreadOne threadOne = new ThreadOne();
        ThreadTwo threadTwo = new ThreadTwo();
        threadOne.start();
        threadTwo.start();
        threadOne.join();
        threadTwo.join();

    }
}

class ThreadOne extends Thread {
    @Override
    public void run() {
        synchronized (MyDeadlock.lock1) {
            System.out.println("Тред1 - захвачен лок1");
            System.out.println("Тред1 - пытается захватить лок2");
            try {
                MyDeadlock.cyclicBarrier.await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (BrokenBarrierException e) {
                throw new RuntimeException(e);
            }
            synchronized (MyDeadlock.lock2) {
                System.out.println("Тред1 - захвачен лок2 и лок1");
            }
        }
    }
}

class ThreadTwo extends Thread {
    @Override
    public void run() {
        synchronized (MyDeadlock.lock2) {
            System.out.println("Тред2 - захвачен лок2");
            System.out.println("Тред2 - пытается захватить лок1");
            try {
                MyDeadlock.cyclicBarrier.await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (BrokenBarrierException e) {
                throw new RuntimeException(e);
            }
            synchronized (MyDeadlock.lock1) {
                System.out.println("Тред2 - захвачен лок1 и лок2");
            }
        }
    }
}