package myexxplore;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
class Main {
    public static void main(String[] args) throws IOException {
        /*
        输入m  n
        输入x r,换算x到索引上，做加法

        while n：输入l  r 进行查询输出
         */
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String[] s1 = bufferedReader.readLine().split(" ");
        int n = Integer.parseInt(s1[0]);
        int[] arr = new int[n];
        int[] res = new int[n];
        int m = Integer.parseInt(s1[1]);

        String[] s2;
        while (n > 0){
            s2 = bufferedReader.readLine().split(" ");
            int x = Integer.parseInt(s2[0]);
            int c = Integer.parseInt(s2[1]);
            arr[x] += c;
            n--;
        }
        for (int i = 0; i < n; i++) {
            res[i]
        }
        while (m > 0){
            s2 = bufferedReader.readLine().split(" ");
            int l = Integer.parseInt(s2[0]);
            int r = Integer.parseInt(s2[1]);
            m--;
        }

    }
}

