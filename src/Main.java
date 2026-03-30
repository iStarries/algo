import java.io.*;

class Main {
    public static int N = 100003;
    /*
    定义一个 visit 数组，表示数字 1~n 中每个数字是否已经被使用过。
    递归函数表示当前在填写排列的第几个位置。
    在每一层递归中，用 for 循环枚举 1~n，对于每个还没有被访问的数字，都把它放到当前位置，并标记为已访问，然后递归填写下一个位置。
    当所有位置都填满时，就输出当前排列。
    递归返回后，要把刚才选择的数字恢复为未访问，继续尝试下一个数字，直到所有情况遍历完为止。
     */
    public static StringBuilder val = new StringBuilder();
    public static int n;
    public static int idx = 1;
    public static String[] input;
    public static int[] vis = new int[10];

    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));
        input = bufferedReader.readLine().split(" ");
        n = Integer.parseInt(input[0]);

        dfs(idx);

        bufferedReader.close();
        bufferedWriter.close();
    }
    public static void dfs(int idx) {
        // 注意满足结束条件，打印输出
        if (idx == n + 1) {
            System.out.println(val);
            return;
        }
        for (int i = 1; i <= n; i++) {
            if (vis[i] == 0) {
                vis[i] = 1;
                //如果不用数组存即将输出的内容，那么为了在回溯的时候删去刚加入的内容就要用setLength方法
                //因为加入的输出串的不止是数字，还有空格
                int len = val.length();
                val.append(i).append(" ");
                dfs(idx + 1);
                val.setLength(len);
                vis[i] = 0;
            }
        }

    }


}