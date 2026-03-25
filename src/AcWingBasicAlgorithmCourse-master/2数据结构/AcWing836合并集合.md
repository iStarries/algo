# AcWing 算法基础课 -- 数据结构

## AcWing 836. 合并集合

`难度：简单`

### 题目描述

一共有n个数，编号是1~n，最开始每个数各自在一个集合中。

现在要进行m个操作，操作共有两种：

-  “M a b”，将编号为a和b的两个数所在的集合合并，如果两个数已经在同一个集合中，则忽略这个操作；
-  “Q a b”，询问编号为a和b的两个数是否在同一个集合中；

**输入格式**

第一行输入整数n和m。

接下来m行，每行包含一个操作指令，指令为“M a b”或“Q a b”中的一种。

**输出格式**

对于每个询问指令”Q a b”，都要输出一个结果，如果a和b在同一集合内，则输出“Yes”，否则输出“No”。

每个结果占一行。

**数据范围**

$1≤n,m≤10^5$

```r
输入样例：

4 5
M 1 2
M 3 4
Q 1 2
Q 1 3
Q 3 4

输出样例：

Yes
No
Yes
```

### Solution

并查集结构：1. 快速合并两个集合，快速查询两个元素是否在同一个集合中

注意 `find(int x)` 函数中的路径压缩 `p[x] = find[x]`

```java
import java.util.*;
import java.io.*;

public class Main{
    public static int N = 100010;
    // 存储每个节点的父节点
    public static int[] p = new int[N];
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        String[] s = br.readLine().split(" ");
        int n = Integer.parseInt(s[0]);
        int m = Integer.parseInt(s[1]);
        // 先初始化每个节点为自己的父节点
        for(int i = 1; i <= n; i++){
            p[i] = i;
        }
        while(m-- > 0){
            s = br.readLine().split(" ");
            int a = Integer.parseInt(s[1]);
            int b = Integer.parseInt(s[2]);
            if(s[0].equals("M")){
                p[find(a)] = find(b);
            }else{
                if(find(a) == find(b)){
                    bw.write("Yes" + "\n");
                }else{
                    bw.write("No" + "\n");
                }
            }
        }
        bw.close();
        br.close();
    }
    // 找到 x 的最后的父节点也就是根节点
    // 路径压缩:每个节点都指向根节点
    public static int find(int x){
        if(p[x] != x) p[x] = find(p[x]);
        return p[x];
    }
}

```

我直接按题目开始讲。题目与题解来自你上传的文件：
你的学习范围里，“并查集”属于第二讲数据结构：

## 1. 这题属于什么类型

这题是 **并查集（Union-Find / DSU）模板题**。

核心考点只有两个：

1. **合并两个集合**
2. **判断两个元素是否在同一个集合**

题目里有两种操作：

* `M a b`：把 `a` 和 `b` 所在集合合并
* `Q a b`：查询 `a` 和 `b` 是否在同一集合

这正是并查集最典型的使用场景。

---

## 2. 朴素做法与优化做法对比

### 朴素想法

你可以给每个元素贴一个“集合编号”，合并时把一个集合里的所有点都改编号。

但这样一旦集合很大，每次合并可能要改很多元素，最坏会很慢。

题目数据范围是 `n,m ≤ 10^5`，如果每次操作都线性处理，就容易超时。

### 优化做法

用 **并查集**：

* 每个集合看成一棵树
* `p[x]` 表示 `x` 的父节点
* 每个集合有一个代表元素，叫 **根节点**
* 如果两个点根节点相同，就说明它们在同一个集合里

这样：

* 合并集合：连根节点
* 查询是否同集合：比较根节点是否相同

---

## 3. 这题的核心思路

### 并查集维护什么

定义数组：

```java
p[i]
```

表示编号 `i` 的父节点是谁。

### 初始化

一开始每个点自己单独成一个集合，所以：

```java
p[i] = i;
```

意思是：自己就是自己的父节点，也就是自己是根。

### 查找操作 `find(x)`

`find(x)` 的作用是：

> 找到 `x` 所在集合的根节点

代码逻辑：

```java
if (p[x] != x) p[x] = find(p[x]);
return p[x];
```

意思是：

* 如果 `x` 不是根，就继续往上找它爸爸的根
* 找到以后，顺手把 `x` 直接挂到根上

这一步叫 **路径压缩**。

路径压缩后，树会变矮，后面查询更快。

### 合并操作

对于 `M a b`：

```java
p[find(a)] = find(b);
```

意思是：

* 先找到 `a` 的根
* 再找到 `b` 的根
* 让 `a` 的根指向 `b` 的根

这样两个集合就合并了。

### 查询操作

对于 `Q a b`：

```java
if (find(a) == find(b))
```

如果根相同，说明在同一个集合中，输出 `Yes`；否则输出 `No`。

---

## 4. 结合你文件里的代码逐段讲解

你上传的题解代码整体思路是对的，可以通过这题。

### ① 数组定义

```java
public static int N = 100010;
public static int[] p = new int[N];
```

* `N = 100010`：因为 `n ≤ 10^5`，开大一点防止越界
* `p[i]`：存每个点的父节点

---

### ② 初始化每个点为单独集合

```java
for(int i = 1; i <= n; i++){
    p[i] = i;
}
```

这一步非常关键。

表示一开始：

* 1 的父亲是 1
* 2 的父亲是 2
* 3 的父亲是 3
* ...

也就是每个点自己单独成集合。

---

### ③ 读入操作

```java
while(m-- > 0){
    s = br.readLine().split(" ");
    int a = Integer.parseInt(s[1]);
    int b = Integer.parseInt(s[2]);
```

每次读一条操作：

* `s[0]` 是操作类型：`M` 或 `Q`
* `s[1]` 是 `a`
* `s[2]` 是 `b`

---

### ④ 合并操作

```java
if(s[0].equals("M")){
    p[find(a)] = find(b);
}
```

这句的意思不是让 `a` 直接指向 `b`，而是：

> 让 `a` 所在集合的根，指向 `b` 所在集合的根

这是并查集合并集合的标准写法。

比如：

* `a` 在集合根 1 下
* `b` 在集合根 4 下

那么执行后：

```java
p[1] = 4;
```

两个集合就并起来了。

---

### ⑤ 查询操作

```java
}else{
    if(find(a) == find(b)){
        bw.write("Yes" + "\n");
    }else{
        bw.write("No" + "\n");
    }
}
```

只要判断根节点是否相同即可。

这是并查集最重要的判断方式：

> 两个点在不在一个集合里，不看它们编号，不看它们父节点，**只看最终根节点是否一样**。

---

### ⑥ `find` 函数

```java
public static int find(int x){
    if(p[x] != x) p[x] = find(p[x]);
    return p[x];
}
```

这个函数是整题灵魂。

#### 含义

* 如果 `x` 是根，直接返回 `x`
* 如果 `x` 不是根，就递归找它的祖先根节点

#### 路径压缩

这一句最重要：

```java
p[x] = find(p[x]);
```

它会把 `x` 直接挂到根上。

比如原来是：

```java
1 -> 2 -> 3 -> 4
```

执行一次 `find(1)` 后可能变成：

```java
1 -> 4
2 -> 4
3 -> 4
4 -> 4
```

这样以后再查就快很多。

---

## 5. 这份题解里要注意的一个小地方

你文件里的说明写了：

```java
p[x] = find[x]
```

这个写法是笔误，**正确的应该是**：

```java
p[x] = find(p[x]);
```

因为 `find` 是函数，不是数组。
这一点你要特别注意，面试或者考试里很容易手滑写错。

---

## 6. Java 实现细节

下面我给你整理成一份更规范一点的 Java 写法，逻辑和你文件里的题解一致：

```java
import java.io.*;

public class Main {
    static final int N = 100010;
    static int[] p = new int[N];

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        String[] first = br.readLine().split(" ");
        int n = Integer.parseInt(first[0]);
        int m = Integer.parseInt(first[1]);

        // 初始化：每个点自成一个集合
        for (int i = 1; i <= n; i++) {
            p[i] = i;
        }

        while (m-- > 0) {
            String[] s = br.readLine().split(" ");
            String op = s[0];
            int a = Integer.parseInt(s[1]);
            int b = Integer.parseInt(s[2]);

            if (op.equals("M")) {
                // 合并 a 和 b 所在的集合
                p[find(a)] = find(b);
            } else {
                // 查询 a 和 b 是否在同一个集合
                if (find(a) == find(b)) {
                    bw.write("Yes\n");
                } else {
                    bw.write("No\n");
                }
            }
        }

        bw.flush();
        bw.close();
        br.close();
    }

    // 返回 x 所在集合的根节点，并进行路径压缩
    static int find(int x) {
        if (p[x] != x) {
            p[x] = find(p[x]);
        }
        return p[x];
    }
}
```

---

## 7. 边界与易错点

这题虽然简单，但初学者很容易错在这些地方：

### ① 合并时不能写成

```java
p[a] = b;
```

因为 `a`、`b` 不一定是根。

正确写法必须是：

```java
p[find(a)] = find(b);
```

---

### ② 查询时不能只比较父节点

不能写：

```java
if (p[a] == p[b])
```

因为 `a` 和 `b` 的父节点可能不同，但最终根一样。

必须写：

```java
if (find(a) == find(b))
```

---

### ③ 初始化不能漏

如果不写：

```java
p[i] = i;
```

并查集就没有建立起来，后面全错。

---

### ④ 路径压缩别写错

正确是：

```java
p[x] = find(p[x]);
```

不是别的形式。

---

## 8. 时间复杂度

并查集在用了路径压缩后：

* 单次操作平均非常快
* 这题总复杂度可以认为接近 `O(m)`

对于 `10^5` 级别数据完全没问题。

---

## 9. 为什么最后能想到这种解法

这个部分我放在最后讲，这是最重要的方法总结。

### 看到什么信号，应该想到并查集？

当题目出现下面这种描述时，你要立刻警觉：

#### 信号 1：有很多个元素，初始各自独立

比如这题一开始：

* `1~n`
* 每个数各自在一个集合中

这就是并查集的经典开场。

#### 信号 2：操作是“合并两个集合”

题目里有：

* `M a b`：把 `a` 和 `b` 所在集合合并

这几乎就是在直接点名并查集。

#### 信号 3：询问“两个点是否属于同一个集合”

题目里有：

* `Q a b`：问是否在同一集合

这就是并查集的第二个核心操作。

---

## 10. 方法迁移：以后哪些题也能这样做

以后你看到这些题型，也要联想到并查集：

* 动态合并若干组元素
* 判断连通性
* 好友关系 / 亲戚关系
* 图中两点是否连通
* 连通块维护
* Kruskal 最小生成树里的连通性判断

也就是说，只要本质是：

> **维护若干个集合，并支持集合合并 + 同集合查询**

就优先考虑并查集。

---

## 11. 你这题应该记住的模板结论

这题本质上就是记住这三句：

```java
p[i] = i;
```

```java
if (p[x] != x) p[x] = find(p[x]);
```

```java
p[find(a)] = find(b);
```

以及查询时：

```java
find(a) == find(b)
```

只要这几句真正理解了，并查集基础题你就已经入门了。

你要是愿意，我下一步可以继续按这个节奏，把这题再给你讲一遍“带样例手推版”，把 `M 1 2、M 3 4、Q 1 2...` 每一步父节点数组怎么变，完整模拟出来。



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
         */
        int[] arr = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            //每个节点的根节点就是它自己
            arr[i] = i;
        }
        while (m-- > 0){
            s = bufferedReader.readLine().split(" ");
            char c = s[0].charAt(0);
            int a = Integer.parseInt(s[1]);
            int b = Integer.parseInt(s[2]);
            if(c == 'M'){
                //合并之前必须先找根节点，不然一个点会出现多个父节点
                a = find(arr, a);
                b = find(arr, b);
                //必须先判断两个根节点a和b不一样（不在同一个集合里）才能修改根节点，
                //不然自己变成了自己的父节点
                if (a != b) arr[a] = b;
            }else{
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