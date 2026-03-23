package myexxplore;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String[] s = bufferedReader.readLine().split(" ");
        int n = Integer.parseInt(s[0]);
        int[] stack = new int[n];
        int hh = -1;

        s = bufferedReader.readLine().split(" ");
        for (int i = 0; i < n; i++) {
            int x = Integer.parseInt(s[i]);
            hh = res(stack, hh, x);
        }
    }
    public static int res(int[] stack, int hh, int x) {
        while (hh >= 0) {
            if (stack[hh] >= x) {
                hh--;
            }else{
                break;
            }
        }
        if (hh == -1) {
            System.out.printf("%d ", -1);
            stack[++hh] = x;
            return hh;
        }else{
            System.out.printf("%d ", stack[hh]);
            stack[++hh] = x;
            return hh;
        }

    }
}

