package myexxplore;

import java.io.*;

class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));
        String[] input = bufferedReader.readLine().split(" ");
        int n = Integer.parseInt(input[0]);
        int[] arr = new int[n];
        input = bufferedReader.readLine().split(" ");
        for (int i = 0; i < n; i++) arr[i] = Integer.parseInt(input[i]);
        //trie二维数组的第一维是31 * n，因为每个数字都可能建立一个不一样的二进制路径
        int[][] bnums = new int[31 * n + 10][2];
        //idx=0 的时候是根节点
        int idx = 1;
        int t = 0;
        for (int i : arr) {
            int cur = 0;
            for (int j = 30; j >= 0 ; j--) {
//                t = i >> j;
                //一定要&1，才能取出最后一位
                t = (i >> j) & 1;
                if (bnums[cur][t] == 0) {
                    bnums[cur][t] = idx;
                    cur = idx++;
                }
                else {
                    cur = bnums[cur][t];
                }
            }
        }
        int res = 0;
        for (int i : arr) {
            int cur = 0;
            //total实时更新异或结果，最后的total就是一个异或值
            int total = 0;
            for (int j = 30; j >= 0 ; j--) {
                t = (i >> j) & 1;
                int want = t ^ 1;   // 想走相反位
                if (bnums[cur][want] != 0) {
                    total = total * 2 + 1;
                    cur = bnums[cur][want];
                }else{
                    total *= 2;
                    cur = bnums[cur][t];
                }
            }
            res = Math.max(res, total);

        }
        bufferedWriter.write("" + res);

        bufferedReader.close();
        bufferedWriter.close();
    }
}