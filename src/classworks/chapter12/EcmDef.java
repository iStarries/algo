package classworks.chapter12;

import java.util.Scanner;

public class EcmDef {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String sin;
        int n1 = 0;
        sin = scanner.next();
        if(sin == null){
            throw new RuntimeException("输入为空");
        }
        try {
            n1 = Integer.parseInt(sin);
        } catch (NumberFormatException e) {
            String message = new RuntimeException("第一个整数不对").getMessage();
            System.out.println(message);
        }


        int n2 = 0;
        sin = scanner.next();
        if(sin == null){
            throw new RuntimeException("输入为空");
        }
        try {
            n2 = Integer.parseInt(sin);
        } catch (NumberFormatException e) {
            String message = new RuntimeException("第二个整数不对").getMessage();
            System.out.println(message);
        }


        try {
            System.out.println(cal(n1, n2));;
        } catch (Exception e) {
            String message = new RuntimeException("除数为零").getMessage();
            System.out.println(message);
        }
    }
    public static double cal(int a, int b) throws Exception{
        return a / b;
    }
}
