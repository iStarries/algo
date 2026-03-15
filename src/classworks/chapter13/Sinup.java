package classworks.chapter13;

import java.util.Scanner;

public class Sinup {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String name=null;
        String mail=null;
        String keyString=null;
        int key = 0;
        try {
            name = scanner.next();

            if (!(name.length() >= 2 && name.length() <= 4)) {
                throw new RuntimeException("名字不对");
            }
            mail = scanner.next();
            int ind1 = mail.lastIndexOf('@');
            int ind2 = mail.lastIndexOf('.');
            if (!(ind1 > 0 && ind2 > 0 && ind1 < ind2)) {
                throw new RuntimeException("邮箱不对");
            }
            keyString = scanner.next();
            key = Integer.parseInt(keyString);
            if (!(keyString.length() == 6)) {
                throw new RuntimeException("密码不对");
            }

        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        Account account = new Account(name, key, mail);
        System.out.println(account);
    }
}

class Account {
    private String name = null;
    private String mail = null;
    private int key = 0;

    public Account(String name, int key, String mail) {
        this.name = name;
        this.mail = mail;
        this.key = key;
    }

    @Override
    public String toString() {
        return "Account{" +
                "name='" + name + '\'' +
                ", mail='" + mail + '\'' +
                ", key=" + key +
                '}';
    }
}
