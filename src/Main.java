import java.io.*;

class Main {

    // ===================== 全局变量 =====================

    // 输入临时数组
    public static String[] input;

    // 最大节点数
    public static int N = 100010;

    // 邻接表当前边的下标（从1开始，0表示空）
    public static int idx = 1;

    // 最终答案：所有点中“删除后最大连通块”的最小值
    public static int res = N;

    // 邻接表头节点：h[a] 表示从 a 出发的第一条边的编号
    public static int[] h = new int[N];

    // 访问标记数组：防止走回头路（父节点）
    public static boolean[] vis = new boolean[N];

    // e[idx] 表示第 idx 条边指向的点
    public static int[] e = new int[N * 2];

    // ne[idx] 表示与当前边同起点的下一条边
    public static int[] ne = new int[N * 2];


    public static void main(String[] args) throws IOException {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));

        // 读取节点数
        input = bufferedReader.readLine().split("\\s+");
        int n = Integer.parseInt(input[0]);

        // 读入 n-1 条边（树的性质）
        for (int i = 1; i < n; i++) {
            input = bufferedReader.readLine().split("\\s+");
            int a = Integer.parseInt(input[0]);
            int b = Integer.parseInt(input[1]);

            // 无向边，需要建两次
            add(a, b);
            add(b, a);
        }

        // 从任意点开始DFS（这里选1）
        dfs(1, n);

        // 输出答案
        bufferedWriter.write(String.valueOf(res));

        bufferedReader.close();
        bufferedWriter.close();
    }


    /**
     * DFS函数：返回以当前节点 i 为根的子树大小
     *
     * 同时在过程中计算：
     * 删除当前节点 i 后，最大连通块的大小
     */
    private static int dfs(int i, int n) {

        // 标记当前点已访问（防止走回父节点）。不需要回溯！
        vis[i] = true;

        // size：记录删除 i 后，所有连通块中的“最大值”
        // sum：所有子树节点数之和
        int size = 0, sum = 0;

        // 遍历 i 的所有邻接点
        for (int j = h[i]; j != 0; j = ne[j]) {

            int p = e[j];  // 当前邻接点

            // 如果已经访问过，说明是父节点，跳过
            if (!vis[p]) {

                // 递归计算子树大小
                int s = dfs(p, n);

                // 累加所有子树大小
                sum += s;

                // 更新：子树中最大的那一块
                size = Math.max(size, s);
            }
        }

        // ================= 核心关键 =================
        // 除了子树，还要考虑“父方向”的那一块
        // 大小 = n - sum - 1
        size = Math.max(size, n - sum - 1);

        // 更新答案：找所有点中最小的“最大连通块”
        res = Math.min(res, size);

        // 返回当前子树大小（包含自己）
        return sum + 1;
    }


    /**
     * 邻接表加边（头插法）
     */
    private static void add(int a, int b) {

        // 当前边指向 b
        e[idx] = b;

        // 当前边的下一条边 = 原来 a 的第一条边
        ne[idx] = h[a];

        // 更新 a 的第一条边为当前边
        h[a] = idx;

        idx++;
    }
}