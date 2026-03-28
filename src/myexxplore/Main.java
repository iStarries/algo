package myexxplore;

import java.io.*;

class Main {
    public static int N = 100003;
    /*
    数组模拟单链表所使用的三个数组
    head存所有的头节点，这里也是哈希表的所有桶
    val存所有节点的值
    next存所有节点的下一个节点
     */
    public static int[] head = new int[N];
    public static int[] val = new int[N];
    public static int[] next = new int[N];
    public static int cur = 1;
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));
        String[] input = bufferedReader.readLine().split(" ");
        int n = Integer.parseInt(input[0]);

        while (n-- > 0) {
            input = bufferedReader.readLine().split(" ");
            char ch = input[0].charAt(0);
            int x = Integer.parseInt(input[1]);
            if (ch == 'Q') {
                if (query(x)) System.out.println("Yes");
                else System.out.println("No");
            }
            else insert(x);
        }


        bufferedReader.close();
        bufferedWriter.close();
    }

    public static boolean query(int x) {
        //这里的哈希函数避免了负数k
        int k = (x % N + N) % N;
        if (head[k] == 0){
            return false;
        }
        k = head[k];
        while (k != 0) {
            if (val[k] == x) {
                return true;
            }
            k = next[k];
        }
        return false;
    }

    public static void insert(int x) {
        if (query(x)) return;
        int k = (x % N + N) % N;
        val[cur] = x;
        next[cur] = head[k];
        head[k] = cur;
        cur++;
    }

}