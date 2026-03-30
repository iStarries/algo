# AcWing 算法基础课 -- 基础算法

## AcWing 789. 数的范围 

`难度：简单`

### 题目描述

给定一个按照升序排列的长度为n的整数数组，以及 q 个查询。

对于每个查询，返回一个元素k的起始位置和终止位置（位置从0开始计数）。

如果数组中不存在该元素，则返回“-1 -1”。
输入格式

第一行包含整数n和q，表示数组长度和询问个数。

第二行包含n个整数（均在1~10000范围内），表示完整数组。

接下来q行，每行包含一个整数k，表示一个询问元素。
输出格式

共q行，每行包含两个整数，表示所求元素的起始位置和终止位置。

如果数组中不存在该元素，则返回“-1 -1”。

**数据范围**
```r
1≤n≤100000
1≤q≤10000
1≤k≤10000
```
**输入样例**：
```r
6 3
1 2 2 3 3 4
3
4
5
```
**输出样例**：
```r
3 4
5 5
-1 -1
```

### Solution

```java
import java.util.*;
import java.io.*;

public class Main{
    public static void main(String[] args) throws IOException{
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String[] s = in.readLine().split(" ");
        int n = Integer.parseInt(s[0]);
        int q = Integer.parseInt(s[1]);
        s = in.readLine().split(" ");
        int[] a = new int[n];
        for(int i = 0; i < n; i++){
            a[i] = Integer.parseInt(s[i]);
        }
        for(int i = 0; i < q; i++){
            int k = Integer.parseInt(in.readLine());
            // low 起始位置，high 终止位置
            int low = 0, high = 0;
            // 查找第一个值等于给定值的元素
            int l = 0, r = n - 1;
            while(l <= r){
                int mid = l + r >> 1;
                if(a[mid] > k) r = mid - 1;
                else if(a[mid] < k) l = mid + 1;
                else{
                    if(mid == 0 || a[mid - 1] != k) {
                        low = mid;
                        break;
                    }
                    else r = mid - 1;
                }
            }
            if(a[low] != k){
                System.out.println("-1 -1");
            }else{
                // 查找最后一个值等于给定值的元素
                l = 0;
                r = n - 1;
                while(l <= r){
                    int mid = l + r >> 1;
                    if(a[mid] > k) r = mid - 1;
                    else if(a[mid] < k) l = mid + 1;
                    else{
                        if(mid == n - 1 || a[mid + 1] != k){
                            high = mid;
                            break;
                        }else{
                            l = mid + 1;
                        }
                    }
                }
                System.out.println(low + " " + high);
            }
        }
    }
}
```
这题是**二分查找的经典题**，核心不是“找某个数在不在”，而是：

**在有序数组里，分别找这个数第一次出现的位置、最后一次出现的位置。**

题目与代码见你上传的文件 。

---

## 一、先理解题目在问什么

给你一个**升序数组**，例如：

```text
1 2 2 3 3 4
```

如果查询 `3`，答案不是只说“找到了”，而是要返回：

* 第一次出现的位置：`3`
* 最后一次出现的位置：`4`

所以输出：

```text
3 4
```

如果查询 `5`，数组里没有，就输出：

```text
-1 -1
```

---

## 二、为什么这题适合用二分

因为数组是**升序排列**的，这就给了我们二分的条件。

普通二分通常是：

* 找到某个 `k` 就停

但这题不够，因为数组中可能有多个 `k`。

例如：

```text
1 2 2 2 3
```

你二分到中间那个 `2`，并不知道它是不是第一个、最后一个。

所以这里要做两次二分：

### 1）找左边界

找“**第一个等于 k 的位置**”

### 2）找右边界

找“**最后一个等于 k 的位置**”

---

## 三、这题真正要掌握的思想

你可以把它记成一句话：

### 在有重复元素的有序数组里：

* 找左边界：命中后继续往左缩
* 找右边界：命中后继续往右缩

这是这题的本质。

---

## 四、结合样例手推一次

数组：

```text
下标: 0 1 2 3 4 5
数值: 1 2 2 3 3 4
```

查询 `3`。

---

### 第一步：找第一个 3

我们想找到最左边那个 `3`。

二分过程中如果：

* `a[mid] < 3`，说明答案在右边
* `a[mid] > 3`，说明答案在左边
* `a[mid] == 3`，不能直接结束，要判断它是不是第一个 3

如果 `mid == 0` 或者 `a[mid - 1] != 3`，那它就是第一个 3。
否则说明左边还有 3，要继续往左找。

最后找到位置 `3`。

---

### 第二步：找最后一个 3

同理：

* `a[mid] < 3`，去右边
* `a[mid] > 3`，去左边
* `a[mid] == 3`，判断它是不是最后一个 3

如果 `mid == n - 1` 或者 `a[mid + 1] != 3`，那它就是最后一个 3。
否则右边还有 3，要继续往右找。

最后找到位置 `4`。

---

## 五、你这份题解代码在做什么

你上传的代码整体思路是对的：
**每个询问做两次二分。** 

我按逻辑拆开讲。

---

## 六、读入部分

```java
BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
String[] s = in.readLine().split(" ");
int n = Integer.parseInt(s[0]);
int q = Integer.parseInt(s[1]);
s = in.readLine().split(" ");
int[] a = new int[n];
for(int i = 0; i < n; i++){
    a[i] = Integer.parseInt(s[i]);
}
```

作用：

* 读入数组长度 `n`
* 读入询问次数 `q`
* 读入长度为 `n` 的数组 `a`

这里你暂时可以先记住：

* `BufferedReader`：高效输入
* `split(" ")`：按空格切开
* `Integer.parseInt(...)`：字符串转整数

---

## 七、外层循环：处理每个查询

```java
for(int i = 0; i < q; i++){
    int k = Integer.parseInt(in.readLine());
```

每次读入一个查询值 `k`。

比如这次查询 `3`。

---

## 八、第一次二分：找起始位置 low

```java
int low = 0, high = 0;
int l = 0, r = n - 1;
while(l <= r){
    int mid = l + r >> 1;
    if(a[mid] > k) r = mid - 1;
    else if(a[mid] < k) l = mid + 1;
    else{
        if(mid == 0 || a[mid - 1] != k) {
            low = mid;
            break;
        }
        else r = mid - 1;
    }
}
```

### 这段逻辑怎么理解

#### 情况1：`a[mid] > k`

说明中点太大，答案只能在左边：

```java
r = mid - 1;
```

#### 情况2：`a[mid] < k`

说明中点太小，答案只能在右边：

```java
l = mid + 1;
```

#### 情况3：`a[mid] == k`

这时候找到了一个 `k`，但不一定是第一个。

所以继续判断：

```java
if(mid == 0 || a[mid - 1] != k)
```

意思是：

* 如果 `mid` 已经在最左边了
* 或者前一个数不是 `k`

那么当前这个 `mid` 就是第一个 `k`。

否则：

```java
r = mid - 1;
```

继续去左边找。

---

## 九、为什么接下来要判断 `a[low] != k`

```java
if(a[low] != k){
    System.out.println("-1 -1");
}
```

因为有可能数组里根本没有 `k`。

这时第一次二分不会进入 `low = mid; break;`，于是 `low` 仍然保持初始值 `0`。

所以要检查一下：

* 如果 `a[low] != k`，说明没找到
* 直接输出 `-1 -1`

---

## 十、第二次二分：找结束位置 high

```java
l = 0;
r = n - 1;
while(l <= r){
    int mid = l + r >> 1;
    if(a[mid] > k) r = mid - 1;
    else if(a[mid] < k) l = mid + 1;
    else{
        if(mid == n - 1 || a[mid + 1] != k){
            high = mid;
            break;
        }else{
            l = mid + 1;
        }
    }
}
System.out.println(low + " " + high);
```

逻辑和前面一样，只不过现在找的是**最后一个 `k`**。

### 当 `a[mid] == k` 时：

判断：

```java
if(mid == n - 1 || a[mid + 1] != k)
```

意思是：

* 如果 `mid` 已经在最右边
* 或者后一个数不是 `k`

那它就是最后一个 `k`。

否则说明右边还有 `k`，继续往右找：

```java
l = mid + 1;
```

---

## 十一、这份代码有一个小风险，你要知道

你这份代码能过很多情况，但写法上有一点不够稳：

```java
if(a[low] != k)
```

因为 `low` 初始是 `0`，如果没找到，就拿 `a[0]` 去判断。

虽然在这题给的数据范围下通常没问题，但从代码健壮性看，**更推荐把 low 初始成 -1**，这样语义更清楚。

例如：

```java
int low = -1;
```

然后找不到就判断：

```java
if(low == -1)
```

这种写法更安全、更清晰。

---

## 十二、更推荐你掌握的写法

相比“找到后判断左右邻居”，我更建议你学会一种更标准的边界二分写法：

* 左边界：找第一个 `>= k` 的位置
* 右边界：找第一个 `> k` 的位置，再减一

这样思路更统一。

---

## 十三、推荐版 Java 写法

```java
import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String[] s = in.readLine().split(" ");
        int n = Integer.parseInt(s[0]);
        int q = Integer.parseInt(s[1]);

        int[] a = new int[n];
        s = in.readLine().split(" ");
        for (int i = 0; i < n; i++) {
            a[i] = Integer.parseInt(s[i]);
        }

        while (q-- > 0) {
            int k = Integer.parseInt(in.readLine());

            int left = findLeft(a, k);
            if (left == n || a[left] != k) {
                System.out.println("-1 -1");
                continue;
            }

            int right = findRight(a, k) - 1;
            System.out.println(left + " " + right);
        }
    }

    // 找第一个 >= k 的位置
    static int findLeft(int[] a, int k) {
        int l = 0, r = a.length - 1;
        while (l <= r) {
            int mid = (l + r) >> 1;
            if (a[mid] >= k) r = mid - 1;
            else l = mid + 1;
        }
        return l;
    }

    // 找第一个 > k 的位置
    static int findRight(int[] a, int k) {
        int l = 0, r = a.length - 1;
        while (l <= r) {
            int mid = (l + r) >> 1;
            if (a[mid] > k) r = mid - 1;
            else l = mid + 1;
        }
        return l;
    }
}
```

---

## 十四、为什么这版更好理解

### `findLeft(a, k)`

找第一个 `>= k` 的位置。

例如数组：

```text
1 2 2 3 3 4
```

查 `3`：

* 第一个 `>= 3` 的位置，就是第一个 `3`
* 所以左边界就是它

---

### `findRight(a, k)`

找第一个 `> k` 的位置。

查 `3`：

* 第一个 `> 3` 的数是 `4`
* 它的位置是 `5`
* 那么最后一个 `3` 的位置就是 `5 - 1 = 4`

---

## 十五、二分边界题最重要的模板意识

以后你做二分题，不要只想着“找到某个值”，而要先问自己：

### 我要找的是哪种边界？

* 第一个等于 `k`
* 最后一个等于 `k`
* 第一个大于等于 `k`
* 第一个大于 `k`
* 最后一个小于等于 `k`

很多题本质上都是“边界二分”。

这题就是最典型的入门题。

---

## 十六、复杂度

每次查询做两次二分：

* 一次二分是 `O(log n)`
* 两次还是 `O(log n)`

总复杂度：

```text
O(q log n)
```

对于 `n <= 100000, q <= 10000`，完全可以通过。

---

## 十七、你现在应该真正记住什么

这题最值得学的不是代码细节，而是这三点：

### 1. 有序数组中查找边界，用二分

不是线性扫。

### 2. 找左边界和右边界，要分两次二分

不能“找到就停”。

### 3. 命中目标后，决定继续往左还是往右

* 找第一个：继续左边
* 找最后一个：继续右边

---

## 十八、这题属于你目录里的哪一类

这题属于你现在正在学的**基础算法里的二分**，也是二分里最重要的基础题型之一。你给的总目录里，第一讲就包括二分，这题正是那个部分的标准练习。

---

## 十九、给你一个练习建议

你现在可以自己先不看代码，试着口述这两个问题：

1. 怎么找第一个等于 `k` 的位置？
2. 怎么找最后一个等于 `k` 的位置？

只要你能把这两个过程用自然语言说清楚，这题就算真的理解了。

下一步你要是愿意，我可以继续带你做这两件事中的一个：

* 把这题的**二分模板总结成你以后能直接套的版本**
* 按你的基础，用**更适合初学者的方式逐行讲这份 Java 代码**




## 我读了你建议的更统一的版本，不理解为什么返回的是 l
因为这个写法里，**`l` 不是随便剩下来的**，而是被我们“维护成答案的位置”。

你不理解这一点很正常，这正是“边界二分”最容易卡住的地方。

这题属于二分边界查找这一类。

---

## 先说结论

在这个模板里：

### `findLeft(a, k)` 里返回 `l`

因为循环结束时，`l` 恰好停在：

**第一个 `>= k` 的位置**

### `findRight(a, k)` 里返回 `l`

因为循环结束时，`l` 恰好停在：

**第一个 `> k` 的位置**

所以返回 `l` 不是巧合，是这个二分过程保证的。

---

## 一、先看 `findLeft`

代码是：

```java
static int findLeft(int[] a, int k) {
    int l = 0, r = a.length - 1;
    while (l <= r) {
        int mid = (l + r) >> 1;
        if (a[mid] >= k) r = mid - 1;
        else l = mid + 1;
    }
    return l;
}
```

它找的是：

**第一个 `>= k` 的位置**

---

## 二、这段代码到底在排除什么

每次看 `mid`：

### 情况1：`a[mid] >= k`

说明 `mid` 这个位置 **有可能就是答案**，但也可能答案在它左边。

所以这时不能丢掉 `mid` 左边那一半，只能做：

```java
r = mid - 1;
```

意思是：

* 把右边无用部分砍掉
* 继续去左边找更早出现的位置

---

### 情况2：`a[mid] < k`

说明 `mid` 这个位置一定不可能是答案。

因为我们要找的是第一个 `>= k` 的位置，而这里连 `k` 都没到。

所以答案一定在右边：

```java
l = mid + 1;
```

---

## 三、为什么最后返回 `l`

你可以这样理解：

循环过程中始终在维护一个规律：

* `l` 左边的数，都已经确定 **< k**
* `r` 右边的数，都已经确定 **>= k**

最后循环结束条件是：

```java
l > r
```

也就是 `l` 和 `r` 交错了。

这时区间已经空了，但边界刚好分出来：

* `l` 左边全是 `< k`
* `l` 以及右边，都是 `>= k` 的候选区域

所以：

## `l` 就是第一个 `>= k` 的位置

---

## 四、拿例子推一遍

数组：

```text
1 2 2 3 3 4
```

查 `k = 3`

下标：

```text
0 1 2 3 4 5
```

---

### 初始

```text
l = 0, r = 5
```

### 第一次

```text
mid = 2, a[2] = 2
```

因为 `2 < 3`，说明答案一定在右边：

```text
l = 3
```

---

### 第二次

```text
l = 3, r = 5
mid = 4, a[4] = 3
```

因为 `3 >= 3`，说明 `4` 可能是答案，但左边可能还有更早的 `3`：

```text
r = 3
```

---

### 第三次

```text
l = 3, r = 3
mid = 3, a[3] = 3
```

仍然满足 `a[mid] >= 3`：

```text
r = 2
```

---

现在：

```text
l = 3, r = 2
```

循环结束。

返回 `l = 3`

这正好就是第一个 `>= 3` 的位置，也就是第一个 `3` 的位置。

---

## 五、你可能会问：为什么不是返回 `r`

因为最后：

* `r` 停在最后一个 `< k` 的位置
* `l` 停在第一个 `>= k` 的位置

这两个位置刚好一左一右，挨着。

所以：

* 找“第一个满足条件的位置”时，常返回 `l`
* 找“最后一个不满足条件的位置”时，常返回 `r`

在这个模板里我们要的是前者，所以返回 `l`。

---

## 六、再看 `findRight`

代码：

```java
static int findRight(int[] a, int k) {
    int l = 0, r = a.length - 1;
    while (l <= r) {
        int mid = (l + r) >> 1;
        if (a[mid] > k) r = mid - 1;
        else l = mid + 1;
    }
    return l;
}
```

它找的是：

**第一个 `> k` 的位置**

理由完全一样。

循环结束时：

* `l` 左边都 `<= k`
* `r` 右边都 `> k`

所以 `l` 正好就是第一个 `> k` 的位置。

---

## 七、为什么右边界要 `findRight(a, k) - 1`

因为 `findRight(a, k)` 找到的是：

**第一个大于 `k` 的位置**

那它前一个位置，自然就是：

**最后一个小于等于 `k` 的位置**

而如果数组里确实有 `k`，那这个位置就是：

**最后一个等于 `k` 的位置**

所以：

```java
int right = findRight(a, k) - 1;
```

---

## 八、你可以记一个最重要的判断方法

看这句：

```java
if (a[mid] >= k) r = mid - 1;
else l = mid + 1;
```

这类写法的本质是：

### 我们在找“最左边满足条件的位置”

这里“满足条件”就是：

```text
a[mid] >= k
```

所以一旦 `mid` 满足条件，不是立刻返回，而是继续往左逼近。

最后留下的最左位置，就是 `l`。

---

## 九、你可以把它想成“分界线”

例如数组：

```text
1 2 2 3 3 4
```

查 `k = 3` 时，按“是否 >= 3”分类：

```text
2左边：不满足
3开始：满足
```

也就是：

```text
[1 2 2] | [3 3 4]
```

我们要找的其实就是这条分界线的位置。

而这条分界线，就是 `l`。

---

## 十、最后给你一句最好记的话

### 二分结束后：

* `l` 常表示“右半部分的第一个位置”
* `r` 常表示“左半部分的最后一个位置”

所以这题这种边界二分，返回 `l` 是因为：

**我们要的就是那个“第一个进入目标区域的位置”。**

---

如果你愿意，我下一条可以专门给你画一张非常直观的：

**“为什么 `l` 和 `r` 最后会交错，以及交错后各自代表什么” 的图解版**。


## 那如果我返回 l-1 会不会出问题
一般**不会出问题**，而且在这一个模板里：

```java
if (a[mid] <= k) l = mid + 1;
else r = mid - 1;
```

循环结束后，确实有：

```text
l = r + 1
```

所以：

```text
r == l - 1
```

也就是说，**返回 `r` 和返回 `l - 1` 是等价的**。

---

## 为什么等价

因为循环结束条件是：

```java
while (l <= r)
```

退出时一定是 `l > r`。

而每次更新又都是：

* `l = mid + 1`
* 或 `r = mid - 1`

所以最后二者会刚好错开一个位置，变成：

```text
r  l
```

也就是：

```text
r = l - 1
```

因此这题里你返回 `l - 1`，结果和返回 `r` 一样。

---

## 例子

数组：

```text
1 2 2 3 3 4
```

找最后一个 `<= 3` 的位置。

最后会停在：

```text
l = 5, r = 4
```

所以：

* 返回 `r` 是 `4`
* 返回 `l - 1` 也是 `4`

一样。

---

## 但我更建议你返回 `r`

因为它和你要找的含义是直接对应的：

这个模板维护的是：

* 左边都 `<= k`
* 右边都 `> k`

结束后：

* `r` = 最后一个 `<= k`
* `l` = 第一个 `> k`

所以你要找“最后一个 `<= k`”，直接返回 `r`，语义最清楚。

如果写成返回 `l - 1`，虽然对，但你脑子里还要多绕一步：

* 先知道 `l` 是第一个 `> k`
* 再减一得到答案

对于初学阶段，不如直接返回 `r` 好理解。

---

## 什么时候要特别小心

要注意的是：**答案可能不存在**。

例如数组：

```text
5 6 7
```

找最后一个 `<= 3` 的位置。

最后会得到：

```text
l = 0, r = -1
```

这时：

* 返回 `r` 是 `-1`
* 返回 `l - 1` 也是 `-1`

还是一样。

所以不是“会不会出问题”，而是你要知道：

### `-1` 在这里表示“不存在这样的下标”

---

## 你可以记成一组对称关系

### 找第一个 `>= k`

```java
if (a[mid] >= k) r = mid - 1;
else l = mid + 1;
return l;
```

### 找最后一个 `<= k`

```java
if (a[mid] <= k) l = mid + 1;
else r = mid - 1;
return r;
```

这两个最适合成对记忆。

---

你的问题答案就是：

**不会，在这个模板里返回 `l - 1` 和返回 `r` 等价。只是更推荐返回 `r`，因为含义更直接。**

下一步你其实已经快把“边界二分”的核心吃透了。现在最值得继续弄清的是：

**为什么有的题返回 `l`，有的题返回 `r`，本质到底由什么决定。**


```java
import java.io.*;
public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String[] s = bufferedReader.readLine().split(" ");
        int n = Integer.parseInt(s[0]);
        int q = Integer.parseInt(s[1]);
        int[] num = new int[n];
        s = bufferedReader.readLine().split(" ");
        for (int i = 0; i < n; i++) {
            num[i] = Integer.parseInt(s[i]);
        }
        int x;
        while (q-- > 0){
            x = Integer.parseInt(bufferedReader.readLine());
            int l = findLeft(num, x);
            if(l >= 0 && l < n && num[l] == x) System.out.print(l + " ");
            else{
                System.out.println("-1 -1");
                continue;
            }
            int r = findRight(num, x);
            System.out.println(r);
        }
    }
    private static int findRight(int[] num, int x) {
        int l = 0, r = num.length - 1;
        while (l <= r){
            int mid = (l + r) / 2;
            if(num[mid] <= x) l = mid + 1;
            else r = mid - 1;
        }
        return r;
    }
    public static int findLeft(int[] num, int target){
        int l = 0, r = num.length - 1;
        while (l <= r){
            int mid = (l + r) / 2;
            if(num[mid] >= target) r = mid - 1;
            else l = mid + 1;
        }
        return l;
    }
}
```