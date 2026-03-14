package classworks.chapter8;

public class AccountTest {
    public static void main(String[] args) {
        Account account = new Account("zhang", new int[]{1}, 123);
        account.show();
        Account account1 = new Account("zha", new int[]{1,1,1,1,1,1}, 20);
        account1.show();

    }
}
