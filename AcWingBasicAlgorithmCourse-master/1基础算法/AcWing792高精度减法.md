# AcWing 算法基础课 -- 基础算法

## AcWing 792. 高精度减法 

`难度：简单`

### 题目描述

给定两个正整数，计算它们的差，计算结果可能为负数。

**输入格式**

共两行，每行包含一个整数。

**输出格式**

共一行，包含所求的差。

```r
数据范围

1≤整数长度≤100000

输入样例：

32
11

输出样例：

21
```
### Solution

```java
import java.util.*;
import java.io.*;

public class Main{
    public static void main(String[] args) throws IOException{
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        // 字符串读取两个数字
        String a = in.readLine(), b = in.readLine();
        // 用数组保存数字，数组的0位对应数字的个位，所以倒序遍历字符串
        List<Integer> x = new ArrayList<>();
        List<Integer> y = new ArrayList<>();
        for(int i = a.length() - 1; i >= 0; i--) x.add(a.charAt(i) - '0');
        for(int i = b.length() - 1; i >= 0; i--) y.add(b.charAt(i) - '0');
        // 用栈来存储，存的话先存个位，打印的话先打印高位
        Deque<Integer> c = new ArrayDeque<>();
        // 如果 x 大于等于 y， 就用 x - y
        // 如果 x 小于 y，先打印负号，再计算 y - x
        if(cmp(x, y)) c = sub(x, y);
        else{
            System.out.print("-");
            c = sub(y, x);
        }
        while(!c.isEmpty()){
            System.out.print(c.pop());
        }
    }
    public static boolean cmp(List<Integer> x, List<Integer> y){
        if(x.size() != y.size()) return x.size() > y.size();
        for(int i = x.size() - 1; i >= 0; i--){
            if(x.get(i) != y.get(i)) return x.get(i) > y.get(i);
        }
        return true;
    }
    public static Deque<Integer> sub(List<Integer> x, List<Integer> y){
        Deque<Integer> c =new ArrayDeque<>();
        // t 记录借位
        int t = 0;
        for(int i = 0; i < x.size(); i++){
            t = x.get(i) - t;
            if(i < y.size()) t = t - y.get(i);
            c.push((t + 10) % 10);
            // 如果 t 为负数，就借一位，否则不用借位
            t = t < 0 ? 1 : 0;
        }
        // 去除前导 0
        while( c.size() > 1 && c.peek() == 0) c.pop();
        return c;
    }
}
```

数组代替 List 表示被减数和减数

```java
import java.util.*;

class Main{
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        String sa = sc.next();
        String sb = sc.next();
        int la = sa.length();
        int lb = sb.length();
        int[] a = new int[la];
        int[] b = new int[lb];
        for(int i = la - 1, j = 0; i >= 0; i--, j++)    a[j] = sa.charAt(i) - '0';
        for(int i = lb - 1, j = 0; i >= 0; i--, j++)    b[j] = sb.charAt(i) - '0';
        // 比较两个数，用大的数减去小的数
        Deque<Integer> c = new ArrayDeque<>();
        if(cmp(a, b)) c = sub(a, b);
        else{
            System.out.print("-");
            c = sub(b, a);
        }
        while(!c.isEmpty()) System.out.print(c.pop());
    }
    public static boolean cmp(int[] a, int[] b){
        if(a.length != b.length) return a.length > b.length;
        else{
            for(int i = a.length - 1; i >= 0; i--){
                if(a[i] != b[i]) return a[i] > b[i];
            }
        }
        return true;
    }
    public static Deque<Integer> sub(int[] a, int[] b){
        Deque<Integer> c = new ArrayDeque<>();
        // t 保存借位
        int t = 0;
        for(int i = 0; i < a.length; i++){
            t = a[i] - t;
            if(i < b.length) t -= b[i];
            // 加10模10，很好的处理了负数的 t
            c.push((t + 10) % 10);
            if(t < 0) t = 1;
            else t = 0;
        }
        // 相减可能有 0，去除前导 0
        // 但是要保留最后一位 0
        while(c.size() > 1 && c.peek() == 0) c.pop();
        return c;
    }
}
```
可以。这里我换一种**更直白、一步一步**的讲法，不贴着代码讲，先让你真正理解这题在做什么。

这题的本质就是：

**因为数字太大，不能直接用 `int` 或 `long long` 存，所以要自己模拟“竖式减法”。** 

---

# 一、先想清楚：我们平时怎么算减法

比如：

```text
1234 - 567
```

手算时一定是从右往左：

```text
  1234
-  567
------
```

先算个位，再算十位，再算百位。

程序也一样，所以要把数字存成：

```text
1234 -> [4, 3, 2, 1]
567  -> [7, 6, 5]
```

也就是：

* 下标 `0` 存个位
* 下标 `1` 存十位
* 下标 `2` 存百位

这样循环从 `0` 开始，就等于从个位开始减。题解里也是把字符串倒序存起来的。

---

# 二、为什么不能直接 `a - b`

因为题目里的整数长度可以非常大，可能有十万位，普通整数类型根本存不下。

所以只能：

* 把每一位单独拿出来
* 一位一位做减法
* 自己处理借位

这就是“高精度减法”。

---

# 三、整道题其实只有 3 步

## 第 1 步：比较谁大谁小

因为结果可能是负数，所以要先判断：

* 如果 `a >= b`，直接算 `a - b`
* 如果 `a < b`，先输出负号，再算 `b - a`

题解里有个 `cmp` 函数，就是专门做这个比较。它先比长度，长度相同再从高位往低位比较。

比如：

* `123` 和 `45`，显然 `123` 大，因为位数更多
* `456` 和 `451`，位数一样，就从最高位开始比

---

## 第 2 步：按位相减，并处理借位

这是最核心的地方。

我们用一个变量 `t` 表示：

* **当前这一位是否要先减去上一位借来的 1**

注意：这里的 `t` 不是“结果”，而是“借位状态”。题解里也是这么写的：`t` 记录借位。

---

# 四、借位到底怎么理解

看例子：

```text
32 - 18
```

先倒过来：

```text
a = [2, 3]
b = [8, 1]
```

## 个位

```text
2 - 8
```

不够减，要借 1。

借完以后个位相当于：

```text
12 - 8 = 4
```

于是：

* 当前位结果是 `4`
* 下一位要少 1，所以“借位标记”设成 `1`

---

## 十位

原来十位是 `3`，但前一位借走了 `1`，所以这位先变成：

```text
3 - 1 = 2
```

再减去 `1`：

```text
2 - 1 = 1
```

所以结果是：

```text
14
```

---

# 五、代码里为什么写成这样

题解中的核心写法是：

```java
t = x.get(i) - t;
if(i < y.size()) t = t - y.get(i);
c.push((t + 10) % 10);
t = t < 0 ? 1 : 0;
```

我给你翻译成中文：

## 1）`t = x[i] - t`

先用当前位减去“上一位借来的 1”。

因为：

* 如果上一位没借位，`t = 0`
* 如果上一位借了位，`t = 1`

所以这一步相当于：

```text
当前位 = 被减数当前位 - 借位
```

---

## 2）`if(i < y.size()) t -= y[i]`

再减去减数这一位。

也就是：

```text
当前位 = 被减数当前位 - 借位 - 减数当前位
```

---

## 3）`c.push((t + 10) % 10)`

这一步是在取“当前这一位真正应该留下来的数字”。

为什么这么写？

因为当前位可能是负数。

例如：

```text
2 - 8 = -6
```

但实际这一位应该写的是：

```text
12 - 8 = 4
```

而 `(-6 + 10) % 10 = 4`

再比如：

```text
7 - 3 = 4
```

那 `(4 + 10) % 10 = 4`

所以这一句统一处理了：

* 不借位的情况
* 借位的情况

这个写法很巧。

---

## 4）`t = t < 0 ? 1 : 0`

这一步是在决定：

**下一位要不要向更高位借 1**

因为：

* 如果当前算出来是负数，说明不够减，要借位，所以 `t = 1`
* 否则不用借位，所以 `t = 0`

注意这里更新后的 `t`，已经不再是刚才的差值了，而是重新变回“借位标记”。

这是这段代码最容易看晕的地方。

---

# 六、完整手推一个例子

我们手推：

```text
1000 - 1
```

倒序后：

```text
a = [0, 0, 0, 1]
b = [1]
```

初始：

```text
t = 0
```

---

## 第 0 位（个位）

```text
t = 0 - 0 = 0
t = 0 - 1 = -1
当前位结果 = (-1 + 10) % 10 = 9
因为 t < 0，所以下一位借位：t = 1
```

---

## 第 1 位（十位）

```text
t = 0 - 1 = -1
b 没这一位，不减
当前位结果 = 9
因为 t < 0，所以下一位继续借位：t = 1
```

---

## 第 2 位（百位）

同理：

```text
结果 = 9
继续借位
```

---

## 第 3 位（千位）

```text
t = 1 - 1 = 0
b 没这一位
当前位结果 = 0
因为 t >= 0，不借位：t = 0
```

得到栈里的结果相当于：

```text
0 9 9 9
```

去掉前导零后就是：

```text
999
```

题解最后也专门做了“去掉前导 0，但至少保留一位”的处理。

---

# 七、为什么结果要去前导零

比如：

```text
1000 - 999 = 1
```

你按位算出来，可能会得到：

```text
0001
```

但输出时应该是：

```text
1
```

所以要不断删掉前导零。

但要注意一个边界：

```text
1 - 1 = 0
```

这时候结果不能删成空，必须保留一个 `0`。题解里也处理了这个条件：只有当结果长度大于 1 时，才继续删前导零。

---

# 八、你现在真正需要记住的模板逻辑

你可以把这题直接记成下面这个流程：

```text
1. 字符串读入
2. 倒序存每一位
3. 比较大小
4. 用大数减小数
5. 从低位到高位模拟减法
6. t 表示借位
7. 去前导零
8. 输出结果
```

---

# 九、最容易错的点

## 1. 忘记先比较大小

那你就没法正确处理负数。

## 2. 借位变量 `t` 的含义没搞清

它不是一直表示“差值”，而是经常在两种角色之间切换：

* 先暂时表示当前位计算值
* 然后又被改成“下一位是否借位”的标记

## 3. 前导零删过头

结果是 `0` 时，必须保留一个 `0`。

## 4. 没有倒序存

那你会发现处理起来非常别扭，因为减法天然是从个位开始。

---

# 十、给你一句最简版总结

这题就是：

**把大整数拆成一位一位，倒序存储，从个位开始模拟竖式减法，用 `t` 记录借位。**

如果你愿意，我下一条可以直接给你讲：
**“这份 Java 代码每一行到底在干什么”**，我会按代码顺序逐行解释。


## 这种题考察什么
这种题主要考察四件事。

**1. 把“数学竖式”翻译成程序的能力。**
题解里本质就是在模拟手算减法：从个位开始，逐位相减，遇到不够减就借位，`t` 专门记录借位。也就是说，它考的不是公式，而是你能不能把手算过程写成循环。

**2. 高精度的表示方法。**
因为整数长度可达 `100000` 位，普通整型存不下，所以必须把数当作字符串读入，再拆成每一位保存。题解里把字符串倒序存入数组或 `List`，让下标 `0` 对应个位，这就是高精度题最基础的“表示”。

**3. 细节处理能力。**
这类题经常不难想，但容易写错。这里的关键细节有三个：先比较两个数大小决定是否输出负号；减法时正确维护借位；最后去掉前导零但要保留最后一个 `0`。题解中的 `cmp`、`sub` 和删前导零部分，考的就是这些实现细节。

**4. 模板意识。**
高精度加减乘除在基础算法里通常都属于“模板题”。这题是在训练你掌握一个可复用的框架：
字符串读入 → 倒序存位 → 比较大小 → 模拟运算 → 处理前导零。
后面你学高精度加法、乘低精度、除低精度，整体写法会很像。

所以一句话概括：

**这题表面考减法，实际考的是“高精度数怎么存、竖式过程怎么模拟、边界细节怎么处理”。**

再往算法学习的角度说，这题属于**基础模拟 + 模板实现题**，不是考复杂思维，而是考你是否能把过程写准确。

你可以把它归到这类题型里记：

**高精度题的核心不是算术，而是表示与模拟。**

下一步你最该建立的意识是：

* 加法：处理进位
* 减法：处理借位
* 乘法：每位贡献
* 除法：从高位往低位模拟

这样整个高精度模块会连起来。


```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
class Main{
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String s1 = bufferedReader.readLine();
        String s2 = bufferedReader.readLine();
        if(s1.length() > s2.length()) sub(s1, s2);
        else if(s1.length() < s2.length()) {

            System.out.print("-");sub(s2, s1);
        }else{
            int com = s1.compareTo(s2);
            if (com > 0) sub(s1, s2);
            else if(com < 0) {
                System.out.print("-");sub(s2, s1);
            }
            else System.out.println(0);
        }
    }
    public static void sub(String s1, String s2){
        int[] ints1 = new int[s1.length()];
        int[] ints2 = new int[s2.length()];
        int k = 0;
        for (int i = s1.length() - 1; i >= 0; i--) {
            ints1[k++] = s1.charAt(i) - '0';
        }
        k = 0;
        for (int i = s2.length() - 1; i >= 0; i--) {
            ints2[k++] = s2.charAt(i) - '0';
        }
        int[] res = new int[s1.length()];

        int t = 0;
        for (int i = 0; i < s1.length(); i++) {
            t = ints1[i] - t;
            if(i < s2.length()) t -= ints2[i];
            res[i] = (t + 10) % 10;
            if(t < 0) t = 1;
            else t = 0;
        }
        int start;
        for (start = res.length - 1; start >= 0; start--) {
            if(res[start] != 0) break;
        }
        while (start >= 0) System.out.print(res[start--]);
    }

}
```