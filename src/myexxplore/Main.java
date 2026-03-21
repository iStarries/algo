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
        int c = Integer.parseInt(s1[2]);
        int[] arr1 = new int[n];
        int[] arr2 = new int[m];
        String[] s2 = bufferedReader.readLine().split(" ");
        String[] s3 = bufferedReader.readLine().split(" ");
        for (int i = 0; i < n; i++) {
            arr1[i] = Integer.parseInt(s2[i]);
        }
        for (int i = 0; i < m; i++) {
            arr2[i] = Integer.parseInt(s3[i]);
        }

        int i = 0, j = m - 1;
        while (true){
            while (arr1[i] + arr2[j] > c) j--;
            while (arr1[i] + arr2[j] < c) i++;
            if(arr1[i] + arr2[j] == c) {
                System.out.println(i + " " + j);
                break;
            }
        }


    }
}

