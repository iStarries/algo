# AcWing 算法基础课 -- 数据结构

## AcWing 837. 连通块中点的数量 

`难度：简单`

### 题目描述

给定一个包含n个点（编号为1~n）的无向图，初始时图中没有边。

现在要进行m个操作，操作共有三种：

- “C a b”，在点a和点b之间连一条边，a和b可能相等；
- “Q1 a b”，询问点a和点b是否在同一个连通块中，a和b可能相等；
- “Q2 a”，询问点a所在连通块中点的数量；

**输入格式**

第一行输入整数n和m。

接下来m行，每行包含一个操作指令，指令为“C a b”，“Q1 a b”或“Q2 a”中的一种。

**输出格式**

对于每个询问指令”Q1 a b”，如果a和b在同一个连通块中，则输出“Yes”，否则输出“No”。

对于每个询问指令“Q2 a”，输出一个整数表示点a所在连通块中点的数量

每个结果占一行。

**数据范围**

$1≤n,m≤10^5$

```r
输入样例：

5 5
C 1 2
Q1 1 2
Q2 1
C 2 5
Q2 5

输出样例：

Yes
2
3
```

### Solution

这道题在并查集的基础上，还有一个计算每个连通块的点的数量。

每个连通块的点的数量也就是这个连通块根节点所在连通块中点的数量。
```java
import java.util.*;
import java.io.*;

public class Main{
    public static int N = 100010;
    public static int[] cnt = new int[N];
    public static int[] p = new int[N];
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        String[] s = br.readLine().split(" ");
        int n = Integer.parseInt(s[0]);
        // 初始化,每个节点的父节点是自己
        for(int i = 1; i <= n; i++){
            p[i] = i;
            cnt[i] = 1;
        }
        int m = Integer.parseInt(s[1]);
        while(m-- > 0){
            s = br.readLine().split(" ");
            // 合并连通块
            if(s[0].equals("C")){
                int a = Integer.parseInt(s[1]);
                int b = Integer.parseInt(s[2]);
                a = find(a);
                b = find(b);
                if(a != b){
                    p[a] = b;
                    cnt[b] = cnt[a] + cnt[b];
                }
            }
            // 两个点是否在一个连通块
            else if(s[0].equals("Q1")){
                int a = Integer.parseInt(s[1]);
                int b = Integer.parseInt(s[2]);
                if(find(a) == find(b)) bw.write("Yes\n");
                else bw.write("No\n");
            }
            // 点 a 所在连通块的点的数量
            else if(s[0].equals("Q2")){
                int a = Integer.parseInt(s[1]);
                bw.write(cnt[find(a)] + "\n");
            }
            bw.flush();
        }
        bw.close();
        br.close();
    }
    public static int find(int x){
        if(p[x] != x) p[x] = find(p[x]);
        return p[x];
    }
}
```

我直接按这道题开始讲。题目和题解来自你刚上传的文件：

## 1. 这题属于什么类型

这题还是 **并查集**，但比上一题多了一个维护信息：

* `C a b`：连边，等价于把两个连通块合并
* `Q1 a b`：查询两个点是否在同一个连通块
* `Q2 a`：查询点 `a` 所在连通块的点数

所以它的本质是：

> **并查集 + 维护每个集合的大小**

这题是并查集模板题的升级版。
上一题只需要维护“是否在同一集合”；这题还要维护“这个集合有多大”。

---

## 2. 核心做法是什么

并查集仍然维护父节点数组：

```java
p[i]
```

表示 `i` 的父节点是谁。

再多开一个数组：

```java
cnt[i]
```

表示：

> 以 `i` 为根的这个集合，当前有多少个点

注意这句话很关键：

* `cnt[x]` **只有当 `x` 是根节点时才有实际意义**
* 非根节点的 `cnt[x]`，不用管

---

## 3. 为什么 `Q2 a` 可以这样做

题目问的是：

> 点 `a` 所在连通块中点的数量

而并查集里，一个点所在集合的信息，应该去看它的 **根节点**。

所以：

1. 先找到 `a` 的根：`find(a)`
2. 再看这个根维护的集合大小：`cnt[find(a)]`

所以查询代码就是：

```java
cnt[find(a)]
```

这就是本题最核心的一步。

---

## 4. 朴素做法与并查集做法对比

### 朴素想法

每次连边以后，都重新从某个点开始搜整张图，统计连通块大小。

比如 `Q2 a` 时临时做一次 DFS / BFS。

这样单次查询最坏可能要 `O(n+m)`，数据范围是 `10^5`，显然会慢。

### 并查集做法

并查集直接维护“每个连通块的代表元”和“这个连通块有多少点”。

这样：

* 合并时顺便更新大小
* 查询时直接返回

效率就很高。

---

## 5. 这题的整体思路

### 第一步：初始化

一开始没有边，所以每个点自己单独是一个连通块。

因此：

```java
p[i] = i;
cnt[i] = 1;
```

意思是：

* 每个点的父节点是自己
* 每个连通块只有自己一个点，所以大小是 1

---

### 第二步：查找根节点

仍然用 `find(x)`：

```java
public static int find(int x){
    if(p[x] != x) p[x] = find(p[x]);
    return p[x];
}
```

这和上一题一样，带 **路径压缩**。

---

### 第三步：合并两个连通块

对于 `C a b`：

先找到它们各自的根：

```java
a = find(a);
b = find(b);
```

如果已经在同一个集合里，就不用合并：

```java
if(a != b)
```

否则，把一个根挂到另一个根上，并更新集合大小：

```java
p[a] = b;
cnt[b] = cnt[a] + cnt[b];
```

含义是：

* 原来 `a` 这棵树所在集合有 `cnt[a]` 个点
* 原来 `b` 这棵树所在集合有 `cnt[b]` 个点
* 合并以后，新根是 `b`
* 所以新集合大小就是两者之和

---

### 第四步：查询两个点是否连通

对于 `Q1 a b`：

```java
if(find(a) == find(b))
```

根节点相同，就说明在同一个连通块。

---

### 第五步：查询某个点所在连通块大小

对于 `Q2 a`：

```java
cnt[find(a)]
```

这个一定要记住。

---

## 6. 结合你文件里的代码逐段讲解

你上传的代码整体是正确的，可以通过这题：

### ① 数组定义

```java
public static int N = 100010;
public static int[] cnt = new int[N];
public static int[] p = new int[N];
```

* `p[i]`：父节点
* `cnt[i]`：以 `i` 为根的集合大小

---

### ② 初始化

```java
for(int i = 1; i <= n; i++){
    p[i] = i;
    cnt[i] = 1;
}
```

这一步非常标准。

因为一开始每个点都是单独集合，所以：

* 父节点是自己
* 集合大小是 1

---

### ③ 合并操作

```java
if(s[0].equals("C")){
    int a = Integer.parseInt(s[1]);
    int b = Integer.parseInt(s[2]);
    a = find(a);
    b = find(b);
    if(a != b){
        p[a] = b;
        cnt[b] = cnt[a] + cnt[b];
    }
}
```

这一段写得很好，逻辑很完整。

注意这里有两个关键点。

#### 关键点 1：先找根再合并

不能直接写：

```java
p[a] = b;
```

因为输入的 `a`、`b` 不一定是根。

必须先：

```java
a = find(a);
b = find(b);
```

---

#### 关键点 2：只有真的合并了，才能更新大小

只有在 `a != b` 的时候，才说明原来是两个不同集合。

这时候才做：

```java
cnt[b] = cnt[a] + cnt[b];
```

如果 `a == b`，说明本来就在一个集合里，再加一次就错了。

---

### ④ 连通性查询

```java
else if(s[0].equals("Q1")){
    int a = Integer.parseInt(s[1]);
    int b = Integer.parseInt(s[2]);
    if(find(a) == find(b)) bw.write("Yes\n");
    else bw.write("No\n");
}
```

这和上一题完全一样，本质还是：

> 判断两个点根节点是否相同

---

### ⑤ 大小查询

```java
else if(s[0].equals("Q2")){
    int a = Integer.parseInt(s[1]);
    bw.write(cnt[find(a)] + "\n");
}
```

这一句就是本题新增内容。

因为 `find(a)` 找到的是根，而 `cnt[根]` 保存的正是整个连通块的大小。

---

## 7. 用样例手推一遍

样例：

```text
5 5
C 1 2
Q1 1 2
Q2 1
C 2 5
Q2 5
```

### 初始状态

每个点自成集合：

* `{1}` 大小 1
* `{2}` 大小 1
* `{3}` 大小 1
* `{4}` 大小 1
* `{5}` 大小 1

即：

```text
p[1]=1 p[2]=2 p[3]=3 p[4]=4 p[5]=5
cnt[1]=1 cnt[2]=1 cnt[3]=1 cnt[4]=1 cnt[5]=1
```

---

### 操作 1：`C 1 2`

找到根：

* `find(1)=1`
* `find(2)=2`

不在同一集合，合并：

```java
p[1] = 2;
cnt[2] = cnt[1] + cnt[2] = 2;
```

现在集合变成：

* `{1,2}` 大小 2
* `{3}` 大小 1
* `{4}` 大小 1
* `{5}` 大小 1

---

### 操作 2：`Q1 1 2`

* `find(1)=2`
* `find(2)=2`

根相同，输出：

```text
Yes
```

---

### 操作 3：`Q2 1`

* `find(1)=2`
* `cnt[2]=2`

输出：

```text
2
```

---

### 操作 4：`C 2 5`

* `find(2)=2`
* `find(5)=5`

合并：

```java
p[2] = 5;
cnt[5] = cnt[2] + cnt[5] = 3;
```

现在集合变成：

* `{1,2,5}` 大小 3
* `{3}` 大小 1
* `{4}` 大小 1

---

### 操作 5：`Q2 5`

* `find(5)=5`
* `cnt[5]=3`

输出：

```text
3
```

和样例一致。

---

## 8. 这题最容易错的地方

### ① 忘了只有根节点的 `cnt` 才有效

比如你不能随便写：

```java
cnt[a]
```

因为 `a` 可能不是根。

必须写：

```java
cnt[find(a)]
```

---

### ② 合并前不找根

错误写法：

```java
p[a] = b;
cnt[b] += cnt[a];
```

这会把非根节点乱连，集合大小也会乱掉。

必须先：

```java
a = find(a);
b = find(b);
```

---

### ③ 已经在同一个集合时还更新大小

如果不判断：

```java
if(a != b)
```

那么本来一个集合再“合并”一次，就会重复加，答案就错了。

---

### ④ 把 `cnt[a] = cnt[a] + cnt[b]` 写反了

因为你这里是：

```java
p[a] = b;
```

说明新根是 `b`。

那集合大小就应该存到 `b` 上：

```java
cnt[b] = cnt[a] + cnt[b];
```

不是存到 `a` 上。

这个要和“谁当根”保持一致。

---

## 9. Java 写法细节

你这份代码还有一个小细节：

```java
bw.flush();
```

你把它放在了 `while` 循环里，每次操作都刷一次输出缓冲。

这不会导致答案错误，但效率上没必要。更常见的写法是：

* 循环里只 `write`
* 最后统一 `flush`

更推荐这样写：

```java
import java.io.*;

public class Main {
    static final int N = 100010;
    static int[] p = new int[N];
    static int[] cnt = new int[N];

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        String[] s = br.readLine().split(" ");
        int n = Integer.parseInt(s[0]);
        int m = Integer.parseInt(s[1]);

        for (int i = 1; i <= n; i++) {
            p[i] = i;
            cnt[i] = 1;
        }

        while (m-- > 0) {
            s = br.readLine().split(" ");
            String op = s[0];

            if (op.equals("C")) {
                int a = Integer.parseInt(s[1]);
                int b = Integer.parseInt(s[2]);

                a = find(a);
                b = find(b);

                if (a != b) {
                    p[a] = b;
                    cnt[b] += cnt[a];
                }
            } else if (op.equals("Q1")) {
                int a = Integer.parseInt(s[1]);
                int b = Integer.parseInt(s[2]);

                if (find(a) == find(b)) {
                    bw.write("Yes\n");
                } else {
                    bw.write("No\n");
                }
            } else { // Q2
                int a = Integer.parseInt(s[1]);
                bw.write(cnt[find(a)] + "\n");
            }
        }

        bw.flush();
        br.close();
        bw.close();
    }

    static int find(int x) {
        if (p[x] != x) {
            p[x] = find(p[x]);
        }
        return p[x];
    }
}
```

这里：

```java
cnt[b] += cnt[a];
```

和

```java
cnt[b] = cnt[a] + cnt[b];
```

是一个意思，前者更简洁。

---

## 10. 为什么最后能想到这种解法

这个部分最重要，是你以后做题识别题型的关键。

### 看到哪些信号，应该想到“并查集 + 维护集合大小”？

#### 信号 1：初始时每个点独立

题目说：

* 初始时图中没有边

这就意味着：

* 一开始每个点各自是一个连通块

这就是并查集的典型初始状态。

---

#### 信号 2：操作里有“连边 / 合并”

题目说：

* `C a b` 在 `a` 和 `b` 之间连边

对于无向图来说，连边的本质就是：

> 把 `a` 和 `b` 所在的两个连通块合并

这就是并查集最擅长的操作。

---

#### 信号 3：询问两个点是否连通

题目有 `Q1 a b`。

这就是并查集模板中的：

> 判断两个点是否在同一个集合

---

#### 信号 4：还要问所在块的大小

题目又有 `Q2 a`。

这说明普通并查集还不够，需要在根节点额外维护一个信息：

> 连通块大小

所以自然升级成：

> 并查集 + 额外信息维护

---

## 11. 如何举一反三

这题学完后，你要把“并查集能维护集合附加信息”这个意识建立起来。

除了集合大小，还常见维护：

* 集合中点的个数
* 某个集合的边数
* 某个集合的权值和
* 到根节点的距离
* 扩展域关系（敌人/朋友）
* 带权并查集

也就是说，只要题目本质是：

> 动态合并集合，并且需要查询某个集合的一些属性

就可以先想：

> 能不能把信息维护在根节点上

这就是这题真正值得学会的地方。

---

## 12. 这题你最终要记住的模板

最核心就这几句：

### 初始化

```java
p[i] = i;
cnt[i] = 1;
```

### 查根

```java
if (p[x] != x) p[x] = find(p[x]);
```

### 合并

```java
a = find(a);
b = find(b);
if (a != b) {
    p[a] = b;
    cnt[b] += cnt[a];
}
```

### 查询连通块大小

```java
cnt[find(a)]
```

---

这题你已经可以把它看成“并查集第二模板题”了：
第一题是只会合并和判断是否同集合；第二题是在此基础上再维护集合大小。

下一步最适合练的是：**食物链 / 带权并查集**，因为它是在“普通并查集 + 维护额外信息”上继续升级。

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class Main {
    public static void main(String[] args) throws IOException {
        // 创建缓冲输入对象，用来从控制台读取数据
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String[] s = bufferedReader.readLine().split(" ");

        int n = Integer.parseInt(s[0]);
        int m = Integer.parseInt(s[1]);
        /*
        数组的下标就对应了 n 个数，每个数组元素最初都是-1，表示没有父节点。
        每次合并两个数，就把一个数的父节点设置为另一个数的索引。
        每次查询就找两个数的父节点是不是同一个，判断是否属于同一个集合。
        统计连通块的节点数量就在根节点维护一个节点总数
         */
        int[] arr = new int[n + 1];
        int[] cnt = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            //每个节点的根节点就是它自己
            arr[i] = i;
            //每个节点对应自己的集合，数量为 1
            cnt[i] = 1;
        }
        while (m-- > 0){
            s = bufferedReader.readLine().split(" ");
            String c = s[0];
            if(c.equals("C")){
                int a = Integer.parseInt(s[1]);
                int b = Integer.parseInt(s[2]);
                //合并之前必须先找根节点，不然一个点会出现多个父节点
                a = find(arr, a);
                b = find(arr, b);
                if (a != b){
                    arr[a] = b;
                    //这里不能用++，因为两个集合合并可能涉及很多节点
                    cnt[b] += cnt[a];
                }

            }else if (c.equals("Q2")){
                int a = Integer.parseInt(s[1]);
                a = find(arr, a);
                System.out.println(cnt[a]);
            }else{
                int a = Integer.parseInt(s[1]);
                int b = Integer.parseInt(s[2]);
                a = find(arr, a);
                b = find(arr, b);
                if(a == b) System.out.println("Yes");
                else System.out.println("No");
            }
        }
    }
    public static int find(int[] arr, int x){
        //这里实现了递归路径压缩，递归结束后这颗树的每个节点都直接指向根节点
        if(arr[x] != x) arr[x] = find(arr, arr[x]);
        return arr[x];
    }
}
```