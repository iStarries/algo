import java.io.*;
import java.util.ArrayDeque;
import java.util.Queue;

class Main {
    /*
    定义val二维数组存放迷宫
    定义dist二维数组，记录某个点到起始点的距离,如果不为零就是被访问过了
    定义队列存放下一层bfs搜索的位置坐标

    从（1，1）开始bfs：
    while：只要队列不空就取出对头坐标
    如果（x，y）是（n，m）就输出步数，结束程序
    for循环遍历四个方向，寻找可以走的位置
    在可以走的位置visit置一，dist++，队列记录新位置
    没找到可以走到位置就continue，直到for循环结束


    */
    public static int N = 110;
    public static String[] input;
    public static int[][] dist = new int[N][N];
    public static int[][] val = new int[N][N];
    public static Queue<Pair> q = new ArrayDeque<>();

    public static int[] xs = {1, 0, -1, 0};
    public static int[] ys = {0, 1, 0, -1};

    public static class Pair{
        int x, y;
        Pair(int x, int y){
            this.x = x;
            this.y = y;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));
        input = bufferedReader.readLine().split(" ");
        int n = Integer.parseInt(input[0]);
        int m = Integer.parseInt(input[1]);
        for (int i = 1; i <= n; i++) {
            input = bufferedReader.readLine().split(" ");
            for (int j = 1; j <= m; j++) {
                val[i][j] = Integer.parseInt(input[j - 1]);
            }
        }
        q.offer(new Pair(1, 1));
        dist[1][1] = 1;
        while (!q.isEmpty()) {
            Pair pair = q.poll();
            if (pair.x == n && pair.y == m) {
                bufferedWriter.write(String.valueOf(dist[pair.x][pair.y] - 1));
                break;
            }
            for (int i = 0; i < 4; i++) {
                int x = pair.x + xs[i];
                int y = pair.y + ys[i];
                if (x >= 1 && x <= n && y >= 1 && y <= m && dist[x][y] == 0 && val[x][y] == 0) {
                    dist[x][y] = dist[pair.x][pair.y] + 1;
                    q.add(new Pair(x, y));
                }
            }
        }
        bufferedReader.close();
        bufferedWriter.close();
    }


}