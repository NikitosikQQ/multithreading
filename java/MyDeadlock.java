public class MyDeadlock {
    public static final Object lock1 = new Object();
    public static final Object lock2 = new Object();

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
            try {
                ThreadTwo.sleep(15);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Тред1 - пытается захватить лок2");
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
            try {
                ThreadTwo.sleep(15);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Тред2 - пытается захватить лок1");

            synchronized (MyDeadlock.lock1) {
                System.out.println("Тред2 - захвачен лок1 и лок2");
            }
        }
    }
}