# AcWing 算法基础课 -- 基础算法

## AcWing 799. 最长连续不重复子序列

`难度：简单`

### 题目描述

给定一个长度为n的整数序列，请找出最长的不包含重复的数的连续区间，输出它的长度。

**输入格式**

第一行包含整数n。

第二行包含n个整数（均在0~100000范围内），表示整数序列。

**输出格式**

共一行，包含一个整数，表示最长的不包含重复的数的连续区间的长度。

```r
数据范围

1≤n≤100000

输入样例：

5
1 2 2 3 5

输出样例：

3
```

### Solution

```java
import java.util.*;
import java.io.*;

public class Main{
    public static void main(String[] args) throws IOException{
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));
        int n = Integer.parseInt(in.readLine());
        String[] s = in.readLine().split(" ");
        int[] a = new int[n + 10];
        for(int i = 0; i < n; i++){
            a[i] = Integer.parseInt(s[i]);
        }
        int[] q = new int[100010];
        int res = 0;
        // 区间 [i, j] 为不包含重复元素的区间
        // 遍历每一个元素，找到以该元素为结尾的最长不重复字符串
        // 当结尾元素往后移
        // 1. 如果出现了重复字符，开始元素往后移动
        // 2. 如果没有出现重复元素，开始元素不动
        // 可以发现开始元素的移动具有单调性
        // 这种场景适合双指针
        for(int i = 0, j = 0; j < n; j++){
            q[a[j]]++;
            while(i < j && q[a[j]] > 1){
                q[a[i]]--;
                i++;
            }
            res = Math.max(res, j - i + 1);
        }
        out.write(String.valueOf(res));
        out.flush();
    }
}
```
可以，我们把这题完整讲一遍。你现在这题属于一个非常经典的入门双指针题。

---

# 一、这题属于什么类型

这是：

**双指针 / 滑动窗口 / 最长连续不重复子数组**

题目本质是：

> 给你一个数组，求一个最长的连续区间，使得这个区间里的元素都不重复。

比如样例：

```text
1 2 2 3 5
```

最长不重复连续子数组是：

```text
2 3 5
```

长度是 `3`。

注意这里要求的是：

* **连续**
* **不重复**
* **最长**

只要同时看到这几个词，就要优先想到 **滑动窗口（双指针）**。

---

# 二、先说朴素做法，再说优化做法

## 1）朴素做法

最容易想到的是：

* 枚举每个左端点 `l`
* 再枚举每个右端点 `r`
* 检查区间 `[l, r]` 里有没有重复

这样做的问题是：

* 枚举区间本身就很多
* 每个区间再检查是否重复，又要花时间

复杂度通常会到 `O(n^2)` 甚至 `O(n^3)`，数据大时会超时。

---

## 2）优化做法

我们希望做到：

* 每个元素尽量只进窗口一次
* 每个元素尽量只出窗口一次

这就是双指针的核心思想。

用两个指针：

* `l`：窗口左端
* `r`：窗口右端

维护一个当前窗口 `[l, r]`，让这个窗口始终满足：

> 窗口内没有重复元素

一旦右边加入新元素后出现重复，就移动左指针，直到窗口重新合法。

---

# 三、核心思路

我们用一个计数数组 `res[]` 记录某个数字在当前窗口中出现了几次。

例如：

```java
res[x]
```

表示数字 `x` 在当前窗口里出现的次数。

然后流程是：

### 第一步

右指针 `r` 往右走，把 `arr[r]` 加入窗口：

```java
res[arr[r]]++;
```

### 第二步

如果加入后发现 `arr[r]` 重复了，也就是：

```java
res[arr[r]] > 1
```

说明当前窗口不合法。

### 第三步

这时不断右移左指针 `l`，把左边元素移出窗口，直到不重复：

```java
while (res[arr[r]] > 1) {
    res[arr[l]]--;
    l++;
}
```

### 第四步

窗口恢复合法后，这个窗口 `[l, r]` 就是一个不重复区间，可以更新答案：

```java
max = Math.max(max, r - l + 1);
```

---

# 四、为什么一定要用 `while`，不能只用 `if`

这是这题最重要的点之一。

假设数组是：

```text
1 2 3 2 2
```

当右指针走到最后一个 `2` 时，窗口里可能已经有多个 `2` 相关冲突了。

这时如果你只写：

```java
if (重复了) {
    l++;
}
```

只移动一次左指针，不一定能把重复消掉。

所以必须写成：

```java
while (重复了) {
    l++;
}
```

意思是：

> 只要窗口还不合法，就一直缩。

这是滑动窗口的标准写法。

---

# 五、你的代码在做什么

你现在这版代码核心是：

```java
int max = 0;
int r = 0, l = 0;
while (r < n){
    res[arr[r]]++;
    if (res[arr[r]] == 2){
        max = Math.max(max, r - l);
        while (res[arr[r]] == 2){
            res[arr[l]]--;
            l++;
        }
    }
    r++;
}
max = Math.max(max, r - l);
```

这版逻辑已经是对的。

它的意思是：

* `r` 每次向右扩展
* 先把 `arr[r]` 放入窗口
* 如果发现重复，就先记录重复前那个合法窗口长度 `r - l`
* 然后不断移动 `l`，直到窗口重新合法
* 最后在循环结束后，再补一次答案

这版能过题。

---

# 六、不过更推荐的标准写法

你现在的写法虽然对，但不是最标准、最好记的形式。

更推荐写成这样：

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(bufferedReader.readLine());

        int[] arr = new int[n];
        int[] res = new int[100001];

        String[] s = bufferedReader.readLine().split(" ");
        for (int i = 0; i < n; i++) {
            arr[i] = Integer.parseInt(s[i]);
        }

        int max = 0;
        int l = 0, r = 0;

        while (r < n) {
            res[arr[r]]++;

            while (res[arr[r]] > 1) {
                res[arr[l]]--;
                l++;
            }

            max = Math.max(max, r - l + 1);
            r++;
        }

        System.out.println(max);
    }
}
```

---

# 七、为什么推荐这个标准写法

因为它的逻辑特别顺：

### 每轮循环固定三步

第一步，加入右端点：

```java
res[arr[r]]++;
```

第二步，如果不合法，就一直缩左边：

```java
while (res[arr[r]] > 1) {
    res[arr[l]]--;
    l++;
}
```

第三步，窗口合法后更新答案：

```java
max = Math.max(max, r - l + 1);
```

这种写法的优点是：

* 不需要额外在重复时特判更新答案
* 不需要最后再补一次
* 模板性很强，以后很多题都能直接套

---

# 八、拿样例完整模拟一遍

样例：

```text
n = 5
arr = [1, 2, 2, 3, 5]
```

初始：

```text
l = 0, r = 0, max = 0
```

---

## 1）r = 0，加入 1

窗口变成：

```text
[1]
```

没有重复。

更新答案：

```text
max = 1
```

---

## 2）r = 1，加入 2

窗口变成：

```text
[1, 2]
```

没有重复。

更新答案：

```text
max = 2
```

---

## 3）r = 2，加入 2

窗口变成：

```text
[1, 2, 2]
```

发现 `2` 重复了。

开始移动左指针：

### 去掉 `arr[l] = 1`

窗口相当于：

```text
[2, 2]
```

还是重复。

### 再去掉 `arr[l] = 2`

窗口变成：

```text
[2]
```

现在合法了。

更新答案：

```text
max = 2
```

---

## 4）r = 3，加入 3

窗口变成：

```text
[2, 3]
```

合法。

更新答案：

```text
max = 2
```

---

## 5）r = 4，加入 5

窗口变成：

```text
[2, 3, 5]
```

合法。

更新答案：

```text
max = 3
```

最终答案是：

```text
3
```

---

# 九、代码里每个变量的含义

## `arr`

原数组。

## `res`

计数数组。

`res[x]` 表示数字 `x` 在当前窗口中出现了几次。

## `l`

左指针，表示当前窗口左边界。

## `r`

右指针，表示当前窗口右边界。

## `max`

记录最长合法窗口长度。

---

# 十、时间复杂度和空间复杂度

## 时间复杂度

`O(n)`

因为：

* `r` 从左到右走一遍
* `l` 也最多从左到右走一遍

每个元素最多被加入一次、移出一次。

---

## 空间复杂度

如果用计数数组：

```text
O(M)
```

`M` 是值域大小。

你这里写的是：

```java
int[] res = new int[100001];
```

说明你默认数组元素范围不超过 `100000`。

---

# 十一、Java 实现细节

## 1）输入第一行更简洁的写法

你原来写的是：

```java
String[] s1 = bufferedReader.readLine().split(" ");
int n = Integer.parseInt(s1[0]);
```

如果第一行只有一个整数，更建议直接写：

```java
int n = Integer.parseInt(bufferedReader.readLine());
```

更干净。

---

## 2）计数数组适合值域不大的情况

如果题目说：

* 数字范围很大
* 可能有负数

那么就不能直接用 `res[arr[r]]` 了，要改用 `HashMap<Integer, Integer>`。

但这题如果值域小，用数组最快。

---

## 3）窗口长度怎么算

当前窗口是 `[l, r]`，两端都包含，所以长度是：

```java
r - l + 1
```

这个很容易写错成 `r - l`。

---

# 十二、常见错误

这题非常容易犯下面几个错。

## 错误 1：重复后只缩一次

错误写法：

```java
if (res[arr[r]] > 1) {
    res[arr[l]]--;
    l++;
}
```

问题：不一定能把重复消掉。

必须用：

```java
while (res[arr[r]] > 1)
```

---

## 错误 2：窗口长度写错

窗口 `[l, r]` 的长度是：

```java
r - l + 1
```

不是 `r - l`。

---

## 错误 3：更新答案时机不对

应该在窗口合法后更新答案，而不是窗口还重复时更新。

---

## 错误 4：重复时把整个窗口清空

有些同学会一发现重复就把整个窗口重新来过，这样复杂度会变高，而且不是双指针的标准思想。

---

# 十三、这题为什么能想到双指针

这个放最后说，是为了帮你建立“题型识别能力”。

看到这题时，有三个很明显的信号。

## 信号 1：要求“连续子数组”

只要题目强调“连续”，就要优先想区间类方法：

* 双指针
* 前缀和
* 单调队列

这题显然适合双指针。

---

## 信号 2：要求“不重复”

“不重复”这个条件可以动态维护。

因为窗口右边加入一个数时，只需要知道：

* 这个数有没有重复出现

窗口左边移出一个数时，也能立刻更新状态。

这说明窗口状态可以被维护，所以适合滑动窗口。

---

## 信号 3：要求“最长”

一旦题目是：

* 最长合法连续区间
* 最短满足条件区间

这种就很像滑动窗口题。

统一套路就是：

> 右边扩展，左边按需收缩

---

# 十四、这题可以总结成什么模板

以后你看到：

* 最长不重复子数组
* 最长满足某种限制的连续区间
* 至多包含 k 种数
* 和不超过某个值的最长区间

很多都可以往这个模板上靠：

```java
for (int l = 0, r = 0; r < n; r++) {
    把 arr[r] 加入窗口;

    while (窗口不合法) {
        把 arr[l] 移出窗口;
        l++;
    }

    更新答案;
}
```

这就是双指针最核心的模板之一。

---

# 十五、你现在应该掌握的重点

这题你最需要真正记住的是这三句：

```java
res[arr[r]]++;

while (res[arr[r]] > 1) {
    res[arr[l]]--;
    l++;
}

max = Math.max(max, r - l + 1);
```

它分别对应：

* 扩展右边
* 修复窗口
* 更新答案

你把这三步吃透，这类题基本就会了。

---

接下来你要是愿意，我可以继续给你做两件事里的一个：
第一种，我把这题整理成一份你能直接背的“双指针模板笔记”；第二种，我给你出 2 道同类型练习题，帮你马上巩固。



```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String[] s1 = bufferedReader.readLine().split(" ");
        int n = Integer.parseInt(s1[0]);
        int[] arr = new int[n];
        int[] res = new int[100001];
        String[] s2 = bufferedReader.readLine().split(" ");
        for (int i = 0; i < n; i++) {
            arr[i] = Integer.parseInt(s2[i]);
        }
        int max = 0;
        int r = 0, l = 0;
        while (r < n){
            //双指针都是只右移
            res[arr[r]]++;
            //检查到重复就记录最大值
            if (res[arr[r]] == 2){
                //一定持续往右检查重复，因为不一定正好l和r重复
                max = Math.max(max, r - l);
                while (res[arr[r]] == 2){
                    res[arr[l]]--;
                    l++;
                }
            }
            r++;
        }
        max = Math.max(max, r - l);
        System.out.println(max);
    }
}
```