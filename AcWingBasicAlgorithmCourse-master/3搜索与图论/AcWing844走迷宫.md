# AcWing 算法基础课 -- 搜索与图论

## AcWing 844. 走迷宫  

`难度：简单`

### 题目描述

给定一个n*m的二维整数数组，用来表示一个迷宫，数组中只包含0或1，其中0表示可以走的路，1表示不可通过的墙壁。

最初，有一个人位于左上角(1, 1)处，已知该人每次可以向上、下、左、右任意一个方向移动一个位置。

请问，该人从左上角移动至右下角(n, m)处，至少需要移动多少次。

数据保证(1, 1)处和(n, m)处的数字为0，且一定至少存在一条通路。

**输入格式**

第一行包含两个整数n和m。

接下来n行，每行包含m个整数（0或1），表示完整的二维数组迷宫。

**输出格式**

输出一个整数，表示从左上角移动至右下角的最少移动次数。

**数据范围**

$1≤n,m≤100$

```r
输入样例：

5 5
0 1 0 0 0
0 1 0 1 0
0 0 0 0 0
0 1 1 1 0
0 0 0 1 0

输出样例：

8
```
### Solution

```java
import java.util.*;

class Main{
    public static final int N = 110;
    // a 数组保存地图
    public static int[][] a = new int[N][N];
    // d 数组表示从起点到 (x, y) 的距离,如没有走过为-1
    public static int[][] d = new int[N][N];
    public static int[] dy = {0, -1, 0, 1};
    public static int[] dx = {-1, 0, 1, 0};
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();
        for(int i = 1; i <= n; i++){
            for(int j = 1; j <= m; j++){
                a[i][j] = sc.nextInt();
            }
        }
        // 从(1,1)走到(n,m)
        // 把起始点初始化为 1,这样 d[x][y] 为 0 的点就是没有到达的点
        d[1][1] = 1;
        Queue<Pair> q = new ArrayDeque<>();
        q.add(new Pair(1, 1));
        while(!q.isEmpty()){
            Pair p = q.poll();
            // 朝四个方向走
            for(int i = 0; i < 4; i++){
                int x = p.x + dx[i], y = p.y + dy[i];
                // 如果(x,y)在范围内且没有障碍物,则代表可走,放入队列;
                if(x >= 1 && x <= n && y >= 1 && y <= m && a[x][y] == 0 && d[x][y] == 0){
                    d[x][y] = d[p.x][p.y] + 1;
                    q.add(new Pair(x, y));
                }
            }
        }
        System.out.print(d[n][m] - 1);
    }
    static class Pair{
        int x, y;
        public Pair(int x, int y){
            this.x = x;
            this.y = y;
        }
    }
}
```
这题是**搜索与图论里的 BFS 模板题**，更准确地说，是：

**二维网格最短路 → 用 BFS 求从左上角到右下角的最少步数。**

---

## 一、先讲题型与考点

这题的关键信号非常明显：

* 地图是一个 **n × m 的网格**
* 每次只能往 **上、下、左、右** 走一步
* 问的是 **“至少移动多少次”**，也就是**最短步数**
* 每走一步的代价都一样，都是 1

只要你看到：

* “最少几步”
* “每次移动一步”
* “无权图 / 每条边代价一样”
* “从起点到终点最短距离”

就应该优先想到 **BFS（广度优先搜索）**。
因为在这种“每条边权重相同”的图里，BFS 天然就是按距离一层一层扩展的，所以第一次到达某个点时，走的就是最短路。
这正属于你学习目录里第三讲“搜索与图论”中的 **BFS** 内容。

---

## 二、核心思路与整体做法

### 1. 这题本质上是在什么图上做最短路？

虽然题目给的是二维数组，但你可以把它看成一张图：

* 每个能走的格子 `(x, y)` 是一个点
* 如果两个格子上下左右相邻，并且都能走，就连一条边
* 每条边的长度都是 1

于是问题就变成了：

> 从起点 `(1,1)` 到终点 `(n,m)` 的最短距离是多少？

---

### 2. 为什么不用 DFS？

很多初学者一看到“迷宫”就想 DFS。
DFS 能不能做？**能走到终点，但不适合直接求最短路。**

因为：

* DFS 是“一条路走到底再回头”
* 它不保证先找到的路径就是最短的
* 如果要用 DFS 求最短路，往往要枚举大量路径，效率和实现都不如 BFS

而 BFS 的特点是：

* 先扩展距离为 0 的点
* 再扩展距离为 1 的点
* 再扩展距离为 2 的点
* ……

所以它第一次到达终点时，得到的就是最短步数。

---

### 3. 这题的标准做法

BFS 做法分成 4 步：

#### 第一步：队列里先放起点

从 `(1,1)` 出发。

#### 第二步：记录每个点到起点的距离

用 `d[x][y]` 表示从 `(1,1)` 走到 `(x,y)` 的最短距离。

#### 第三步：每次从队头取一个点，往四个方向扩展

如果新位置满足：

* 没越界
* 不是墙
* 没访问过

那就可以入队。

#### 第四步：更新新点距离

如果从当前点 `p` 走到新点 `(x,y)`，那就有：

```java
d[x][y] = d[p.x][p.y] + 1;
```

最后输出 `d[n][m]` 即可。

---

## 三、朴素做法与当前做法的区别

### 朴素想法

“从起点开始，把所有能走的路都试一遍，看看哪条最短。”

这其实更接近 DFS + 回溯的思路。
问题是：

* 会走很多重复路线
* 容易超时
* 实现还麻烦

### 当前做法

BFS 按“步数层次”推进。

也就是说：

* 先找到所有 1 步能到的点
* 再找到所有 2 步能到的点
* 再找到所有 3 步能到的点

这样天然就保证了最短路，效率也高。

---

## 四、再讲你的题解代码是怎么实现的

下面开始单独讲代码实现，不和思路混在一起。

---

### 1. 定义数组

```java
public static final int N = 110;
// a 数组保存地图
public static int[][] a = new int[N][N];
// d 数组表示从起点到 (x, y) 的距离,如没有走过为-1
public static int[][] d = new int[N][N];
public static int[] dy = {0, -1, 0, 1};
public static int[] dx = {-1, 0, 1, 0};
```

这里：

* `a[i][j]` 存迷宫，`0` 表示能走，`1` 表示墙
* `d[i][j]` 存距离
* `dx, dy` 用来表示四个方向：

    * 上 `(-1, 0)`
    * 左 `(0, -1)`
    * 下 `(1, 0)`
    * 右 `(0, 1)`

这是一种很常见的网格 BFS 写法。

---

### 2. 读入地图

```java
int n = sc.nextInt();
int m = sc.nextInt();
for(int i = 1; i <= n; i++){
    for(int j = 1; j <= m; j++){
        a[i][j] = sc.nextInt();
    }
}
```

这里用了 **1 下标**，也就是地图从 `(1,1)` 开始存。
这样和题目描述一致，写起来比较直观。

---

### 3. 初始化起点

```java
d[1][1] = 1;
Queue<Pair> q = new ArrayDeque<>();
q.add(new Pair(1, 1));
```

这里你的代码没有把 `d` 初始化成 `-1`，而是用了另一种方式：

* `d[x][y] == 0` 表示“还没访问过”
* 起点设成 `1`
* 最后输出时再减 1

也就是说：

* 起点真实距离应该是 `0`
* 但你人为记成了 `1`
* 所以最后答案输出 `d[n][m] - 1`

这是**可以做对的**，只是和注释里“没有走过为 -1”不一致。
代码逻辑本身没问题。

---

### 4. BFS 主循环

```java
while(!q.isEmpty()){
    Pair p = q.poll();
    // 朝四个方向走
    for(int i = 0; i < 4; i++){
        int x = p.x + dx[i], y = p.y + dy[i];
        // 如果(x,y)在范围内且没有障碍物,则代表可走,放入队列;
        if(x >= 1 && x <= n && y >= 1 && y <= m && a[x][y] == 0 && d[x][y] == 0){
            d[x][y] = d[p.x][p.y] + 1;
            q.add(new Pair(x, y));
        }
    }
}
```

这一段就是 BFS 的核心。

含义是：

* 每次取出当前点 `p`
* 枚举它四个方向的相邻点
* 对每个相邻点判断：

    * 是否在地图内
    * 是否能走
    * 是否没访问过
* 如果合法，就：

    * 更新距离
    * 入队

注意这里的“没访问过”判断：

```java
d[x][y] == 0
```

这和你前面把起点设成 `1` 是配套的。

---

### 5. 输出答案

```java
System.out.print(d[n][m] - 1);
```

因为你把起点距离记成了 `1`，所以最后要减掉这个偏移量。

---

## 五、这份代码为什么是对的

因为它满足 BFS 求最短路的核心性质：

### 1. 队列保证按层扩展

先入队的点，距离更小；后入队的点，距离更大。
所以 BFS 总是先处理更近的点。

### 2. 每个点只会第一次入队一次

因为你用 `d[x][y] == 0` 限制了未访问点。
一旦某个点第一次被访问，它的距离就已经确定是最短的，不需要再更新。

### 3. 每次扩展只加 1

因为每移动一步，代价固定是 1。
这正是 BFS 能处理最短路的前提。

---

## 六、给你一个更标准的 Java 写法

虽然你这份代码能做对，但初学阶段我更建议你写成“未访问为 -1”的版本。这样更统一，也更容易和 BFS 模板对应。

```java
import java.util.*;

public class Main {
    static final int N = 110;
    static int[][] g = new int[N][N];
    static int[][] dist = new int[N][N];
    static int[] dx = {-1, 0, 1, 0};
    static int[] dy = {0, 1, 0, -1};

    static class Pair {
        int x, y;
        Pair(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                g[i][j] = sc.nextInt();
                dist[i][j] = -1;   // -1 表示没访问过
            }
        }

        Queue<Pair> q = new ArrayDeque<>();
        q.offer(new Pair(1, 1));
        dist[1][1] = 0;

        while (!q.isEmpty()) {
            Pair cur = q.poll();

            for (int i = 0; i < 4; i++) {
                int x = cur.x + dx[i];
                int y = cur.y + dy[i];

                if (x >= 1 && x <= n && y >= 1 && y <= m
                        && g[x][y] == 0 && dist[x][y] == -1) {
                    dist[x][y] = dist[cur.x][cur.y] + 1;
                    q.offer(new Pair(x, y));
                }
            }
        }

        System.out.println(dist[n][m]);
    }
}
```

这个版本的优点是：

* `dist[1][1] = 0` 更符合“起点到自己距离为 0”
* `-1` 表示没访问过，语义更清晰
* 最后直接输出 `dist[n][m]`，不用再减 1

---

## 七、最后再讲：为什么能想到这种解法？

这是最重要的迁移部分。

以后你看到下面这些条件时，就要开始联想 BFS：

### 触发信号 1：求最少步数

题目问“最少移动多少次”“最短路径长度”“最少操作次数”，这是最强信号。

### 触发信号 2：每次操作代价一样

这里每走一步都算 1 次，所以是**等权图最短路**。
等权图最短路首选 BFS。

### 触发信号 3：状态可以看成点，相邻转移可以看成边

这题里每个格子是点，上下左右移动就是边。
只要能抽象成图，就可以考虑 BFS。

---

## 八、方法迁移

这题学完后，你应该能迁移到下面几类题：

### 1. 网格最短路

比如：

* 迷宫问题
* 从起点到终点最少几步
* 矩阵中上下左右移动

### 2. 字符串状态变换最少次数

例如：

* 每次变换一个字符
* 求从字符串 A 变到 B 的最少操作数

只要每次变换代价一样，也能用 BFS。

### 3. 图上最短边数

无权图里从点 1 到点 n 最少经过多少条边，也是 BFS。

---

## 九、一句话帮你记住这题

**看到“无权图最短路 / 最少步数”，优先想 BFS。**

---

你这道题的学习重点，不是记住代码，而是记住这个判断：

> **网格 + 上下左右走 + 每步代价相同 + 求最少步数 = BFS**

你要是愿意，我可以下一条继续带你把这题的 **BFS 过程手动画一遍**，让你彻底明白“为什么第一次到达就是最短路”。




```java
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
                //bufferedWriter传入的必须是字符，直接传入数字会被当成Unicode字符
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
```