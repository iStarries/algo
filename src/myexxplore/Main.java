package myexxplore;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class Main {
    public static void main(String[] args) throws IOException {
        // 创建缓冲输入对象，用来从控制台读取数据
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        // 读取第一行，并按空格分割
        // 这里其实第一行只有一个整数 n，这样写也能用
        String[] s = bufferedReader.readLine().split(" ");

        // 取出操作次数 n
        int n = Integer.parseInt(s[0]);

        /*
        开一个二维数组，第一维表示节点数编号，第二维表示字符编号
        arrs[p][u] 表示：从节点 p 出发，沿着字符 u 这条边能走到哪个节点

        开一个一维数组，记录每个节点作为字符串结束的位置，存在多少个这样的字符串
        cnt[p] 表示：有多少个字符串恰好在节点 p 结束
         */
        int[][] arrs = new int[100010][26];
        int[] cnt = new int[100010];

        // idx 表示当前已经创建到第几个节点
        // 0 号节点默认作为根节点，所以新节点从 1 开始分配
        int idx = 0;

        // t 表示当前字符对应的下标：'a'->0, 'b'->1, ..., 'z'->25
        int t = 0;

        // pos 表示当前走到 Trie 的哪个节点
        int pos = 0;

        // 一共处理 n 次操作
        while (n-- > 0) {
            // 读取一行操作，例如 "I abc" 或 "Q abc"
            s = bufferedReader.readLine().split(" ");

            // s[1] 是操作对应的字符串
            String s1 = s[1];

            // 如果当前操作是插入
            if (s[0].equals("I")) {

                // 每次插入都从根节点开始走
                pos = 0;

                // 依次处理字符串中的每个字符
                for (int i = 0; i < s1.length(); i++) {

                    // 把当前字符转成 0~25 的数字
                    t = s1.charAt(i) - 'a';

                    // 如果当前节点 pos 没有字符 t 这条边
                    if (arrs[pos][t] == 0) {

                        // 创建一个新节点
                        idx++;

                        // 让当前节点通过字符 t 指向这个新节点
                        arrs[pos][t] = idx;
                    }

                    // 顺着这条边走到下一个节点
                    pos = arrs[pos][t];
                }

                // 整个字符串插入完成后
                // 在终点节点处记录：这个字符串出现次数 +1
                cnt[pos]++;
            }

            // 否则就是查询操作
            else {

                // 查询也要从根节点开始
                pos = 0;

                // i 要在循环外定义
                // 因为循环结束后还要判断是“正常走完”还是“中途 break”
                int i = 0;

                // 依次按字符串的每个字符往下走
                for (i = 0; i < s1.length(); i++) {

                    // 当前字符转成 0~25
                    t = s1.charAt(i) - 'a';

                    // 如果当前这条路不存在
                    if (arrs[pos][t] == 0) {

                        // 说明这个字符串从来没有被完整插入过
                        // 直接输出 0
                        System.out.println(0);

                        // 提前结束查询循环
                        break;
                    }

                    // 如果路存在，就继续往下走
                    pos = arrs[pos][t];
                }

                // 如果 i == s1.length()，说明 for 循环是正常走完的
                // 即整个字符串路径都存在，没有中途 break
                if (i == s1.length()) System.out.println(cnt[pos]);

                // 此时输出 cnt[pos]
                // 含义是：这个字符串在终点节点出现了多少次
            }
        }
    }
}