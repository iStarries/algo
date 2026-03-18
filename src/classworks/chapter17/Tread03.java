package classworks.chapter17;

public class Tread03 {
    public static void main(String[] args) {
        Bank bank = new Bank();

        Thread thread1 = new Thread(bank);
        Thread thread2 = new Thread(bank);
        thread1.start();
        thread2.start();
    }
}

class Bank implements Runnable {
    private static int sal = 3000;

    @Override
    public void run() {
        while (true) {
            synchronized (Bank.class){
                if (sal >= 1000) {
                    System.out.println(Thread.currentThread().getName() + "yes");
                    sal -= 1000;
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    continue;
                }
                System.out.println("none");
                break;
            }
        }

    }
}
