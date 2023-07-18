public class DoubleCheckSingleton {
    public static void main(String[] args) {
        Singleton singleton = Singleton.getInstance();
    }

    static class Singleton {
        private volatile static Singleton singleton;

        private Singleton() {
        }

        public static Singleton getInstance() {
            if (singleton == null) {
                synchronized (Singleton.class) {
                    if (singleton == null) {
                        singleton = new Singleton();
                    }
                }
            }
            return singleton;
        }
    }
}
