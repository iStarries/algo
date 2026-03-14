package classworks.chapter11;

public class CellPhone {
    public void testWork(Phone phone,int a, int b) {
        phone.work(a, b);
    }
}

interface Phone {
    void work(int a, int b);
}

class TestCellPhone {
    public static void main(String[] args) {
        new CellPhone().testWork(new Phone() {
            @Override
            public void work(int a, int b) {
                System.out.println(a + b);
            }
        }, 1, 2);
    }
}