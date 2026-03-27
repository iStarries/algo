package myexxplore;

import java.io.*;

class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));
        String[] input = bufferedReader.readLine().split(" ");
        int n = Integer.parseInt(input[0]);
        int m = Integer.parseInt(input[1]);
        input = bufferedReader.readLine().split(" ");
        int[] arr = new int[n + 10];
        //下标全部从一开始，因为在树里面要找左右子节点，用2*i 和 2*i+1 会很方便
        for (int i = 1; i <= n; i++) arr[i] = Integer.parseInt(input[i - 1]);
        //第1 - n/2个 是非叶子节点，第n/2+1 - n个 是叶子节点
        //只要从后往前依次对非叶子节点进行下坠就能形成堆
        for (int i = n / 2; i > 0; i--) down(arr, i, n);

        while (m-- > 0) {
            //获取、输出对顶
            //去最后一个节点放到对顶，下坠调整排序
            bufferedWriter.write(arr[1] + " ");
            arr[1] = arr[n];
            n--;
            down(arr, 1, n);
        }

        bufferedReader.close();
        bufferedWriter.close();
    }
    public static void down(int[] arr, int i, int n) {
        int j = i;
        //判断左孩子存不存在，取最小值的索引
        //判断右孩子存不存在，取最小值的索引
        //把取出来的最小值的索引和原本传进来的索引进行值交换
        if(i * 2 <= n && arr[i * 2] < arr[j]) j = i * 2;
        if(i * 2 + 1 <= n && arr[i * 2 + 1] < arr[j]) j = i * 2 + 1;
        //现在j对应的值就是三者里面的最小值

        if (i != j){
            int temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
            down(arr, j, n);
        }
    }
}