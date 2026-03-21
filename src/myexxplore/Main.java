package myexxplore;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String[] s1 = bufferedReader.readLine().split(" ");
        int n = Integer.parseInt(s1[0]);
        int[] arr = new int[n];
        int[] res = new int[100001];
        String[] s2 = bufferedReader.readLine().split(" ");
        for (int i = 0; i < n; i++) {
            arr[i] = Integer.parseInt(s2[i]);
        }
        int max = 0;
        int r = 0, l = 0;
        while (r < n){
            //双指针都是只右移
            res[arr[r]]++;
            //检查到重复就记录最大值
            if (res[arr[r]] == 2){
                //一定持续往右检查重复，因为不一定正好l和r重复
                max = Math.max(max, r - l);
                while (res[arr[r]] == 2){
                    res[arr[l]]--;
                    l++;
                }
            }
            r++;
        }
        max = Math.max(max, r - l);
        System.out.println(max);
    }
}

