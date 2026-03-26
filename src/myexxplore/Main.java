package myexxplore;

import java.io.*;

class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));
        String[] input = bufferedReader.readLine().split(" ");
        int n = Integer.parseInt(input[0]);
        int m = Integer.parseInt(input[1]);
        /*
        一个队列存下标，队列长度是m
        一个数组存原始输入值，数组长度是n

        初始化数组
        for遍历每一个元素，维护队列
            没遇到一个新的数，考虑把队尾对应的元素删去
            检查对头对应的下标有没有离开窗口，考虑删去
        每次维护好一个新的窗口后，队头对应的值就是最值
        最大最小值分开统计
         */

        int[] arr = new int[n];
        String[] s = bufferedReader.readLine().split(" ");
        for (int i = 0; i < n; i++) arr[i] = Integer.parseInt(s[i]);
        int[] queue = new int[n];
        int hh = 0, tt = -1;
        //队列的len不需要，因为hh = 0, tt = -1 的时候，只要hh <= tt，就一定有元素
        for (int i = 0; i < n; i++) {
            while (hh <= tt && arr[queue[tt]] >= arr[i]) tt--;
            queue[++tt] = i;
//            if (queue[tt] - queue[hh] + 1 > m) {
            //看起来像是在算“窗口长度”，但其实单调队列里的队头和队尾，不代表当前窗口的左右端点。
            //因为队列里存的不是窗口所有元素，只存了“可能成为最值的候选下标”。
            //下面才是对的
            if (i - m + 1 > queue[hh]) hh++;
            if (i >= m - 1) bufferedWriter.write(arr[queue[hh]] + " ");
        }
        bufferedWriter.write("\n");

        hh = 0;
        tt = -1;
        for (int i = 0; i < n; i++) {
            while (hh <= tt && arr[queue[tt]] <= arr[i]) tt--;
            queue[++tt] = i;
            if (i - m + 1 > queue[hh]) hh++;
            if (i >= m - 1) bufferedWriter.write(arr[queue[hh]] + " ");
        }

        bufferedReader.close();
        bufferedWriter.close();
    }

    public static void fund() {
    }
}