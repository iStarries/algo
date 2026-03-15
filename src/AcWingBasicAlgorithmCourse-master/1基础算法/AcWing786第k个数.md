# AcWing 算法基础课 -- 基础算法

## AcWing 786. 第k个数 

`难度：简单`

### 题目描述

给定一个长度为n的整数数列，以及一个整数k，请用快速选择算法求出数列从小到大排序后的第k个数。

**输入格式**

输入共两行，第一行包含两个整数 n 和 k。

第二行包含 n 个整数（所有整数均在$1~10^9$范围内），表示整个数列。

**输出格式**

输出一个整数，表示数列的第k小数。

**数据范围**

1 ≤ n ≤ 100000
1 ≤ k ≤ n

**输入样例**：

```
5 3
2 4 1 5 3
```

**输出样例**：

```
3
```

### Solution

```java 
import java.util.*;
import java.io.*;

class Main{
    static int N = 100010;
    static int[] q = new int[N];
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] s = br.readLine().split(" ");
        int n = Integer.parseInt(s[0]);
        int k = Integer.parseInt(s[1]);
        s = br.readLine().split(" ");
        for(int i = 0; i < n; i++){
            q[i] = Integer.parseInt(s[i]);
        }
        quickSort(0, n - 1, k);
        System.out.println(q[k - 1]);
    }
    public static void quickSort(int l, int r, int k){
        if(l >= r) return;
        int i = l - 1, j = r + 1, x = q[l + r >> 1];
        while(i < j){
            do i++; while(q[i] < x);
            do j--; while(q[j] > x);
            if(i < j){
                int t = q[i];
                q[i] = q[j];
                q[j] = t;
            }

        }
        // 第 k 数字的下标为 k - 1
        // 如果第 k 个数字在 j 的右边就递归右边
        // 否则递归左边
        // 这样可以把时间复杂度优化为  O(n)
        if(k - 1 > j) quickSort(j + 1, r, k);
        else quickSort(l, j, k);
    }
}
```

这题是**快速排序的变形题**，核心不是“把整个数组排好”，而是：

**只找第 k 小的数。**

它用到的算法叫：

**快速选择算法**，也可以理解成“只做必要递归的快速排序”。题目、样例和代码都在你刚上传的这一题里。

---

# 一、先说这题和上一题的关系

你上一题学的是：

**快速排序**
目标：把整个数组从小到大排好。

这题是：

**第 k 个数**
目标：只找到排完序后第 `k` 小的数，不要求整个数组都排好。

比如：

```text
数组：2 4 1 5 3
k = 3
```

从小到大排序后是：

```text
1 2 3 4 5
```

第 3 个数是：

```text
3
```

所以答案是 `3`。

---

# 二、这题为什么不能直接“完全排序”再取第 k 个

当然可以这么做：

1. 先把整个数组排好
2. 输出 `q[k - 1]`

这样思路没错，但这题题解故意不用完整快排，而是用更高效的方法。

因为：

* 完整排序会处理很多“其实不需要处理”的部分
* 我们只关心第 `k` 小那个位置
* 没必要把所有元素都完全排好

这就是这题想教你的地方：

**只递归第 k 个数所在的那一边。**

这样平均时间复杂度可以做到 **O(n)**。题解注释里也明确写了这一点。

---

# 三、这题的核心思想

你先记一句最重要的话：

**每次划分后，我们能知道第 k 小的数是在左边还是右边，只需要继续找那一边。**

这和快排的区别是：

* 快排：左右两边都递归
* 快速选择：只递归一边

这是本题最核心的区别。

---

# 四、先看主函数 `main`

代码是：

```java
public static void main(String[] args) throws IOException{
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    String[] s = br.readLine().split(" ");
    int n = Integer.parseInt(s[0]);
    int k = Integer.parseInt(s[1]);
    s = br.readLine().split(" ");
    for(int i = 0; i < n; i++){
        q[i] = Integer.parseInt(s[i]);
    }
    quickSort(0, n - 1, k);
    System.out.println(q[k - 1]);
}
```

这部分你已经有基础了，我这里重点指出和上一题不同的地方。

---

## 1）读入第一行

```java
String[] s = br.readLine().split(" ");
int n = Integer.parseInt(s[0]);
int k = Integer.parseInt(s[1]);
```

因为这一题第一行输入的是两个数：

* `n`：数组长度
* `k`：要找第几个数

比如样例：

```text
5 3
```

那么：

* `n = 5`
* `k = 3`

---

## 2）读入数组

```java
s = br.readLine().split(" ");
for(int i = 0; i < n; i++){
    q[i] = Integer.parseInt(s[i]);
}
```

和上一题类似，就是把第二行数字读到数组 `q` 里。

---

## 3）调用函数

```java
quickSort(0, n - 1, k);
```

这里和上一题最大的不同是：

这次函数多传了一个参数 `k`，因为我们要找的是**第 k 小的数**。

---

## 4）输出答案

```java
System.out.println(q[k - 1]);
```

为什么是 `k - 1`？

因为题目说的是“第 k 个数”，这是**第几个**，从 1 开始数。

而数组下标是从 0 开始。

所以：

* 第 1 个数 -> 下标 0
* 第 2 个数 -> 下标 1
* 第 k 个数 -> 下标 `k - 1`

这一点题解里也写了注释。

---

# 五、先别急着看细节，先看整体算法轮廓

这题的函数叫 `quickSort`，但它其实做的是“快速选择”。

函数结构是：

```java
public static void quickSort(int l, int r, int k){
    if(l >= r) return;

    // 1. 划分区间
    // 2. 判断第 k 小的数在哪边
    // 3. 只递归那一边
}
```

所以你脑子里先立一个框架：

```text
1. 选基准
2. 双指针划分
3. 看 k 在左边还是右边
4. 只继续处理那一边
```

---

# 六、核心函数逐行讲解

代码如下：

```java
public static void quickSort(int l, int r, int k){
    if(l >= r) return;
    int i = l - 1, j = r + 1, x = q[l + r >> 1];
    while(i < j){
        do i++; while(q[i] < x);
        do j--; while(q[j] > x);
        if(i < j){
            int t = q[i];
            q[i] = q[j];
            q[j] = t;
        }

    }
    if(k - 1 > j) quickSort(j + 1, r, k);
    else quickSort(l, j, k);
}
```

---

## 1）终止条件

```java
if(l >= r) return;
```

和快速排序一样。

意思是：

* 当前区间没有数，或者只有一个数
* 不需要继续处理了

---

## 2）定义 `i`、`j`、`x`

```java
int i = l - 1, j = r + 1, x = q[l + r >> 1];
```

这和上一题的快速排序模板完全一样。

* `x`：选中间位置的数作为基准
* `i`：从左往右找
* `j`：从右往左找

---

## 3）划分过程

```java
while(i < j){
    do i++; while(q[i] < x);
    do j--; while(q[j] > x);
    if(i < j){
        int t = q[i];
        q[i] = q[j];
        q[j] = t;
    }
}
```

这一段你已经在上一题学过了，它的作用是：

**把当前区间分成左右两部分：**

* 左边尽量是小的数
* 右边尽量是大的数

循环结束后，可以认为：

* `l ~ j` 这一段是左边部分
* `j + 1 ~ r` 这一段是右边部分

这里不要求左右内部完全有序，只要求完成“划分”。

---

# 七、这题最关键的一句：为什么只递归一边

代码是：

```java
if(k - 1 > j) quickSort(j + 1, r, k);
else quickSort(l, j, k);
```

这就是整题灵魂。

先把它翻译成中文：

* 如果第 `k` 小的数的下标在 `j` 的右边，就去右半部分找
* 否则，就去左半部分找

题解里也写了这层意思。

---

# 八、为什么是比较 `k - 1` 和 `j`

这里很多初学者会卡。

---

## 先说 `k - 1`

我们要找“第 k 小的数”。

如果整个数组最终排好序，那么这个数会在下标：

```java
k - 1
```

因为数组下标从 0 开始。

---

## 再说 `j`

划分完成后，当前区间被分成：

* 左边：`l ~ j`
* 右边：`j + 1 ~ r`

所以：

* 如果 `k - 1 <= j`，说明目标下标在左边
* 如果 `k - 1 > j`，说明目标下标在右边

于是代码就写成：

```java
if(k - 1 > j) quickSort(j + 1, r, k);
else quickSort(l, j, k);
```

---

# 九、用样例完整模拟一遍

样例：

```text
5 3
2 4 1 5 3
```

也就是：

* `n = 5`
* `k = 3`
* 数组 `q = [2, 4, 1, 5, 3]`

我们要找第 3 小的数。

也就是说，要找排序后下标为：

```java
k - 1 = 2
```

的元素。

---

## 第一次划分

当前区间：

```text
l = 0, r = 4
```

基准：

```java
x = q[(0 + 4) / 2] = q[2] = 1
```

对 `[2, 4, 1, 5, 3]` 划分后，`1` 会被分到左边那块，其他较大的数在右边。

划分结束后，左边区间会很小，右边区间会比较大。关键不是数组最终长什么样，而是：

我们只需要判断目标下标 `2` 在哪边。

如果第一次划分后 `j = 0`，那么：

* 左边区间：`0 ~ 0`
* 右边区间：`1 ~ 4`

此时：

```java
k - 1 = 2 > 0
```

说明第 3 小的数不在左边，在右边。

所以只递归右边：

```java
quickSort(1, 4, 3);
```

---

## 第二次划分

现在只看右边区间。

继续做同样的事：

* 选基准
* 划分
* 判断 `k - 1` 在左还是右

如果某次划分后 `j = 2`，那么：

* 左边区间：`1 ~ 2`
* 右边区间：`3 ~ 4`

此时：

```java
k - 1 = 2
```

落在左边，所以继续递归左边。

最后递归到足够小的区间时，第 `k - 1` 位置上的数就会是答案。

最终输出：

```text
3
```

---

# 十、这题和快速排序的本质区别

这一点你必须真正记住。

---

## 快速排序

```java
quickSort(l, j);
quickSort(j + 1, r);
```

左右都递归。

因为目标是：整个数组完全有序。

---

## 快速选择

```java
if(k - 1 > j) quickSort(j + 1, r, k);
else quickSort(l, j, k);
```

只递归一边。

因为目标只是：找到第 `k` 小的数。

---

## 一句话总结

**快排排全部，快选找一个。**

这是这题最重要的认识。

---

# 十一、为什么时间复杂度能优化到 O(n)

题解注释里说：

```text
这样可以把时间复杂度优化为 O(n)
```

这里你先理解成“平均情况下 O(n)”更准确。

原因是：

* 每次划分本身大约要扫当前区间一遍
* 但不像快排那样左右都递归
* 它只进入一边

所以平均来看，总工作量接近：

```text
n + n/2 + n/4 + ...
```

这是一个等比数列，和大约是 `2n`

所以平均复杂度是 **O(n)**。

你现在不需要严格证明，先有这个感觉就够了。

---

# 十二、这道题最值得你掌握的 5 个点

## 1. 第 k 小 -> 对应下标是 `k - 1`

这个转换必须熟练。

---

## 2. 划分过程和快速排序一样

`i、j、x` 的写法和上一题一模一样。

---

## 3. 划分后区间是 `l ~ j` 和 `j + 1 ~ r`

这一点和上一题一致。

---

## 4. 只递归目标所在的一边

这是本题核心。

---

## 5. 最后直接输出 `q[k - 1]`

因为处理过程中，目标位置最终会被“逼近”到正确结果。

---

# 十三、你现在最容易混淆的地方

## 1）这题不是“先排完再找”

虽然函数名字叫 `quickSort`，但它做的不是完整排序。

---

## 2）为什么最后能直接输出 `q[k - 1]`

因为递归一直只保留包含目标下标的区间，最终这个位置的值会成为正确答案。

---

## 3）为什么没有同时递归左右两边

因为另外一边不可能包含第 `k` 小的数，处理它是浪费。

---

# 十四、给你一版适合初学者理解的注释版

```java
import java.util.*;
import java.io.*;

class Main{
    static int N = 100010;
    static int[] q = new int[N];

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 读入 n 和 k
        String[] s = br.readLine().split(" ");
        int n = Integer.parseInt(s[0]);
        int k = Integer.parseInt(s[1]);

        // 读入数组
        s = br.readLine().split(" ");
        for(int i = 0; i < n; i++){
            q[i] = Integer.parseInt(s[i]);
        }

        // 只查找第 k 小的数
        quickSort(0, n - 1, k);

        // 第 k 小的数对应下标 k - 1
        System.out.println(q[k - 1]);
    }

    public static void quickSort(int l, int r, int k){
        // 区间长度为 0 或 1，直接返回
        if(l >= r) return;

        int i = l - 1, j = r + 1;
        int x = q[l + r >> 1];

        // 按照 x 进行划分
        while(i < j){
            do i++; while(q[i] < x);
            do j--; while(q[j] > x);

            if(i < j){
                int t = q[i];
                q[i] = q[j];
                q[j] = t;
            }
        }

        // 如果目标下标在右边，就递归右边
        if(k - 1 > j) quickSort(j + 1, r, k);
        // 否则递归左边
        else quickSort(l, j, k);
    }
}
```

---

# 十五、你这题学完后，应该能说出这句话

你最好自己复述一遍下面这句话：

**快速选择 = 用快排的划分方法，但每次只进入包含第 k 小元素的那一边。**

如果这句话你能说顺，这题核心就抓住了。

---

# 十六、这一题之后你最该练什么

这题最适合练的不是背代码，而是回答下面 3 个问题：

1. 为什么找第 `k` 个数要看 `k - 1`？
2. 为什么划分后只递归一边？
3. 为什么这题比完整快排更快？

你能把这 3 个问题讲清楚，这题就算真的理解了。

---

接下来最合适的是：我把这题和上一题做一个**并排对比讲解**，让你彻底看清“快速排序”和“快速选择”到底差在哪里。



```java

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
public class Main{
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String[] s = bufferedReader.readLine().split(" ");
        int n = Integer.parseInt(s[0]);
        int k = Integer.parseInt(s[1]);
        s = bufferedReader.readLine().split(" ");
        int[] q = new int[n];
        for (int i = 0; i < s.length; i++) {
            q[i] = Integer.parseInt(s[i]);
        }
        qs(q, 0, q.length - 1, k);
        System.out.println(q[k - 1]);

    }

    public static void qs(int q[], int l, int r, int k) {
        if (l >= r) return;
        int i = l - 1;
        int j = r + 1;
        int x = q[l + r >> 1];
        while (i < j) {
            do i++; while (q[i] < x);
            do j--; while (q[j] > x);
            if (i < j) {
                int t = q[i];
                q[i] = q[j];
                q[j] = t;
            }
        }
        if (k - 1 > j) {
            qs(q, j + 1, r, k);
        }else{
            qs(q, l, j, k);
        }
    }
}


```

