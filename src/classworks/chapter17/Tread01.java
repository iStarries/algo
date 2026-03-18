package classworks.chapter17;

public class Tread01 {
    public static void main(String[] args) {
        Thread thread1 = new Thread(new T1());
        Thread thread2 = new Thread(new T2());
        thread1.start();
        thread2.start();

    }

}

class T1 implements Runnable{
    @Override
    public void run() {
        int times = 10;
        while (times > 0){
            System.out.println("这是T1T1T1T1T1" + times--);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class T2 implements Runnable{
    @Override
    public void run() {
        int times = 5;
        while (times > 0){
            System.out.println("这是T2T2T2T2T2" + times--);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
