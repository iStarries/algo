# AcWing 算法基础课 -- 搜索与图论

## AcWing 846. 树的重心 

`难度：简单`

### 题目描述

给定一颗树，树中包含n个结点（编号1~n）和n-1条无向边。

请你找到树的重心，并输出将重心删除后，剩余各个连通块中点数的最大值。

重心定义：重心是指树中的一个结点，如果将这个点删除后，剩余各个连通块中点数的最大值最小，那么这个节点被称为树的重心。

**输入格式**

第一行包含整数n，表示树的结点数。

接下来n-1行，每行包含两个整数a和b，表示点a和点b之间存在一条边。

**输出格式**

输出一个整数m，表示将重心删除后，剩余各个连通块中点数的最大值。

**数据范围**

$1≤n≤10^5$

```r
输入样例

9
1 2
1 7
1 4
2 8
2 5
4 3
3 9
4 6

输出样例：

4
```

### Solution

```java
import java.util.*;
import java.io.*;

class Main{
    public static final int N = 100010;
    // 因为是无向边，所以要乘以 2
    public static int[] e = new int[N * 2];
    public static int[] ne = new int[N * 2];
    public static int[] h = new int[N * 2];
    // idx 初始化为 1，这样指向 0 的时候就代表为空
    public static int idx = 1;
    // 标记该点是否遍历过
    public static boolean[] flag = new boolean[N];
    // 记录答案,求最小值，初始化为最大
    public static int ans = N;
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        String[] s;
        for(int i = 1; i <= n - 1; i++){
            s = br.readLine().split(" ");
            int a = Integer.parseInt(s[0]);
            int b = Integer.parseInt(s[1]);
            // 建树
            add(a, b);
            add(b, a);
        }
        // 数据范围 1-100000，所以从 1 开始递归
        dfs(1, n);
        System.out.println(ans);
    }
    public static void add(int a, int b){
        e[idx] = b;
        ne[idx] = h[a];
        h[a] = idx;
        idx++;
    }
    public static int dfs(int u, int n){
        flag[u] = true;
        int size = 0, sum = 0;
        for(int i = h[u]; i != 0; i = ne[i]){
            int j = e[i];
            if(flag[j]) continue;
            int s = dfs(j, n);
            // 找到子树中点数最多的
            size = Math.max(size, s);
            // 计算所有子树的点数的和
            sum += s;
        }
        // 在子树和子树外求一个最大值
        size = Math.max(size, n - sum - 1);
        // 求答案
        ans = Math.min(ans, size);
        return sum + 1;
    }
}
```

# 没读懂题
好，这次我**只重新讲这一题的核心思路**，先**完全不看代码**。你只需要先把“这题到底在算什么”想明白。题目和原题解都在你上传的文件里。

---

# 一、这题到底要你干什么？

题目给你一棵树。

你要做的是：

* 假设删掉一个点
* 看看剩下的树会裂成几块
* 这些块里面，找出**最大的一块**
* 对每个点都这样试一遍
* 最后找一个点，使得“最大的一块”**尽量小**

这个点就叫 **树的重心**。

题目最后输出的，不一定是点编号，而是：

**删掉重心后，剩下各块里最大那块的大小。**

---

# 二、先用最简单的例子理解“重心”

看这条链：

```text
1 - 2 - 3 - 4 - 5
```

## 删掉 3

会变成两块：

```text
1 - 2      4 - 5
```

两块大小分别是 `2` 和 `2`，最大的是 `2`。

---

## 删掉 2

会变成两块：

```text
1      3 - 4 - 5
```

两块大小分别是 `1` 和 `3`，最大的是 `3`。

---

## 删掉 1

会剩一块：

```text
2 - 3 - 4 - 5
```

这一块大小是 `4`，最大就是 `4`。

---

所以：

* 删 1，最大块 = 4
* 删 2，最大块 = 3
* 删 3，最大块 = 2
* 删 4，最大块 = 3
* 删 5，最大块 = 4

最小的是 `2`，对应点 `3`。

所以：

* 点 `3``是重心`
* 题目输出的是 `2`

---

# 三、这题最关键的一句话

对于任意一个点 `u`：

**删掉 `u` 之后，剩下的连通块只会来自两种地方：**

1. `u` 的每一棵子树
2. `u` 上面剩下的那一部分

这句话是整题核心。

---

# 四、为什么只会是这两种？

假设你把整棵树从某个点开始看成“有父子关系”的树。

比如现在看点 `u`。

删掉 `u` 以后：

* `u` 连着的每个儿子方向，都会断开，形成一块
* 除了这些儿子子树之外，树里剩下的其他点，会形成另一块，也就是“上面的部分”

所以删掉 `u` 后的所有块，其实就是：

```text
儿子1的子树
儿子2的子树
儿子3的子树
...
父亲方向剩下的部分
```

没有别的了。

---

# 五、所以我们真正要算的是什么？

对每个点 `u`，我们要知道：

* 它每棵儿子子树有多大
* 它上面那块有多大

然后取这些值的最大值。

这个最大值就是：

**删掉 `u` 后，最大连通块的大小**

---

# 六、那怎么快速知道“每棵子树有多大”？

这就是为什么用 **DFS**。

因为 DFS 特别适合算：

> 以某个点为根，它这棵子树一共有多少个点

比如：

* 先递归去算儿子
* 儿子会告诉你：我这棵子树大小是多少
* 你把所有儿子的子树大小加起来，再加自己 1 个点
* 就知道自己的子树大小了

所以 DFS 做的事很简单：

```text
dfs(u) = 返回以 u 为根的子树大小
```

---

# 七、删掉一个点后，“最大块”怎么计算？

假设当前在点 `u`。

设：

* `s1, s2, s3...` 是每个儿子子树的大小
* `sum = s1 + s2 + s3 + ...`

那么：

* 儿子方向形成的那些块，大小就是 `s1, s2, s3...`
* 上面那一块的大小就是 `n - sum - 1`

为什么是 `n - sum - 1`？

因为：

* 整棵树有 `n` 个点
* 去掉 `u` 自己，减 1
* 再去掉所有儿子子树，减 `sum`

剩下的，就是“上面那块”。

所以删掉 `u` 后，最大块大小就是：

```text
max(每棵儿子子树大小, n - sum - 1)
```

这就是这题最重要的公式。

---

# 八、整题的做法现在就很清楚了

我们对每个点 `u` 都做一件事：

1. 用 DFS 算出每棵儿子子树大小
2. 取儿子子树里的最大值
3. 再算出上面那块：`n - sum - 1`
4. 这几个块里取最大值，得到“删掉 `u` 后最大的块”
5. 所有点里，再取最小值

这个最小值就是答案。

---

# 九、你可以把这题理解成“找最平衡的点”

为什么叫重心？

因为删掉它以后，树会被分成几块。

我们希望：

* 不要有某一块特别大
* 分裂得尽量均匀

所以重心其实就是：

**让整棵树“分得最平衡”的那个点**

---

# 十、朴素做法为什么不行？

你可能会想：

“那我就每个点都删一次，再去数每块多大不就行了？”

可以，但太慢。

因为：

* 一共有 `n` 个点
* 每次删掉一个点后，你可能都要重新遍历整棵树

这样大约是 `O(n^2)`。

这题 `n` 最大到 `10^5`，会超时。

所以必须想办法：

**一次 DFS，就把每个点删掉后的结果都顺便算出来。**

这就是这题厉害的地方。

---

# 十一、你现在只要记住这一个模型

以后遇到这题，你脑子里就想这几句：

```text
删掉一个点后，会裂成若干块
这些块 = 每个儿子子树 + 父亲方向剩余部分
所以只要知道每棵子树大小，就能知道删掉这个点后的最大块
于是用 DFS 统计子树大小
```

---

# 十二、我再用一句最直白的话收尾

这题本质上就是：

**枚举每个点作为“被删除的点”，但不是真的删；而是用 DFS 先算出每棵子树大小，再推出来删掉它后最大的那一块有多大，最后找最小值。**

---

如果你愿意，我下一条就继续只讲一件事：
**为什么代码里会出现 `n - sum - 1`，我给你画图讲到你能真正看懂。**

# 完整讲解
这题是 **树上 DFS + 子树大小统计** 的经典题，核心目标不是“找具体哪个点”，而是求出 **删除某个点后，最大连通块尽量小** 的那个最优值。题目和题解都在你上传的文件里。

先按固定顺序来讲。

---

## 一、这题属于什么题型？核心考点是什么？

这题属于：

* **树的深度优先遍历（DFS）**
* **树上统计**
* **树的重心**

它在你的学习目录里对应第三讲“搜索与图论”里的 **DFS / 树与图的深度优先遍历**。

### 这题的核心考点

不是图的最短路，不是并查集，也不是普通遍历，而是：

**对树中每个点，计算“删掉它之后，各个连通块大小中的最大值”**，然后取这些最大值里的最小值。

所以本质上是在做两件事：

1. 以某个点为根，DFS 统计每棵子树大小。
2. 对每个点，算出“删掉它以后最大的那块有多大”。

---

## 二、核心思路与整体做法

### 1. 先理解“删掉一个点后会发生什么”

假设当前看点 `u`。

把 `u` 删除后，整棵树会裂成若干块：

* `u` 的每个儿子子树，会变成一个连通块
* 除了 `u` 的整棵子树之外，树上“剩余的部分”也会形成一个连通块

所以，删除 `u` 后，连通块大小来源只有两类：

1. 每个子树的大小
2. “上面剩下那部分”的大小，也就是 `n - sum - 1`

这里：

* `sum` = `u` 的所有儿子子树节点数之和
* `1` 是点 `u` 自己

因此：

```text
删除 u 后的最大连通块大小
= max(所有儿子子树大小, n - sum - 1)
```

这就是整题最关键的一步。

---

### 2. 为什么用 DFS？

因为要知道每个点的每棵子树有多大。

而“子树大小”这种信息，天然适合 DFS 自底向上统计：

* 先递归到孩子
* 孩子返回它那棵子树的节点总数
* 父节点汇总所有孩子子树大小

所以 DFS 返回值设成：

```text
dfs(u) 返回以 u 为根的子树节点数
```

---

### 3. 朴素做法和当前做法的区别

### 朴素做法

枚举每个点 `u`：

* 假装删除它
* 再去统计剩余每个连通块大小
* 求最大值

如果你对每个点都重新遍历一次整棵树，复杂度大概会到 **O(n²)**。

当 `n = 10^5` 时，这肯定超时。

---

### 当前做法

只做 **一次 DFS**。

在一次 DFS 过程中，同时得到：

* 每个子树大小
* 每个点删除后的最大连通块大小

总复杂度是 **O(n)**。

这就是这题的优化本质：
**不是每删一个点就重新算，而是在一次树形 DFS 中把所有点的信息顺带算出来。**

---

## 三、题解代码如何实现

下面开始讲你这份 Java 代码是怎么落地这个思路的。

---

### 1. 邻接表存树

```java
public static final int N = 100010;
// 因为是无向边，所以要乘以 2
public static int[] e = new int[N * 2];
public static int[] ne = new int[N * 2];
public static int[] h = new int[N * 2];
public static int idx = 1;
```

这里是 AcWing 很常见的数组模拟邻接表。

含义是：

* `h[a]`：点 `a` 的第一条边下标
* `e[idx]`：这条边指向哪个点
* `ne[idx]`：同一起点的下一条边下标
* `idx`：当前边下标

因为是无向树，一条边 `a-b` 要存两次：

* `a -> b`
* `b -> a`

所以数组大小开 `N * 2`。

---

### 2. 建边函数

```java
public static void add(int a, int b){
    e[idx] = b;
    ne[idx] = h[a];
    h[a] = idx;
    idx++;
}
```

这就是头插法建邻接表。

比如插入 `a -> b`：

* 当前边终点记为 `b`
* 这条边的下一条边设为原来 `h[a]`
* 再让 `h[a]` 指向新边

---

### 3. 读入并建树

```java
BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
int n = Integer.parseInt(br.readLine());
String[] s;
for(int i = 1; i <= n - 1; i++){
    s = br.readLine().split(" ");
    int a = Integer.parseInt(s[0]);
    int b = Integer.parseInt(s[1]);
    add(a, b);
    add(b, a);
}
```

树有 `n-1` 条边，所以循环 `n-1` 次。

每次读两个点，双向建边。

---

### 4. 为什么 DFS 从 1 开始？

```java
dfs(1, n);
```

因为题目给的是一棵树，整张图连通，所以从任意一个点开始都能遍历完整棵树。

这里从 `1` 开始只是习惯写法。

---

### 5. DFS 函数的定义

```java
public static int dfs(int u, int n)
```

这个函数做两件事：

1. 返回 `u` 这棵子树的节点总数
2. 顺便更新删除 `u` 后的最大连通块，并维护全局最优答案 `ans`

---

### 6. 进入一个点时先标记已访问

```java
flag[u] = true;
```

因为这是无向树。

如果不标记，从 `u` 走到 `j` 后，`j` 又会沿着反向边走回 `u`，造成死循环。

---

### 7. `size` 和 `sum` 是这题最关键的两个量

```java
int size = 0, sum = 0;
```

* `sum`：所有子树节点数之和
* `size`：删除 `u` 后，各连通块大小中的最大值

不过在循环过程中，`size` 先只维护“儿子子树里最大的那块”。

---

### 8. 遍历 `u` 的所有邻点

```java
for(int i = h[u]; i != 0; i = ne[i]){
    int j = e[i];
    if(flag[j]) continue;
    int s = dfs(j, n);
    size = Math.max(size, s);
    sum += s;
}
```

解释一下：

#### `int j = e[i];`

当前这条边连到点 `j`

#### `if(flag[j]) continue;`

如果访问过，说明它是父节点方向，跳过

#### `int s = dfs(j, n);`

递归求 `j` 子树大小

#### `size = Math.max(size, s);`

删掉 `u` 后，`j` 这棵子树会单独成为一个连通块，所以要记录子树中最大的那块

#### `sum += s;`

累计所有子树大小，方便后面算“上面剩余部分”

---

### 9. 计算“父方向剩余部分”的大小

```java
size = Math.max(size, n - sum - 1);
```

这是整题最核心的一句。

因为删掉 `u` 后，除了各个儿子子树，还有一块是：

```text
整棵树总点数 n
- 所有儿子子树点数 sum
- 当前点 u 自己 1 个
= n - sum - 1
```

于是：

* 子树方向每一块都考虑过了
* 父方向那一块也考虑了

这时 `size` 就真正表示：

**删除 `u` 后，所有连通块中最大的那个块有多大**

---

### 10. 更新全局答案

```java
ans = Math.min(ans, size);
```

题目要的是：

**所有点里，这个最大连通块大小的最小值**

所以对每个点算完 `size` 后，取最小即可。

---

### 11. 返回当前子树大小

```java
return sum + 1;
```

* `sum` 是所有孩子子树总大小
* `+1` 是当前点自己

所以返回的是以 `u` 为根的子树节点数。

---

## 四、把整段代码逻辑串起来

整套流程可以概括成：

1. 用邻接表存树
2. 从任意点（这里是 1）开始 DFS
3. DFS 过程中：

    * 递归统计每个儿子子树大小
    * 找出最大的儿子子树
    * 再算出“删掉当前点后，父方向那块”的大小
    * 两者取最大，得到删除当前点后的最大连通块
4. 对所有点取最小值，就是答案

---

## 五、为什么能想到这种解法？

这部分单独讲，这是你做题时最该训练的地方。

### 1. 先抓题目中的“删除一个点后，若干连通块”

一看到这种描述，就要想到：

**删除某个树节点后，剩下的块其实非常规整。**

因为树没有环，所以删掉一个点后，形成的连通块一定就是：

* 每个儿子方向一块
* 父亲方向一块

这说明问题不是乱的，而是可以通过“子树大小”来表达。

---

### 2. 一看到“每个点都要统计某种树上信息”

就要联想到：

* 能不能在一次 DFS 中算出所有点答案？
* 这个信息是不是和“子树大小”有关？

这题恰好就是典型例子。

因为删除点后的块大小，直接由各子树大小决定，所以很自然就转成 DFS 统计子树。

---

### 3. 为什么不是 BFS？

因为你要的是 **子树大小**，这是一种“先知道孩子，再知道父亲”的信息。

这类“自底向上”的统计，DFS 比 BFS 更自然。

---

### 4. 以后遇到什么信号，可以往这题思路上靠？

当题目出现下面这些特征时，就要考虑这类做法：

* 给你一棵树
* 对每个点删除 / 切断 / 断开后分析影响
* 要求某个点作为“最优点”
* 删除某点后形成若干连通块
* 答案和“每块大小”有关

这时候就要想：

**删除一个点后，连通块能不能拆成“若干子树 + 其余部分”？**
如果能，通常就能用 DFS 统计子树大小解决。

---

## 六、这题你可以记住的核心模板

以后看到类似题，脑子里可以直接过这个模板：

```text
1. 枚举每个点删除后的影响
2. 删除某点后，连通块 = 各儿子子树 + 父方向剩余部分
3. DFS 返回子树大小
4. 对当前点：
   最大块大小 = max(最大儿子子树大小, n - 当前子树内部总和 - 1)
5. 对所有点取最优
```

---

## 七、给你一个简洁总结

这题本质就是：

**用 DFS 统计每个点的子树大小，再据此计算删除该点后最大的连通块大小，最后取最小值。**

你这份代码的思路是对的，而且就是这题的标准写法。

接下来最值得练的是：
**你自己试着不看代码，只根据“删除点后 = 若干子树 + 外部剩余部分”这个思路，把 `dfs` 函数重新写一遍。**

如果你愿意，我下一步可以继续给你讲这题的 **样例是怎么一步一步算出 4 的**。

```java
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
```

# 非竞赛解法
```java
import java.io.*;
import java.util.*;

class Main {

    static int N = 100010;

    // 邻接表（ArrayList版）
    static ArrayList<Integer>[] g = new ArrayList[N];

    // 标记访问
    static boolean[] vis = new boolean[N];

    // 最终答案
    static int res = N;

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        // 初始化每个节点的邻接表
        for (int i = 1; i <= n; i++) {
            g[i] = new ArrayList<>();
        }

        // 读入边
        for (int i = 0; i < n - 1; i++) {
            String[] s = br.readLine().split(" ");
            int a = Integer.parseInt(s[0]);
            int b = Integer.parseInt(s[1]);

            // 无向边
            g[a].add(b);
            g[b].add(a);
        }

        // DFS
        dfs(1, n);

        System.out.println(res);
    }


    /**
     * 返回：以 u 为根的子树大小
     */
    static int dfs(int u, int n) {

        vis[u] = true;

        int size = 0; // 最大连通块
        int sum = 0;  // 子树总大小

        // 遍历所有邻接点
        for (int v : g[u]) {

            if (!vis[v]) {

                int s = dfs(v, n);

                sum += s;

                // 更新子树中的最大块
                size = Math.max(size, s);
            }
        }

        // 考虑父方向那一块
        size = Math.max(size, n - sum - 1);

        // 更新答案
        res = Math.min(res, size);

        return sum + 1;
    }
}
```

