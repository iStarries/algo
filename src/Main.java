import java.io.*;

class Main {
    /*
        并查集（带权并查集 + 路径压缩）

        p[] 表示父节点，初始时每个点的父节点都是自己。
        dist[] 表示当前节点到父节点的关系值；
        在执行 find() 路径压缩后，dist[x] 会被更新为 x 到根节点的关系值。
        初始时每个点单独成集合，所以 dist[i] = 0。

        对于任意两个节点 x、y，
        (dist[x] - dist[y]) % 3 表示 x 和 y 的相对关系：
        0 表示 x 和 y 同类；
        1 表示 x 吃 y；
        2 表示 x 被 y 吃。

        因此在合并两个集合时，
        不仅要连父节点，还要同时维护根节点之间的关系值，
        使合并后的集合满足题目给出的“同类”或“吃”的条件。
    */
    public static String[] input;
    public static int N;
    public static int K;
    public static int[] p = new int[50010];
    public static int[] dist = new int[50010];

    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));
        input = bufferedReader.readLine().split("\\s+");
        N = Integer.parseInt(input[0]);
        K = Integer.parseInt(input[1]);

        for (int i = 1; i <= N; i++) {
            p[i] = i;
            dist[i] = 0;
        }

        int res = 0;

        while (K-- > 0) {
            input = bufferedReader.readLine().split("\\s+");
            int id = Integer.parseInt(input[0]);
            int x = Integer.parseInt(input[1]);
            int y = Integer.parseInt(input[2]);
            if (x >= 1 && x <= N && y >= 1 && y <= N) {
                int px = find(x);
                int py = find(y);
                if (id == 1){
                    if(px == py){
                        //必须比较 dist[x] 和 dist[y]，不能比较 dist[px] 和 dist[py]
                        if ((dist[x] - dist[y]) % 3 != 0) res++;
                    }else{
                        p[px] = py;
                        dist[px] = dist[y] - dist[x];
                    }
                }else{
                    if (x == y) res++;
                    else if(px == py){
                        if ((dist[x] - dist[y]) % 3 != 1) res++;
                    }else{
                        p[px] = py;
                        //x -> px 的关系是 dist[x]
                        //px -> py 的关系是 dist[px]
                        //y -> py 的关系是 dist[y]
                        //需要满足的关系是 dist[x] + dist[px] - dist[y] = 1
                        dist[px] = dist[y] - dist[x] + 1;
                    }
                }
            }else{
                res++;
            }
        }
        bufferedWriter.write(String.valueOf(res));

        bufferedReader.close();
        bufferedWriter.close();
    }

    private static int find(int x) {
        if (x != p[x]) {
            int t = p[x];
            p[x] = find(p[x]);
            //在递归过程中，dist保存的内容是到根节点的距离（递归到根节点的时候开始从上到下更新dist）
            //每个更新好dist[x]，递归返回上一层，上一层的dist[t]就是现在的dist[x]，已经变成到跟的距离了
            dist[x] = dist[x] + dist[t];
        }
        return p[x];
    }


}