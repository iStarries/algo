import java.io.*;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;

class Main {

    public static String[] input;

    public static int N = 100010;
    public static int[] distancce = new int[N];
    public static ArrayList<Integer>[] g = new ArrayList[N];
    /*
    用bfs按层搜索
    用二维动态数字保存节点
    distancce数组保存每个点到起点的距离
    bfs用队列保存遍历到的每一层节点
    每次弹出对头检查是不是目标节点，如果是就返回步数，否则继续扩充队列
    队列如果检查空了说明结果是-1
     */
//    static class Pair{
//        int x;
//        int dist;
//        Pair(int x, int dist){
//            this.x = x;
//            this.dist = dist;
//        }
//    }
    public static void main(String[] args) throws IOException {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));

        // 读取节点数
        input = bufferedReader.readLine().split("\\s+");
        int n = Integer.parseInt(input[0]);
        int m = Integer.parseInt(input[1]);

        for (int i = 1; i <= n; i++) {
            g[i] = new ArrayList<>();
        }

        while (m-- > 0){
            input = bufferedReader.readLine().split("\\s+");
            int a = Integer.parseInt(input[0]);
            int b = Integer.parseInt(input[1]);
            g[a].add(b);
        }

        //bfs保存每一层的点
        Queue<Integer> p = new ArrayDeque<>();
        distancce[1] = 1;
        p.offer(1);
        while (!p.isEmpty()){
            int cur = p.poll();
            if(cur == n) {
                bufferedWriter.write(String.valueOf(distancce[cur] - 1));
                bufferedWriter.close();
                return;
            }
            int dist = distancce[cur];

            for (Integer i : g[cur]) {
                if(distancce[i] == 0){
                    p.offer(i);
                    distancce[i] = 1 + dist;
                }
            }
        }
        bufferedWriter.write(String.valueOf(-1));
        bufferedWriter.close();
    }


}