import java.io.*;

class Main {
    /*
    按行放置皇后，从第 1 行开始依次搜索。

    val 用来记录当前棋盘状态。
    col 用来记录某一列是否已经放过皇后。
    vis 用来记录主对角线是否已经放过皇后。
    uvis 用来记录副对角线是否已经放过皇后。

    递归时按行进行搜索：
    如果当前行已经超过 n，说明前 n 行都已经合法放置了皇后，
    此时输出当前棋盘，并结束这一分支的搜索。

    对于当前行，依次枚举这一行的每一列，
    判断当前位置所在的列、主对角线、副对角线是否都没有皇后。
    如果可以放置，就在该位置放上皇后，并标记对应状态，
    然后递归搜索下一行。

    当下一层递归返回后，需要撤销当前位置的皇后和相关标记，
    继续尝试当前行的其他列，这个过程就是回溯。
    */
    public static int N = 10;
    public static String[] input;
    public static int[] vis = new int[N * 2];
    public static int[] col = new int[N * 2];
    public static int[] uvis = new int[N * 2];
    public static char[][] val = new char[N * 2][N * 2];

    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));
        input = bufferedReader.readLine().split(" ");
        int n = Integer.parseInt(input[0]);
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                val[i][j] = '.';
            }
        }

        dfs(1, n, bufferedWriter);

        bufferedReader.close();
        bufferedWriter.close();
    }
    public static void dfs(int i, int n, BufferedWriter bufferedWriter) throws IOException {
        if (i > n){
            for (int j = 1; j <= n; j++) {
                for (int k = 1; k <= n; k++) {
                    bufferedWriter.write(val[j][k]);
                }
                bufferedWriter.write("\n");
            }
            bufferedWriter.write("\n");
            return;
        }
        for (int j = 1; j <= n; j++) {
            if (vis[i - j + N] == 0 && uvis[i + j] == 0 && col[j] == 0) {
                val[i][j] = 'Q';
                col[j] = vis[i - j + N] = uvis[i + j] = 1;
                dfs(i + 1, n, bufferedWriter);
                val[i][j] = '.';
                col[j] = vis[i - j + N] = uvis[i + j] = 0;
            }else{
                val[i][j] = '.';
            }
        }
    }


}