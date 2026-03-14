package classworks.chapter10;

public class TestPhone {
    public static void main(String[] args) {
        new CellPhone().alarmClock(new Bell() {
            public void ring() {
                System.out.println("这是手机");
            }
        });
    }
}

class CellPhone{
    public void alarmClock(Bell bell){
        bell.ring();
    }
}

interface Bell{
    void ring();
}
