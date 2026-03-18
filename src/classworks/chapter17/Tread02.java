package classworks.chapter17;

public class Tread02 {
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new T3());

        int count = 1;
        while (true){
            if (count == 10) break;
            if (count == 6) {
                thread.start();

                thread.join();
            }
            System.out.println("hi,第" + count++);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class T3 implements Runnable{
    @Override
    public void run() {
        int count = 1;
        while (true){
            if (count == 10) break;
            System.out.println("hello,第" + count++);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

