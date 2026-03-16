package myexxplore;

import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String s = bufferedReader.readLine();
        int len = s.length();
        char[] s1 = s.toCharArray();
        int[] n1 = new int[100010];
        for (int i = 0; i < len; i++) {
            n1[i] = s1[len - i - 1] - '0';
        }
        s = bufferedReader.readLine();
        len = s.length();
        s1 = s.toCharArray();
        int[] n2 = new int[100010];
        for (int i = 0; i < len; i++) {
            n2[i] = s1[len - i - 1] - '0';
        }
        int[] res = new int[100010];

        int i = 0;
        for (; i < 100009; i++) {
            int t = n1[i] + n2[i];
            res[i] += t % 10;
            res[i + 1] += t / 10;
        }
        int start = 100009;
        for (int j = 100009; j >= 0; j--) {
            if (res[j] == 0){
                start = j;
            }
            else break;
        }
        if (start == 0){
            System.out.println(0);
            return;
        }
        for (int j = start - 1; j >= 0; j--) {
            System.out.print(res[j]);
        }
    }
}
