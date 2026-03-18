package myexxplore;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String[] s1 = bufferedReader.readLine().split(" ");
        int n = Integer.parseInt(s1[0]);
        int m = Integer.parseInt(s1[1]);
        int[] arr = new int[n + 1];
        int[] res = new int[n + 1];
        String[] s2 = bufferedReader.readLine().split(" ");
        for (int i = 1; i <= n; i++) {
            arr[i] = Integer.parseInt(s2[i - 1]);
            res[i] = arr[i] - arr[i - 1];
        }
        String[] s;
        while (m > 0) {
            m--;
            s = bufferedReader.readLine().split(" ");
            int l = Integer.parseInt(s[0]);
            int r = Integer.parseInt(s[1]);
            int c = Integer.parseInt(s[2]);
            res[l] += c;
            if(r < n)res[r + 1] -= c;
        }
        int t = 0;
        for (int i = 1; i < res.length; i++) {
            t += res[i];
            System.out.print(t+" ");
        }
    }
}

