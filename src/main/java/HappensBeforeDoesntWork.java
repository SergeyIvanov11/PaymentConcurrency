package main.java;

public class HappensBeforeDoesntWork {
    private boolean flag = false;
    private int a = 0;
    private volatile boolean stop = false;

    public void checkHappensBefore(long iterations) throws InterruptedException {
        for (long i = 0; i < iterations; i++) {
            a = 0;
            flag = false;

            Thread writer = new Thread(() -> {
                a = 1;
                flag = true;
            });

            long finalI = i;
            Thread reader = new Thread(() -> {
                while (!flag) {
                    Thread.yield();
                    if (stop) return;
                }

                if (a == 0) {
                    System.out.println("Happens-before не работает в случае " + finalI);
                    stop = true;
                }
            });

            writer.start();
            reader.start();

            writer.join();
            reader.join();

            if (stop) break;
        }

        if (!stop) {
            System.out.println("Happens-before работал всегда");
        }
    }
}
