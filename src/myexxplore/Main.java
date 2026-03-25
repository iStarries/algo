package myexxplore;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class Main {
    public static void main(String[] args) throws IOException {
        // 创建缓冲输入对象，用来从控制台读取数据
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String[] s = bufferedReader.readLine().split(" ");

        int n = Integer.parseInt(s[0]);
        int m = Integer.parseInt(s[1]);
        /*
        数组的下标就对应了 n 个数，每个数组元素最初都是-1，表示没有父节点。
        每次合并两个数，就把一个数的父节点设置为另一个数的索引。
        每次查询就找两个数的父节点是不是同一个，判断是否属于同一个集合。
         */
        int[] arr = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            //每个节点的根节点就是它自己
            arr[i] = i;
        }
        while (m-- > 0){
            s = bufferedReader.readLine().split(" ");
            char c = s[0].charAt(0);
            int a = Integer.parseInt(s[1]);
            int b = Integer.parseInt(s[2]);
            if(c == 'M'){
                //合并之前必须先找根节点，不然一个点会出现多个父节点
                a = find(arr, a);
                b = find(arr, b);
                //必须先判断两个根节点a和b不一样（不在同一个集合里）才能修改根节点，
                //不然自己变成了自己的父节点
                if (a != b) arr[a] = b;
            }else{
                a = find(arr, a);
                b = find(arr, b);
                if(a == b) System.out.println("Yes");
                else System.out.println("No");
            }
        }
    }
    public static int find(int[] arr, int x){
        //这里实现了递归路径压缩，递归结束后这颗树的每个节点都直接指向根节点
        if(arr[x] != x) arr[x] = find(arr, arr[x]);
        return arr[x];
    }
}