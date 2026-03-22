package myexxplore;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class Main {
    /*
    输入n m
    输出序列a 和 b
    双指针遍历：
        从两个序列的开头开始，长序列中找当前子序列元素，
            找到了两个指针都后移
            没找到就长序列指针后移
            找到子序列的结尾就成功，输出找到了
            一直都没成功，就循环到长序列遍历结束，输出没找到

     */
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String[] s1 = bufferedReader.readLine().split(" ");
        int n = Integer.parseInt(s1[0]);
        int m = Integer.parseInt(s1[1]);
        int[] a = new int[n];
        int[] b = new int[m];

        s1 = bufferedReader.readLine().split(" ");
        for (int i = 0; i < n; i++) {
            a[i] = Integer.parseInt(s1[i]);
        }
        s1 = bufferedReader.readLine().split(" ");
        for (int i = 0; i < m; i++) {
            b[i] = Integer.parseInt(s1[i]);
        }
        int l = 0, r = 0;
        for (; r < m; r++){
            if(a[l] == b[r]){
                l++;
                if(l == n){
                    System.out.println("Yes");
                    return;
                }
            }
        }
        System.out.println("No");
    }
}

