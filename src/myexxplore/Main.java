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
        int[] arr = new int[n];
        StringBuilder stringBuilder = new StringBuilder();
        int hh = -1;
        /*
        hh 是“栈内下标”
        stack[hh] 是“原数组下标”
        a[stack[hh]] 才是值
        这样分开维护可以保留原始数组的值，同时用栈记住每一个筛选出来的元素对应于原数组的下标，
        这样不仅可以应付输出值的题目，还可以应付输出位置、距离的题目

        栈要维护成单调栈（这一题是递增的）
         */
        s = bufferedReader.readLine().split(" ");
        for (int i = 0; i < n; i++) {
            int x = Integer.parseInt(s[i]);
            while (hh >= 0) {
                if (arr[stack[hh]] >= x) {
                    hh--;
                } else {
                    break;
                }
            }
            if (hh == -1) {
                //看栈里面有没有存放下标
                stringBuilder.append("-1 ");
            } else {
                stringBuilder.append(arr[stack[hh]] + " ");
            }
            stack[++hh] = i;
            arr[i] = x;
        }
        System.out.println(stringBuilder.toString());
    }
}

