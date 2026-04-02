# AcWing 算法基础课 -- 搜索与图论

## AcWing 847. 图中点的层次 

`难度：简单`

### 题目描述

给定一个n个点m条边的有向图，图中可能存在重边和自环。

所有边的长度都是1，点的编号为1~n。

请你求出1号点到n号点的最短距离，如果从1号点无法走到n号点，输出-1。

**输入格式**

第一行包含两个整数n和m。

接下来m行，每行包含两个整数a和b，表示存在一条从a走到b的长度为1的边。

**输出格式**

输出一个整数，表示1号点到n号点的最短距离。

**数据范围**

$1≤n,m≤10^5$

```r
输入样例：

4 5
1 2
2 3
3 4
1 3
1 4

输出样例：

1
```

### Solution

```java
import java.util.*;
import java.io.*;

public class Main{
    public static final int N = 100010;
    // 建图
    public static int[] e = new int[N];
    public static int[] ne = new int[N];
    public static int[] h = new int[N];
    public static int idx = 1;
    // 记录 1 到 j 点的距离
    public static int[] dist = new int[N];
    // 存储结果
    public static int res = 0;
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        String[] s = br.readLine().split(" ");
        int n = Integer.parseInt(s[0]);
        int m = Integer.parseInt(s[1]);
        while(m-- > 0){
            s = br.readLine().split(" ");
            int a = Integer.parseInt(s[0]);
            int b = Integer.parseInt(s[1]);
            add(a, b);
        }
        bfs();
        System.out.println(dist[n] - 1);
    }
    public static void add(int a, int b){
        e[idx] = b;
        ne[idx] = h[a];
        h[a] = idx;
        idx++;
    }
    public static void bfs(){
        Queue<Integer> q = new ArrayDeque<>();
        q.add(1);
        // dist[1] 初始化为 1，这样只要碰到 0，就认为没有遍历过
        // 可以省去一个标记数组
        dist[1] = 1;
        
        while(!q.isEmpty()){
            int t = q.remove();
            for(int i = h[t]; i != 0; i = ne[i]){
                int j = e[i];
                
                if(dist[j] == 0){
                    q.add(j);
                    dist[j] = dist[t] + 1;
                }
            }
        }
    }
}
```

好，这题我们按教学流程来，一步一步吃透 👇

---

# 一、题型与核心考点

这题是典型的：

👉 **图论 + BFS（广度优先搜索）求最短路**

关键特征你要能一眼识别：

* **有向图**
* **所有边权都是 1**
* **求最短距离**

👉 一旦看到“所有边权一样（尤其是1）+ 最短路”
**第一反应：BFS，而不是 Dijkstra！**

---

# 二、核心思路与整体做法

## 1️⃣ 朴素想法

你可能会想到：

* DFS 搜一遍所有路径
* 找最短的

❌ 问题：
路径可能指数级，**会超时**

---

## 2️⃣ 正确思路：BFS

### 🔥 为什么 BFS 可以？

因为：

👉 BFS 是“一层一层扩展”的

也就是：

```
第1层：距离为1的点
第2层：距离为2的点
第3层：距离为3的点
...
```

👉 所以 **第一次到达某个点，一定是最短路径！**

---

## 3️⃣ 本题做法总结

从 1 号点开始：

1. 用队列做 BFS
2. 记录每个点的距离 `dist[]`
3. 每次扩展当前点的所有邻居
4. 如果没访问过，就更新距离

---

## 4️⃣ 关键技巧（非常重要！）

### ⭐ 用 dist 代替 visited

```java
dist[j] == 0  → 没访问过
```

👉 因为：

* 所有合法距离 ≥ 1
* 所以 0 可以当“未访问”

这样可以 **省一个 visited 数组**

---

# 三、代码实现讲解（逐段拆解）

## 1️⃣ 邻接表建图

```java
public static int[] e = new int[N];
public static int[] ne = new int[N];
public static int[] h = new int[N];
public static int idx = 1;
```

👉 这是经典的 **数组模拟邻接表**

### add函数：

```java
public static void add(int a, int b){
    e[idx] = b;
    ne[idx] = h[a];
    h[a] = idx;
    idx++;
}
```

👉 含义：

```
a → b 这条边加入图中
```

结构类似：

```
h[a] -> idx -> ne[idx] -> ...
```

---

## 2️⃣ BFS 核心代码

```java
Queue<Integer> q = new ArrayDeque<>();
q.add(1);
dist[1] = 1;
```

👉 初始化：

* 从 1 开始
* 距离设为 1（注意这个技巧）

---

### 主循环：

```java
while(!q.isEmpty()){
    int t = q.remove();
```

👉 每次取出当前层节点

---

### 遍历邻接点：

```java
for(int i = h[t]; i != 0; i = ne[i]){
    int j = e[i];
```

👉 找到 t 的所有出边

---

### 状态转移：

```java
if(dist[j] == 0){
    q.add(j);
    dist[j] = dist[t] + 1;
}
```

👉 核心逻辑：

* 没访问过 → 入队
* 距离 = 当前 + 1

---

## 3️⃣ 最终输出

```java
System.out.println(dist[n] - 1);
```

### ❗ 为什么要 -1？

因为我们一开始：

```java
dist[1] = 1;
```

实际距离是：

```
真实距离 = dist[n] - 1
```

---

# 四、为什么能想到 BFS（最重要）

这是你要真正学会的 👇

---

## 🔥 识别信号

看到以下条件，直接联想到 BFS：

### ✅ 条件1：图

### ✅ 条件2：求最短路径

### ✅ 条件3：边权全相同（=1）

👉 结论：

```
BFS = 最短路（无权图）
```

---

## ⚖️ 对比总结

| 方法           | 适用情况     |
| ------------ | -------- |
| BFS          | 边权全一样    |
| Dijkstra     | 边权不同（正数） |
| Bellman-Ford | 有负权      |

---

## 🧠 方法迁移

这题你要学会举一反三：

### 类似题型：

* 迷宫最短路径
* 最少步数问题
* 图中最少边数路径

👉 本质都是：

**“每走一步代价一样” → BFS**

---

# 五、给你一个思考题（巩固）

如果现在：

👉 每条边权变成不同的数

还能用 BFS 吗？

你可以先想一下，我再帮你分析 👍

```java
import java.io.*;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;

class Main {

    // 用来临时存一行输入拆分后的结果
    public static String[] input;

    // 题目范围 n, m <= 100000，所以数组开到 100010
    public static int N = 100010;

    // distancce[x] 表示：从 1 号点走到 x 号点的“层数”
    // 这里采用一种常见技巧：
    // distancce[x] == 0  表示 x 还没有被访问过
    // distancce[1] = 1   表示起点已经访问，且真实距离要减 1
    // 所以最后答案输出 distancce[n] - 1
    public static int[] distancce = new int[N];

    // 邻接表建图：
    // g[a] 里面存的是所有从 a 出发能到达的点
    // 例如有边 a -> b，就把 b 加到 g[a] 中
    public static ArrayList<Integer>[] g = new ArrayList[N];

    public static void main(String[] args) throws IOException {

        // 输入输出
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));

        // 读取第一行：n 个点，m 条边
        input = bufferedReader.readLine().split("\\s+");
        int n = Integer.parseInt(input[0]);
        int m = Integer.parseInt(input[1]);

        // 初始化每个点对应的邻接表
        // 点的编号是 1 ~ n，所以从 1 开始初始化
        for (int i = 1; i <= n; i++) {
            g[i] = new ArrayList<>();
        }

        // 读入 m 条有向边
        // 每条边 a b 表示：a -> b
        while (m-- > 0) {
            input = bufferedReader.readLine().split("\\s+");
            int a = Integer.parseInt(input[0]);
            int b = Integer.parseInt(input[1]);
            g[a].add(b);
        }

        // BFS 用队列
        // 队列里存“当前准备扩展的点”
        Queue<Integer> p = new ArrayDeque<>();

        // 起点是 1
        // 这里把 distancce[1] 设为 1，而不是 0
        // 这样就可以把 0 当作“没访问过”的标记
        distancce[1] = 1;
        p.offer(1);

        // 开始 BFS
        while (!p.isEmpty()) {

            // 取出队头，表示当前要扩展的点
            int cur = p.poll();

            // 如果当前点已经是终点 n
            // 由于 BFS 是按层扩展的，所以第一次到达 n 一定就是最短路
            if (cur == n) {
                // 因为起点被设成 1，所以真实距离要减 1
                bufferedWriter.write(String.valueOf(distancce[cur] - 1));
                bufferedWriter.close();
                return;
            }

            // 当前点到起点的“层数”
            int dist = distancce[cur];

            // 枚举 cur 的所有邻接点
            // 也就是所有从 cur 能一步走到的点
            for (Integer i : g[cur]) {

                // 如果 i 还没访问过
                if (distancce[i] == 0) {

                    // 先入队，后面会继续扩展它
                    p.offer(i);

                    // i 的距离 = 当前点距离 + 1
                    distancce[i] = dist + 1;
                }
            }
        }

        // 如果 BFS 结束了还没有遇到 n
        // 说明 1 号点无法到达 n 号点
        bufferedWriter.write("-1");
        bufferedWriter.close();
    }
}
```