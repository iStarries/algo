# AcWing 算法基础课 -- 基础算法

## AcWing 787. 归并排序

`难度：简单`

### 题目描述

给定你一个长度为n的整数数列。

请你使用快速排序对这个数列按照从小到大进行排序。

并将排好序的数列按顺序输出。

**输入格式**

输入共两行，第一行包含整数 n。

第二行包含 n 个整数（所有整数均在$1~10^9$范围内），表示整个数列。

**输出格式**

输出共一行，包含 n 个整数，表示排好序的数列。

**数据范围**

1 ≤ n ≤ 100000

**输入样例**：

```
5
3 1 2 4 5
```

**输出样例**：

```
1 2 3 4 5
```

### Solution

```java
import java.util.*;
import java.io.*;

public class Main{
    public static void main(String[] args) throws IOException{
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(in.readLine());
        int[] q = new int[n];
        int[] tmp = new int[n];
        String[] s = in.readLine().split(" ");
        for(int i = 0; i < n; i++){
            q[i] = Integer.parseInt(s[i]);
        }
        mergeSort(q, 0, n - 1, tmp);
        for(int num : q){
            System.out.print(num + " ");
        }
    }
    
    public static void mergeSort(int[] q, int l, int r, int[] tmp){
        if(l >= r) return;
        int mid = l + r >> 1;
        mergeSort(q, l, mid, tmp);
        mergeSort(q, mid + 1, r, tmp);
        int k = 0, i = l, j = mid + 1;
        while(i <= mid && j <= r){
            if(q[i] <= q[j]) tmp[k++] = q[i++];
            else tmp[k++] = q[j++];
        }
        while(i <= mid) tmp[k++] = q[i++];
        while(j <= r) tmp[k++] = q[j++];
        for(i = l, j = 0; i <= r; i++, j++) q[i] = tmp[j];
    }
}
```
这道题是**归并排序**。

先纠正一个小地方：你上传的题面标题是 **AcWing 787. 归并排序**，但题目描述里有一句“请你使用快速排序”，这里应当是题面整理时写错了；从标题、题解代码和做法来看，这题讲的明显是**归并排序**。代码里调用的也是 `mergeSort(...)`。

---

# 一、这题要学什么

这题和上一题快排一样，也是“排序题”，但它背后的思想不同：

* 快速排序：先划分，再递归
* 归并排序：先递归排好左右两边，再把两段**合并**

所以你先记一句核心话：

**归并排序 = 分成两半分别排好 + 把两个有序段合并成一个有序段。**

---

# 二、先用最直观的话理解“归并”

假设现在有两段已经排好序的数组：

```text
左边：1 3 5
右边：2 4 6
```

你怎么把它们合成一个整体有序的数组？

方法很自然：

* 比较 `1` 和 `2`，小的是 `1`
* 再比较 `3` 和 `2`，小的是 `2`
* 再比较 `3` 和 `4`，小的是 `3`
* …

最后得到：

```text
1 2 3 4 5 6
```

这一步就叫**归并**。

所以归并排序本质上是：

1. 先把左边排好
2. 先把右边排好
3. 再把左右两段合并

---

# 三、为什么这题适合用归并排序

因为题目要求：

* 输入一个整数序列
* 从小到大排序
* 输出排序结果

题解采用的是标准的归并排序模板。你上传的代码里主函数调用的是：

```java
mergeSort(q, 0, n - 1, tmp);
```

说明这题就是在练归并排序模板。

---

# 四、先看 `main` 在做什么

你这次的 `main` 是：

```java
public static void main(String[] args) throws IOException{
    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    int n = Integer.parseInt(in.readLine());
    int[] q = new int[n];
    int[] tmp = new int[n];
    String[] s = in.readLine().split(" ");
    for(int i = 0; i < n; i++){
        q[i] = Integer.parseInt(s[i]);
    }
    mergeSort(q, 0, n - 1, tmp);
    for(int num : q){
        System.out.print(num + " ");
    }
}
```

它的流程还是老三步：

1. 读入数据
2. 调用 `mergeSort` 排序
3. 输出数组

和前两题相比，这里最值得注意的是这一句：

```java
int[] tmp = new int[n];
```

这个 `tmp` 是**辅助数组**，专门给归并时临时存放结果用的。

你可以先把它理解成：

**归并的时候，需要一个临时仓库，把合并后的有序结果先放进去。**

---

# 五、核心函数整体结构

代码是：

```java
public static void mergeSort(int[] q, int l, int r, int[] tmp){
    if(l >= r) return;
    int mid = l + r >> 1;
    mergeSort(q, l, mid, tmp);
    mergeSort(q, mid + 1, r, tmp);
    int k = 0, i = l, j = mid + 1;
    while(i <= mid && j <= r){
        if(q[i] <= q[j]) tmp[k++] = q[i++];
        else tmp[k++] = q[j++];
    }
    while(i <= mid) tmp[k++] = q[i++];
    while(j <= r) tmp[k++] = q[j++];
    for(i = l, j = 0; i <= r; i++, j++) q[i] = tmp[j];
}
```

你先不要急着记细节，先记这 5 步：

```text
1. 如果区间长度 <= 1，直接返回
2. 找中点
3. 递归排左半边
4. 递归排右半边
5. 合并两个有序段
```

这就是归并排序骨架。

---

# 六、逐行讲 `mergeSort`

## 1）函数定义

```java
public static void mergeSort(int[] q, int l, int r, int[] tmp)
```

参数含义：

* `q`：原数组
* `l`：当前区间左端点
* `r`：当前区间右端点
* `tmp`：辅助数组，归并时临时使用

---

## 2）递归终止条件

```java
if(l >= r) return;
```

意思是：

* 当前区间没有元素，或者只有一个元素
* 它已经天然有序
* 不需要继续分了

这和快排很像。

---

## 3）求中点

```java
int mid = l + r >> 1;
```

就是：

```java
int mid = (l + r) / 2;
```

表示把当前区间 `[l, r]` 分成两半：

* 左边：`[l, mid]`
* 右边：`[mid + 1, r]`

---

## 4）递归排序左右两半

```java
mergeSort(q, l, mid, tmp);
mergeSort(q, mid + 1, r, tmp);
```

这两句很重要。

意思是：

* 先把左半边排好
* 再把右半边排好

注意，这里和快排不一样。

快排是“先划分，再递归”。

归并是“先递归排好左右，再合并”。

---

# 七、最核心的部分：合并两个有序段

这段是归并排序真正的灵魂：

```java
int k = 0, i = l, j = mid + 1;
while(i <= mid && j <= r){
    if(q[i] <= q[j]) tmp[k++] = q[i++];
    else tmp[k++] = q[j++];
}
while(i <= mid) tmp[k++] = q[i++];
while(j <= r) tmp[k++] = q[j++];
for(i = l, j = 0; i <= r; i++, j++) q[i] = tmp[j];
```

我们拆开讲。

---

## 1）先定义三个指针

```java
int k = 0, i = l, j = mid + 1;
```

含义：

* `i = l`：指向左半段开头
* `j = mid + 1`：指向右半段开头
* `k = 0`：指向 `tmp` 当前该放的位置

你可以这样想：

* `i` 在左边那段往后走
* `j` 在右边那段往后走
* `k` 在临时数组里往后放答案

---

## 2）主循环：两边都还有数时，不断比较

```java
while(i <= mid && j <= r){
    if(q[i] <= q[j]) tmp[k++] = q[i++];
    else tmp[k++] = q[j++];
}
```

意思是：

只要左右两边都没取完，就比较当前两个数，把较小的那个放进 `tmp`。

### 如果左边小：

```java
tmp[k++] = q[i++];
```

等价于两步：

```java
tmp[k] = q[i];
k++;
i++;
```

### 如果右边小：

```java
tmp[k++] = q[j++];
```

等价于：

```java
tmp[k] = q[j];
k++;
j++;
```

---

# 八、手动模拟一下这个合并过程

假设当前两段已经有序：

```text
左边 q[l..mid] = 1 4 6
右边 q[mid+1..r] = 2 3 5
```

初始：

```text
i 指向 1
j 指向 2
k = 0
```

比较过程：

* `1 <= 2`，放 `1`
* `4 > 2`，放 `2`
* `4 > 3`，放 `3`
* `4 <= 5`，放 `4`
* `6 > 5`，放 `5`

这时右边没了，左边还剩 `6`。

所以 `tmp` 里目前是：

```text
1 2 3 4 5
```

然后再把左边剩下的 `6` 接上去，变成：

```text
1 2 3 4 5 6
```

---

# 九、为什么还需要后面两个 `while`

主循环结束时，通常会出现一种情况：

* 左边取完了，右边还有剩
* 或者右边取完了，左边还有剩

所以要把剩下的元素直接接到 `tmp` 后面。

---

## 左边还有剩余

```java
while(i <= mid) tmp[k++] = q[i++];
```

---

## 右边还有剩余

```java
while(j <= r) tmp[k++] = q[j++];
```

为什么可以“直接接上”？

因为左右两边本身已经各自有序了。
当其中一边取完后，另一边剩下的部分一定已经按顺序排好了，直接搬过去就行。

---

# 十、最后一步：把 `tmp` 拷回 `q`

```java
for(i = l, j = 0; i <= r; i++, j++) q[i] = tmp[j];
```

这句意思是：

把刚刚合并好的结果，从 `tmp[0]` 开始，复制回原数组的 `q[l..r]` 这一段。

比如刚刚合并的是区间 `[2, 5]`，那就把：

* `tmp[0]` 放到 `q[2]`
* `tmp[1]` 放到 `q[3]`
* `tmp[2]` 放到 `q[4]`
* `tmp[3]` 放到 `q[5]`

这样原数组这一段就真正变成有序了。

---

# 十一、整个递归过程怎么理解

以数组：

```text
3 1 2 4 5
```

为例。

归并排序会这么想：

## 第一步：不断二分

```text
3 1 2 4 5
-> [3 1 2] [4 5]
-> [3 1] [2] [4] [5]
-> [3] [1] [2] [4] [5]
```

拆到每段只剩 1 个数时，就停。

因为单个数天然有序。

---

## 第二步：开始往回合并

```text
[3] 和 [1] 合并 -> [1 3]
[1 3] 和 [2] 合并 -> [1 2 3]
[4] 和 [5] 合并 -> [4 5]
[1 2 3] 和 [4 5] 合并 -> [1 2 3 4 5]
```

最终整个数组有序。

---

# 十二、这题最关键的理解点

你至少要抓住这 4 个。

## 1. 归并排序是“先分后合”

先拆成小问题，再把小问题答案合并起来。

## 2. 合并的前提是“左右两边已经有序”

如果左右无序，就不能这样线性合并。

## 3. `tmp` 是临时数组

用来存当前区间合并后的结果。

## 4. 合并完必须复制回原数组

否则下一层递归拿到的 `q` 不是排好序的内容。

---

# 十三、和快速排序的对比

这个阶段你要刻意分清：

## 快速排序

* 先选基准
* 划分左右
* 递归左右

## 归并排序

* 先递归左右
* 再合并左右

所以它们虽然都属于分治，但“做事顺序”不一样。

一句话区分：

**快排靠划分，归并靠合并。**

---

# 十四、你这题最容易卡住的地方

## 1）为什么 `tmp` 下标从 0 开始

因为这里 `tmp` 只是当前这次合并的临时存储区，不一定非要从 `l` 开始放。

所以代码写成：

```java
int k = 0;
```

完全没问题。

---

## 2）为什么复制回去时要 `i = l, j = 0`

因为：

* `q` 要写回到当前区间 `[l, r]`
* `tmp` 里这次合并结果是从 `0` 开始存的

所以一个从 `l` 开始，一个从 `0` 开始。

---

## 3）为什么 `if(q[i] <= q[j])`

写 `<=` 而不是 `<` 也可以保持左边相等元素先放，有助于维持稳定性。
你现在先知道：**相等时先取左边也没问题，而且是常见写法。**

---

# 十五、给你一版适合学习的注释版

```java
public static void mergeSort(int[] q, int l, int r, int[] tmp){
    // 区间长度为 0 或 1，已经有序
    if(l >= r) return;

    // 找中点，把区间分成两半
    int mid = l + r >> 1;

    // 先递归排好左边
    mergeSort(q, l, mid, tmp);

    // 再递归排好右边
    mergeSort(q, mid + 1, r, tmp);

    // 开始合并两个有序段
    int k = 0, i = l, j = mid + 1;

    // 左右两边都有数时，比较后放入 tmp
    while(i <= mid && j <= r){
        if(q[i] <= q[j]) tmp[k++] = q[i++];
        else tmp[k++] = q[j++];
    }

    // 左边剩余元素直接放入 tmp
    while(i <= mid) tmp[k++] = q[i++];

    // 右边剩余元素直接放入 tmp
    while(j <= r) tmp[k++] = q[j++];

    // 把 tmp 中合并好的结果复制回 q 的当前区间
    for(i = l, j = 0; i <= r; i++, j++) q[i] = tmp[j];
}
```

---

# 十六、你现阶段该怎么记模板

建议你按这个顺序记：

## 先记骨架

```java
mergeSort(q, l, r, tmp)
1. if(l >= r) return
2. mid
3. mergeSort 左
4. mergeSort 右
5. merge
```

## 再记合并三段

* 主循环比较两边
* 收尾左边剩余
* 收尾右边剩余

## 最后记复制回原数组

这是归并排序很容易漏的一步。

---

# 十七、这题的 `main` 和上一题相比，多了什么

最关键就多了这个：

```java
int[] tmp = new int[n];
```

因为归并排序需要辅助空间。
这也是它和快排一个明显区别：

* 快排主要靠原地交换
* 归并排序通常需要额外数组辅助

---

# 十八、你学完这题后，应该能自己复述这句话

你最好自己试着说一遍：

**归并排序就是先把左边和右边分别排好，再用两个指针把两个有序段合并起来。**

如果这句话你能顺着讲出来，这题核心就抓住了。

---

下一步最适合的是：我把这题的 `main` 和 `mergeSort` 一起改写成一份**逐行中文注释版完整代码**。



```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(bufferedReader.readLine());
        int[] q = new int[n];
        //临时数组问题
        int[] temp = new int[n];
        String[] s = bufferedReader.readLine().split(" ");
        //这里建议用n 而不是length
        for (int i = 0; i < n; i++) {
            q[i] = Integer.parseInt(s[i]);
        }

        mergeSort(q, 0, q.length - 1, temp);

        for (int i : q) {
            System.out.print(i + " ");
        }

    }
    public static void mergeSort(int[] q, int l, int r, int[] temp){
        //这里无需排队的条件判断问题
        if(l >= r) return;
        int mid = l + r >> 1;
        mergeSort(q, l, mid, temp);
        mergeSort(q, mid + 1, r, temp);

        int k = 0, i = l, j = mid + 1;

        while (i <= mid && j <= r){
            if(q[i] <= q[j]){
                temp[k++] = q[i++];
            }else{
                temp[k++] = q[j++];
            }
        }
        while (i <= mid){
            temp[k++] = q[i++];
        }
        while (j <= r){
            temp[k++] = q[j++];
        }
        //这里的i++和j++ 位置问题
        for (i = l, j = 0; i <= r; i++, j++) {
            q[i] = temp[j];
        }
    }
}

```