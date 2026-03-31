import java.io.*;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Queue;

class Main {
    /*
        将每一种棋盘局面看成一个 String 状态。
        使用队列按层存储当前可以扩展到的状态，利用 BFS 求最少交换次数。
        使用哈希表记录每个状态是否出现过，以及该状态到初始状态的最短距离。
        枚举 x 与上下左右相邻位置交换，生成新的状态；为了继续枚举其他方向，需要在原状态上交换后再恢复。
    */
    public static String[] input;
    public static Queue<String> q = new ArrayDeque<>();
    public static HashMap<String, Integer> map = new HashMap<>();

    public static int[] xs = {1, 0, -1, 0};
    public static int[] ys = {0, 1, 0, -1};
    public static int ans = -1;

    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));
        input = bufferedReader.readLine().split("\\s+");

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 9; i++) {
            stringBuilder.append(input[i]);
        }
        String res = "12345678x";

        q.add(stringBuilder.toString());
        //起始位置的距离是0，在后续遍历的时候会遇到起始状态，那时候已经在map里了
        map.put(stringBuilder.toString(), 0);

        while (!q.isEmpty()) {
            String s = q.poll();
            if (s.equals(res)) {
                ans = map.get(s);
                break;
            }
            StringBuilder s1 = new StringBuilder(s);
            int idx = s1.indexOf("x");
            int x = idx / 3;
            int y = idx % 3;

            for (int i = 0; i < 4; i++){
                //目标交换位置
                int newx = x + xs[i];
                int newy = y + ys[i];
                if (newx >= 0 && newx < 3 && newy >= 0 && newy < 3) {
                    String news = swap(idx, newx * 3 + newy, s1);
                    if (!map.containsKey(news)) {
                        q.add(news);
                        map.put(news, map.get(s) + 1);
                    }
                    swap(idx, newx * 3 + newy, s1);
                }
            }
        }
        //注意写进缓存的东西只能是字符
        bufferedWriter.write(String.valueOf(ans));
        bufferedReader.close();
        bufferedWriter.close();
    }

    private static String swap(int idx, int newidx, StringBuilder s1) {
        char t = s1.charAt(idx);
        s1.setCharAt(idx, s1.charAt(newidx));
        s1.setCharAt(newidx, t);
        return s1.toString();
    }


}