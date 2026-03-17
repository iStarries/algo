package myexxplore;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String s1 = bufferedReader.readLine();
        String s2 = bufferedReader.readLine();
        div(s1, s2);
    }
    public static void div(String s1, String s2) {
        int[] ints1 = new int[s1.length()];
        int int2 = Integer.parseInt(s2);
        int[] res = new int[s1.length()];

        for (int i = 0, j = 0; i < s1.length(); i++) {
            ints1[i] = s1.charAt(i) - '0';
        }
        int sum = 0;
        for (int i = 0; i < ints1.length; i++) {
            sum = sum * 10 + ints1[i];
            if (sum >= int2) {
                res[i] = sum / int2;
                sum %= int2;
            } else {
                res[i] = 0;
            }
        }
        int start;
        for (start = 0; start < res.length - 1; start++) {
            if (res[start] != 0) break;
        }
        while (start < res.length) System.out.print(res[start++]);
        System.out.println("\n" + sum);
    }
}

