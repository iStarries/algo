# AcWing 算法基础课 -- 贪心专题

## AcWing 2816. 判断子序列 

`难度：简单`

### 题目描述

给定一个长度为 n 的整数序列 a1,a2,…,an 以及一个长度为 m 的整数序列 b1,b2,…,bm。

请你判断 a 序列是否为 b 序列的子序列。

子序列指序列的一部分项按原有次序排列而得的序列，例如序列 {a1,a3,a5} 是序列 {a1,a2,a3,a4,a5}的一个子序列。

**输入格式**

第一行包含两个整数 n,m。

第二行包含 n 个整数，表示 a1,a2,…,an。

第三行包含 m 个整数，表示 b1,b2,…,bm。

**输出格式**

如果 a 序列是 b 序列的子序列，输出一行 Yes。

否则，输出 No。

**数据范围**

$1≤n≤m≤10^5,$
$−10^9≤ai,bi≤10^9$

```
输入样例：

3 5
1 3 5
1 2 3 4 5

输出样例：

Yes
```

### Solution

```java
import java.util.*;
import java.io.*;

class Main{
    static int N = 100010;
    static int[] a = new int[N];
    static int[] b = new int[N];
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] s = br.readLine().split(" ");
        int n = Integer.parseInt(s[0]), m = Integer.parseInt(s[1]);
        s = br.readLine().split(" ");
        for(int i = 1; i <= n; i++) a[i] = Integer.parseInt(s[i - 1]);
        s = br.readLine().split(" ");
        for(int i = 1; i <= m; i++) b[i] = Integer.parseInt(s[i - 1]);
        int i = 1, j = 1;
        // a 是否是 b 的子序列
        // 从 b 里按顺序找 a 的每一个字符
        while(i <= n && j <= m){
            // 找到了 i 往前走
            if(a[i] == b[j]) i++;
            j++;
        }
        if(i == n + 1) System.out.println("Yes");
        else System.out.println("No");
    }
}
```

双指针，感觉这样写会看着舒服些

```java
import java.util.*;
import java.io.*;

class Main{
    static final int N = 100010;
    static int[] a = new int[N];
    static int[] b = new int[N];
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] s = br.readLine().split(" ");
        int n = Integer.parseInt(s[0]);
        int m = Integer.parseInt(s[1]);
        s = br.readLine().split(" ");
        for(int i = 0; i < n; i++) a[i] = Integer.parseInt(s[i]);
        s = br.readLine().split(" ");
        for(int i = 0; i < m; i++) b[i] = Integer.parseInt(s[i]);
        int i = 0, j = 0;
        for(; i < n; i++, j++){
            while(j < m && a[i] != b[j]) j++;
            if(j == m){
                break;
            }
        }
        if(i == n) System.out.println("Yes");
        else System.out.println("No");
    }
}
```
我直接按照这份题目与题解来讲。题目文件见：
你的学习范围里，这题属于**第一讲 基础算法里的双指针算法**。学习目录文件见：

## 1. 这题属于什么题型，核心考点是什么

这题是一个很典型的：

* **双指针**
* **子序列判断**

核心任务是判断：
`a` 中的元素，能不能按照原来的顺序，在 `b` 中依次找到。

注意这里要求的是 **顺序不能变**，但**不要求连续**。
这正是“子序列”和“子串/连续子段”最关键的区别。

例如：

* `1 3 5` 是 `1 2 3 4 5` 的子序列
* 因为可以按顺序挑出第 1、3、5 个位置

---

## 2. 先讲最直接的思路

最朴素的想法是：

对于 `a[0]`，去 `b` 里从前往后找；
找到后，再从它后面继续找 `a[1]`；
再继续找 `a[2]`……

你会发现，这其实已经天然形成了两个“位置指针”：

* 一个指向 `a` 当前要匹配的元素
* 一个指向 `b` 当前扫描到的位置

所以这题最自然的做法就是 **双指针**。

---

## 3. 正确做法：双指针

设：

* `i` 指向序列 `a`
* `j` 指向序列 `b`

含义是：

* `i` 表示当前要在 `b` 中寻找 `a[i]`
* `j` 表示当前扫描到 `b[j]`

每次比较：

* 如果 `a[i] == b[j]`，说明当前这个元素匹配成功，`i++`
* 不管是否匹配，`j` 都要继续往后走

因为 `b` 是拿来“扫一遍找答案”的。

直到：

* 如果 `i` 走到 `n`，说明 `a` 全部匹配完了，输出 `Yes`
* 如果 `j` 先走到 `m`，但 `i` 还没结束，说明匹配失败，输出 `No`

---

## 4. 为什么这样做是对的

因为题目只要求“按顺序出现”，不要求连续。

所以对于 `a` 中每个元素，我们只需要在 `b` 中从当前位置往后找第一个能匹配的位置即可。
一旦找到了，后面的匹配就只能发生在这个位置之后。

这正符合双指针的推进方式：

* `j` 一路向后，不回头
* `i` 只有在匹配成功时才向后

这样既不会漏，也不会重复找。

---

## 5. 你给的第一份代码讲解

你第一份代码是：

```java
import java.util.*;
import java.io.*;

class Main{
    static int N = 100010;
    static int[] a = new int[N];
    static int[] b = new int[N];
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] s = br.readLine().split(" ");
        int n = Integer.parseInt(s[0]), m = Integer.parseInt(s[1]);
        s = br.readLine().split(" ");
        for(int i = 1; i <= n; i++) a[i] = Integer.parseInt(s[i - 1]);
        s = br.readLine().split(" ");
        for(int i = 1; i <= m; i++) b[i] = Integer.parseInt(s[i - 1]);
        int i = 1, j = 1;
        // a 是否是 b 的子序列
        // 从 b 里按顺序找 a 的每一个字符
        while(i <= n && j <= m){
            // 找到了 i 往前走
            if(a[i] == b[j]) i++;
            j++;
        }
        if(i == n + 1) System.out.println("Yes");
        else System.out.println("No");
    }
}
```

这份代码是**完全正确的**。

### 这份代码的优点

#### 1）逻辑非常标准

就是最经典的双指针模板：

```java
while(i <= n && j <= m){
    if(a[i] == b[j]) i++;
    j++;
}
```

含义特别清楚：

* 如果匹配，`a` 的指针前进
* 无论是否匹配，`b` 的指针都前进

#### 2）循环结束后的判断也正确

```java
if(i == n + 1) System.out.println("Yes");
```

因为你这里数组是 **从 1 开始存** 的：

* `a[1] ~ a[n]` 是有效元素
* 如果全部匹配完，`i` 会被推进到 `n + 1`

所以这里判断 `i == n + 1` 很准确。

---

## 6. 你给的第二份代码讲解

第二份代码是：

```java
import java.util.*;
import java.io.*;

class Main{
    static final int N = 100010;
    static int[] a = new int[N];
    static int[] b = new int[N];
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] s = br.readLine().split(" ");
        int n = Integer.parseInt(s[0]);
        int m = Integer.parseInt(s[1]);
        s = br.readLine().split(" ");
        for(int i = 0; i < n; i++) a[i] = Integer.parseInt(s[i]);
        s = br.readLine().split(" ");
        for(int i = 0; i < m; i++) b[i] = Integer.parseInt(s[i]);
        int i = 0, j = 0;
        for(; i < n; i++, j++){
            while(j < m && a[i] != b[j]) j++;
            if(j == m){
                break;
            }
        }
        if(i == n) System.out.println("Yes");
        else System.out.println("No");
    }
}
```

这份代码也是**正确的**。

不过它的写法相对第一种更绕一点，对初学者来说不如第一种直观。

---

## 7. 第二份代码具体是怎么工作的

这里数组改成了 **从 0 开始存储**：

* `a[0] ~ a[n-1]`
* `b[0] ~ b[m-1]`

主循环：

```java
for(; i < n; i++, j++){
    while(j < m && a[i] != b[j]) j++;
    if(j == m){
        break;
    }
}
```

### 它的意思是：

对于 `a[i]`：

* 先在 `b` 中从当前 `j` 开始一直找
* `while(j < m && a[i] != b[j]) j++;`

如果最后：

* `j == m`，说明已经扫完 `b` 也没找到，失败
* 否则说明找到了 `a[i] == b[j]`

而 `for` 循环结尾还有一个：

```java
i++, j++
```

这表示：

* 当前 `a[i]` 已经匹配完成，去匹配下一个 `a`
* 同时 `b` 也从当前位置后面继续找

所以逻辑上也成立。

---

## 8. 两种写法对比

### 第一种写法

```java
while(i <= n && j <= m){
    if(a[i] == b[j]) i++;
    j++;
}
```

优点：

* 简洁
* 语义最直接
* 容易记模板
* 最适合初学者

### 第二种写法

```java
for(; i < n; i++, j++){
    while(j < m && a[i] != b[j]) j++;
    if(j == m) break;
}
```

优点：

* 更强调“对 `a` 的每个元素，在 `b` 中往后找”
* 从某种角度看也很自然

缺点：

* `j` 既在 `while` 里动，又在 `for` 结尾动
* 更容易把自己绕晕
* 边界更不容易一下看清

所以如果是考试、面试、刷题，我更建议你优先掌握**第一种标准模板**。

---

## 9. 朴素做法和优化做法对比

### 朴素理解

你可以把它想成“暴力地一个一个找”。

但这个“暴力”并不是每次都从头找，而是**接着上次的位置继续找**。

### 优化本质

真正的优化点就在于：

* `b` 中的位置 `j` **不会回退**
* 每个元素最多只看一遍

所以总复杂度不是两层循环的 `O(nm)`，而是：

* **时间复杂度：O(n + m)**
* **空间复杂度：O(1)**（如果不算输入数组）

这就是双指针常见的优化思想：
**两个指针整体各走一遍，而不是来回重复。**

---

## 10. Java 实现细节

这题虽然简单，但 Java 有几个细节值得你注意。

### 1）下标统一

你第一份代码是 **1 下标**，第二份代码是 **0 下标**。
两种都行，但一定要统一，别混着写。

例如：

* 如果从 1 开始存，就判断 `i == n + 1`
* 如果从 0 开始存，就判断 `i == n`

### 2）输入范围

题目数据 `n, m <= 10^5`，用 `BufferedReader` 没问题。

### 3）split(" ") 的习惯

这题数据正常，`split(" ")` 可以过。
不过更稳一点的写法通常是用 `StringTokenizer`，因为效率更高，也不容易受多余空格影响。

例如可以写成：

```java
BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
StringTokenizer st = new StringTokenizer(br.readLine());
int n = Integer.parseInt(st.nextToken());
int m = Integer.parseInt(st.nextToken());
```

---

## 11. 给你一个更推荐的 Java 标准写法

我建议你记下面这个版本，最稳、最清楚：

```java
import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        int[] a = new int[n];
        int[] b = new int[m];

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            a[i] = Integer.parseInt(st.nextToken());
        }

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < m; i++) {
            b[i] = Integer.parseInt(st.nextToken());
        }

        int i = 0, j = 0;
        while (i < n && j < m) {
            if (a[i] == b[j]) {
                i++;
            }
            j++;
        }

        if (i == n) {
            System.out.println("Yes");
        } else {
            System.out.println("No");
        }
    }
}
```

---

## 12. 边界情况要会自己检查

这题要特别注意这几个边界：

### 情况 1：完全匹配成功

例如：

```text
a = 1 3 5
b = 1 2 3 4 5
```

最后 `i` 会走到末尾，输出 `Yes`

### 情况 2：中途找不到

例如：

```text
a = 1 4 3
b = 1 2 3 4 5
```

注意顺序不对。
虽然 `4` 和 `3` 都在 `b` 中，但 `3` 出现在 `4` 前面，不能回头找，所以输出 `No`

### 情况 3：相同元素重复出现

例如：

```text
a = 1 2 2
b = 1 2 3 2
```

是 `Yes`，因为可以依次匹配到两个 `2`

### 情况 4：长度相等

如果 `n == m`，那本质上就是判断两个序列是否完全相同，因为已经没有“跳过”的空间了。

---

## 13. 为什么最后能想到这种解法

这个部分最重要，是帮助你建立“题型识别能力”。

以后看到下面这些信号，就要往**双指针 / 子序列匹配**上想：

### 信号 1：两个序列，要求按顺序匹配

关键词常见是：

* 是否是子序列
* 按顺序出现
* 不要求连续
* 保持相对顺序

只要看到这些，就很容易联想到：
**一个指针扫模式串/目标序列，一个指针扫原序列。**

### 信号 2：只关心顺序，不关心具体跨过多少元素

这说明你不需要复杂数据结构，不需要回溯，也不需要 DP。
因为你只是在一条线上一直往后找。

### 信号 3：元素一旦错过就不能回头

这正是双指针最强的场景。
“单调向前推进”通常就是双指针的重要触发条件。

---

## 14. 方法迁移：这题学完能举一反三到哪里

这类题你以后会经常遇到，比如：

* 判断字符串 `s` 是否是 `t` 的子序列
* 两个有序数组的合并扫描
* 两个序列按条件匹配
* 去重、滑动窗口、区间维护等双指针题

虽然形式不同，但核心都是：

* 两个指针
* 按某种规则向前推进
* 不回头
* 线性复杂度

所以这题虽然简单，但它是双指针入门题，非常重要。

---

## 15. 这题你现在应该记住的结论

这题本质就是一句话：

**用 `j` 扫 `b`，按顺序去匹配 `a` 的每个元素；匹配成功就让 `i` 前进，最后看 `i` 是否走完整个 `a`。**

标准模板记成：

```java
int i = 0, j = 0;
while (i < n && j < m) {
    if (a[i] == b[j]) i++;
    j++;
}
System.out.println(i == n ? "Yes" : "No");
```

如果你愿意，我下一步可以继续带你做这题的“手动模拟一遍指针移动过程”，让你彻底理解为什么 `j` 只前进不回头。


```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class Main {
    /*
    输入n m
    输出序列a 和 b
    双指针遍历：
        从两个序列的开头开始，长序列中找当前子序列元素，
            找到了两个指针都后移
            没找到就长序列指针后移
            找到子序列的结尾就成功，输出找到了
            一直都没成功，就循环到长序列遍历结束，输出没找到

     */
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String[] s1 = bufferedReader.readLine().split(" ");
        int n = Integer.parseInt(s1[0]);
        int m = Integer.parseInt(s1[1]);
        int[] a = new int[n];
        int[] b = new int[m];

        s1 = bufferedReader.readLine().split(" ");
        for (int i = 0; i < n; i++) {
            a[i] = Integer.parseInt(s1[i]);
        }
        s1 = bufferedReader.readLine().split(" ");
        for (int i = 0; i < m; i++) {
            b[i] = Integer.parseInt(s1[i]);
        }
        int l = 0, r = 0;
        for (; r < m; r++){
            if(a[l] == b[r]){
                l++;
                if(l == n){
                    System.out.println("Yes");
                    return;
                }
            }
        }
        System.out.println("No");
    }
}
```
