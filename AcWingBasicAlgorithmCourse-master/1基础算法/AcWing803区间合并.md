# AcWing 算法基础课 -- 基础算法

## AcWing 803. 区间合并 

`难度：简单`

### 题目描述

给定 n 个区间 $[l_i,r_i]$，要求合并所有有交集的区间。

注意如果在端点处相交，也算有交集。

输出合并完成后的区间个数。

例如：[1,3]和[2,6]可以合并为一个区间[1,6]。

**输入格式**

第一行包含整数n。

接下来n行，每行包含两个整数 l 和 r。

**输出格式**

共一行，包含一个整数，表示合并区间完成后的区间个数。

**数据范围**:

$1≤n≤100000,$
$−10^9≤l_i≤r_i≤10^9$

```r
输入样例：

5
1 2
2 4
5 6
7 8
7 9

输出样例：

3
```

### Solution

```java
import java.util.*;
import java.io.*;

public class Main{
    public static class Pairs{
        int l, r;
        public Pairs(int l, int r){
            this.l = l;
            this.r = r;
        }
    }
    public static void main(String[] args) throws IOException{
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(in.readLine());
        List<Pairs> alls = new ArrayList<>();
        for(int i = 0; i < n; i++){
            String[] s = in.readLine().split(" ");
            int l = Integer.parseInt(s[0]);
            int r = Integer.parseInt(s[1]);
            alls.add(new Pairs(l, r));
        }
        // 利用 lambda 表达式重写比较器
        // 根据 左边界 升序排列
        Collections.sort(alls, (Pairs a1, Pairs a2) -> a1.l - a2.l);
        int res = 1;
        Pairs p = new Pairs(alls.get(0).l, alls.get(0).r);
        for(int i = 1; i < n; i++){
            int l = alls.get(i).l;
            int r = alls.get(i).r;
            if(l > p.r){
                res++;
                p.l = alls.get(i).l;
                p.r = alls.get(i).r;
            }
            else{
                if(r > p.r){
                    p.r = alls.get(i).r;
                }
            }
        }
        System.out.println(res);
    }
}
```

1. 根据左端点排序
2. 更新右端点，取较大值`r = Math.max(a[i][1], r);`
3. 不用 List，Pair，只用数组

```java
import java.util.*;
import java.io.*;

class Main{
    static final int N = 100010;
    static int[][] a = new int[N][2];
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] s = br.readLine().split(" ");
        int n = Integer.parseInt(s[0]);
        for(int i = 0; i < n; i++){
            s = br.readLine().split(" ");
            a[i][0] = Integer.parseInt(s[0]);
            a[i][1] = Integer.parseInt(s[1]);
        }
        // 按照左端点排序
        // 二维数组根据第一项升序，指定区间[0, n)
        Arrays.sort(a, 0, n, (x1, x2) -> x1[0] - x2[0]);
        // res 初始化为 1，之后每多一个区间就 +1
        int res = 1;
        int l = a[0][0], r = a[0][1];
        for(int i = 1; i < n; i++){
            // 上一个时刻的 r 小于当前的 l，说明不能合并
            if(r < a[i][0]){
                res++;
            }
            // 更新 r，选两者较大值
            r = Math.max(a[i][1], r);
        }
        System.out.println(res);
    }
}
```

这题是**经典的区间合并模板题**，属于“**排序 + 贪心扫描**”这一类基础算法题。题目要求把所有有交集的区间合并，最后输出合并后区间的个数；并且**端点相交也算重叠**。你上传的题目和两份 Java 写法都很典型。

## 1. 题型与核心考点

这题属于：

* **基础算法**
* **区间合并**
* **先排序，再线性扫描**

核心考点就一句话：

> 按左端点排序后，重叠的区间一定会排在一起，于是只要从左到右扫一遍，就能完成合并。

---

## 2. 这题最核心的思路

### 第一步：先按左端点排序

比如区间原来是乱序的：

```text
[5,6], [1,2], [2,4], [7,9], [7,8]
```

按左端点排序后变成：

```text
[1,2], [2,4], [5,6], [7,8], [7,9]
```

这样一来，后面的区间只可能和“当前正在维护的合并区间”发生关系。

---

### 第二步：维护一个当前合并区间 `[st, ed]`

假设当前已经合并到一个区间 `[st, ed]`。

再看下一个区间 `[l, r]`：

#### 情况 1：`l > ed`

说明新区间在当前区间右边，**完全不重叠**。

这时：

* 之前那个合并区间结束了
* 合并后的区间个数 `res++`
* 当前区间更新为 `[l, r]`

#### 情况 2：`l <= ed`

说明有交集，或者端点相接，也算重叠。

这时只需要更新右端点：

```java
ed = Math.max(ed, r);
```

因为左端点已经更靠左了，不需要动。

---

## 3. 为什么只看右端点就够了？

因为已经按左端点排好序了。

当前维护的区间一定是“左边最早开始的那一段”，后面来的区间左端点只会更大，不可能再把左边界改得更小。

所以重叠时只需要考虑：

* 当前右端点能不能向右扩展

这就是：

```java
ed = Math.max(ed, r);
```

---

## 4. 用样例走一遍

题目样例：

```text
[1,2]
[2,4]
[5,6]
[7,8]
[7,9]
```

初始化：

```text
当前区间 = [1,2]
res = 1
```

继续扫描：

### 看 `[2,4]`

* `2 <= 2`，有交集
* 合并为 `[1,4]`

### 看 `[5,6]`

* `5 > 4`，不相交
* 说明前一个合并区间结束
* `res = 2`
* 当前区间改成 `[5,6]`

### 看 `[7,8]`

* `7 > 6`，不相交
* `res = 3`
* 当前区间改成 `[7,8]`

### 看 `[7,9]`

* `7 <= 8`，相交
* 合并为 `[7,9]`

最终答案是 `3`。

---

## 5. 朴素做法和优化做法对比

### 朴素想法

每加入一个区间，就去和其他区间反复比较，看能不能继续合并。

这样做会很乱，而且最坏可能接近 **O(n²)**。

---

### 优化做法

先排序，再扫一遍：

* 排序：`O(n log n)`
* 扫描：`O(n)`

总复杂度：

```text
O(n log n)
```

空间复杂度看你怎么存：

* 用 `List<Pair>`：`O(n)`
* 用二维数组：`O(n)`

这也是这题标准做法。

---

## 6. 你这两份代码在做什么

你上传的题解里给了两种写法。

### 第一种：自定义 `Pairs` 类

优点：

* 可读性好
* `l`、`r` 含义直观
* 初学时更容易理解“区间对象”的概念

核心代码就是：

```java
Collections.sort(alls, (a1, a2) -> a1.l - a2.l);
```

按左端点排序。

然后：

```java
Pairs p = new Pairs(alls.get(0).l, alls.get(0).r);
```

`p` 表示“当前合并区间”。

接下来逐个比较：

```java
if (l > p.r) {
    res++;
    p.l = l;
    p.r = r;
} else {
    p.r = Math.max(p.r, r);
}
```

这就是区间合并的核心模板。

---

### 第二种：二维数组写法

优点：

* 写起来更接近竞赛模板
* 常数略小
* 不需要额外定义类

核心是：

```java
Arrays.sort(a, 0, n, (x1, x2) -> x1[0] - x2[0]);
```

然后维护当前右端点 `r`。

这份代码里：

```java
if (r < a[i][0]) {
    res++;
}
r = Math.max(a[i][1], r);
```

它没有显式写“重置新区间左端点”，因为题目只要求输出**个数**，不要求输出具体区间，所以只维护 `r` 就够了。

这个思路是对的。

---

## 7. 我更推荐你记住的 Java 写法

下面给你一份更规范、适合背模板的 Java 版本：

```java
import java.io.*;
import java.util.*;

public class Main {
    static class Pair {
        int l, r;
        Pair(int l, int r) {
            this.l = l;
            this.r = r;
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        List<Pair> list = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            String[] s = br.readLine().split(" ");
            int l = Integer.parseInt(s[0]);
            int r = Integer.parseInt(s[1]);
            list.add(new Pair(l, r));
        }

        // 按左端点升序排序
        list.sort((a, b) -> Integer.compare(a.l, b.l));

        int res = 1;
        int st = list.get(0).l;
        int ed = list.get(0).r;

        for (int i = 1; i < n; i++) {
            int l = list.get(i).l;
            int r = list.get(i).r;

            // 不相交，产生一个新区间
            if (l > ed) {
                res++;
                st = l;
                ed = r;
            } else {
                // 相交，更新右端点
                ed = Math.max(ed, r);
            }
        }

        System.out.println(res);
    }
}
```

---

## 8. 代码里的关键变量分别表示什么

### `res`

合并后的区间数量。

---

### `st, ed`

当前正在维护的合并区间：

```text
[st, ed]
```

不过这题只输出区间个数，其实 `st` 基本不参与判断，真正重要的是 `ed`。

所以很多人会把代码进一步简化成只维护 `ed`。

---

### `l, r`

当前扫描到的新区间的左右端点。

---

## 9. 这题最容易错的地方

### 易错点 1：重叠条件写错

题目说：

> 端点相交也算有交集

所以：

* `[1,2]` 和 `[2,3]` 是可以合并的

因此“不重叠”的条件应该写成：

```java
l > ed
```

不是 `l >= ed`。

这一点非常关键。

---

### 易错点 2：没先排序

如果不排序，线性扫描就没有依据，后面的区间可能突然跑到左边去，逻辑就全乱了。

---

### 易错点 3：排序比较器直接相减

你题解里写的是：

```java
a1.l - a2.l
```

这题数据范围下其实还比较安全，但更推荐写：

```java
Integer.compare(a.l, b.l)
```

因为这是一种更稳妥、更规范的写法，能避免一般情况下的整数溢出问题。

---

### 易错点 4：`res` 初始化问题

因为题目保证 `n >= 1`，所以可以直接：

```java
res = 1;
```

如果某些题没有保证 `n >= 1`，那就要额外判断空数组。

---

## 10. 这题还能怎么写

如果题目要求输出**合并后的所有区间**，那就不能只统计数量了，而要把每次确定下来的区间存起来，比如放进 `List<int[]> ans` 里。

这说明：

* **“统计个数”版**：只维护当前区间和计数器
* **“输出区间”版**：维护当前区间 + 结果列表

本质是一样的，都是区间合并模板。

---

## 11. 这题和哪些题是一类题

你学到这里，要建立这个联系：

### 看到以下特征，就要想到“排序 + 扫描”

* 给你很多区间
* 让你判断重叠、覆盖、合并
* 求合并后的段数
* 求被覆盖区间数
* 求最多不相交区间
* 求区间交集/并集相关信息

其中“**先排序**”几乎是所有区间题的起手动作。

---

## 12. 为什么最后能想到这种解法

这个部分我放在最后讲，因为它更像“题型识别能力”。

### 看到什么信号，应该想到区间合并？

#### 信号 1：输入是很多个 `[l, r]`

只要题目主体对象是“一堆区间”，就要马上想到：

* 排序
* 扫描
* 合并 / 选择 / 覆盖

---

#### 信号 2：题目说“有交集就合并”

这几乎是在直接提示你：

> 维护一个当前区间，遇到能接上的就扩展，接不上就开新区间。

---

#### 信号 3：只要求输出“合并后的个数”

这说明你甚至不需要真的把所有新区间都存下来，维护一个当前右端点就够了。

这就是为什么第二份代码能进一步简化。

---

## 13. 方法总结

你可以把这题记成一个固定模板：

### 区间合并模板

1. 按左端点排序
2. 维护当前合并区间右端点 `ed`
3. 若新区间左端点 `l > ed`，说明断开，答案加一
4. 否则更新 `ed = max(ed, r)`

---

## 14. 一句话带走

这题本质上就是：

> **把所有区间按左端点排好，然后贪心地维护当前能合并到哪里。**

---

你这份笔记所在目录里，“区间合并”本来就是基础算法第一讲的重要内容，和排序、二分、前缀和这些是同一层级的核心模板题。

下一步最适合做的是：我可以继续带你把这题扩展成“**输出合并后的所有区间**”版本，这样你会对模板理解得更扎实。

```java

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String[] s1 = bufferedReader.readLine().split(" ");
        int n = Integer.parseInt(s1[0]);
        /*
        把所有区间按左端点排好，然后贪心地维护当前能合并到哪里。
        
        保存所有区间输入
        按照每个区间左边界进行排序
        检查每个区间有没有连起来，统计区间的总数
        */
        String[] s2;
        int[][] add = new int[n][2];
        for (int i = 0; i < n; i++) {
            s2 = bufferedReader.readLine().split(" ");
            add[i][0] = Integer.parseInt(s2[0]);
            add[i][1] = Integer.parseInt(s2[1]);
        }
//        相当于匿名构造器，重写对比方法，推荐第二个写法
        Arrays.sort(add, 0, n, (x1, x2) -> x1[0] - x2[0]);

        int  count = 0;
        int right = add[0][1];
        for (int i = 0; i < n; i++) {
            if (i < n - 1) {
                if (right < add[i + 1][0]) count++;
                //这个地方更新right不能直接用后一个区间的right，一定要选最大的那一个right
                right = Math.max(add[i + 1][1], right);
            } else {
                count++;
            }
        }
        System.out.println(count);
    }
}

```