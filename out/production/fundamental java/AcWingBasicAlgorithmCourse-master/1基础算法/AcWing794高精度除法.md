# AcWing 算法基础课 -- 基础算法

## AcWing 794. 高精度除法 

`难度：简单`

### 题目描述

给定两个非负整数A，B，请你计算 A / B的商和余数。

**输入格式**

共两行，第一行包含整数A，第二行包含整数B。

**输出格式**

共两行，第一行输出所求的商，第二行输出所求余数。


数据范围

$1≤A的长度≤100000,$
$0≤B≤10000$

```r
输入样例：

7
2

输出样例：

3
1
```
### Solution

```java
import java.util.*;

class Main{
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        String sa = sc.next();
        int la = sa.length();
        int[] a = new int[la];
        for(int i = 0, j = 0; i < la; i++, j++) a[i] = sa.charAt(j) - '0';
        int b = sc.nextInt();
        Deque<Integer> c = new ArrayDeque<>();
        // 商用 c 存储，返回余数
        int t = div(a, b, c);
        while(!c.isEmpty()) System.out.print(c.pollFirst());
        System.out.print("\n" + t);
    }
    public static int div(int[] a, int b, Deque<Integer> c){
        int t = 0;
        for(int i = 0; i < a.length; i++){
            t = t * 10 + a[i];
            c.addLast(t / b);
            t = t % b;
        }
        while(c.size() > 1 && c.peekFirst() == 0) c.pollFirst();
        return t;
    }
}
```

这题是**高精度除法里的“高精度 ÷ 低精度”模板题**。

从数据范围就能看出来：

* `A` 的长度最多 `100000`，所以 `A` 是高精度
* `B <= 10000`，所以 `B` 是普通整数

也就是说，这题不是两个超大数相除，而是：

**一个超大整数，除以一个普通整数。**
题解里也是按这个模型实现的：`A` 用字符串拆位存，`B` 直接用 `int`。`div` 函数返回余数，商放进 `Deque`。 `/mnt/data/AcWing794高精度除法.md`

---

# 一、这题考察什么

这题主要考三件事：

## 1. 你会不会模拟“竖式除法”

加法看进位，减法看借位，乘法看进位，这题看的是：

**余数怎么往下一位传。**

---

## 2. 你能不能看出“除法和前几题不一样”

前面的高精度加、减、乘低精度，基本都是从**低位往高位**处理。

但这题不是。

除法必须从**高位往低位**处理。

这是这题最重要的区别。

---

## 3. 你能不能正确维护“当前余数”

题解里用 `t` 表示当前处理到这一位时的“余数状态”：

```java
t = t * 10 + a[i];
当前商位 = t / b;
新的余数 = t % b;
```

这三句就是整题核心。 `/mnt/data/AcWing794高精度除法.md`

---

# 二、为什么除法要从高位开始

这个一定要真正理解。

比如：

```text
1234 ÷ 2
```

你手算时一定是：

* 先看 `1 ÷ 2`
* 再把下一位 `2` 放下来，变成 `12 ÷ 2`
* 再放下一位 `3`
* 再放下一位 `4`

也就是说，除法天然就是：

**从最高位往最低位，一位一位往下试商。**

所以这题和前面几题不同：

* 加减乘常常倒序存，从个位开始
* 除法要正序存，从最高位开始

题解里也正是这样存的：

```java
for (int i = 0, j = 0; i < la; i++, j++) a[i] = sa.charAt(j) - '0';
```

也就是 `a[0]` 存最高位。 `/mnt/data/AcWing794高精度除法.md`

---

# 三、这题的整体流程

你可以把这题记成固定四步：

## 第一步：把大整数按高位到低位存起来

例如：

```text
A = 1234
```

存成：

```text
a = [1, 2, 3, 4]
```

---

## 第二步：从左到右枚举每一位

设 `t` 表示“当前余数扩展后的值”。

每处理一位，都做：

```text
t = t * 10 + 当前位
商的这一位 = t / b
新的余数 = t % b
```

---

## 第三步：把每一位商存起来

因为你本来就是从高位往低位算，所以商也直接按顺序存。

题解里用的是：

```java
c.addLast(t / b);
```

---

## 第四步：去掉商的前导零

例如：

```text
7 ÷ 2
```

第一步可能算出商位中有前导 `0`，所以最后要删掉。

题解里是：

```java
while(c.size() > 1 && c.peekFirst() == 0) c.pollFirst();
```

意思就是：

* 只要商长度大于 1
* 且最高位是 0
* 就删掉它
* 但至少保留一位

`/mnt/data/AcWing794高精度除法.md`

---

# 四、最核心的一句怎么理解

题解核心是：

```java
t = t * 10 + a[i];
c.addLast(t / b);
t = t % b;
```

我们把它翻译成手算过程。

---

## 1）`t = t * 10 + a[i]`

这一步表示：

**把上一轮的余数带下来，再拼接当前这一位。**

比如你在算：

```text
1234 ÷ 2
```

一开始：

* 处理 `1`，余数可能是 `1`
* 下一轮处理 `2` 时，实际上不是单独算 `2`
* 而是算 `12`

程序里就写成：

```text
t = 1 * 10 + 2 = 12
```

这就是手算里“把下一位放下来”。

---

## 2）`c.addLast(t / b)`

表示：

**当前这一位商是多少。**

比如：

```text
12 ÷ 2 = 6
```

那么这一位商就是 `6`。

---

## 3）`t = t % b`

表示：

**当前这一轮算完后，还剩多少余数。**

比如：

```text
12 ÷ 2 = 6 ... 0
```

那余数就是 `0`。

如果是：

```text
13 ÷ 2 = 6 ... 1
```

那余数就是 `1`，下一轮继续带下去。

---

# 五、手推一个完整例子

我们手推：

```text
1234 ÷ 2
```

初始：

```text
a = [1, 2, 3, 4]
b = 2
t = 0
商 c = []
```

---

## 第 0 位：1

```text
t = 0 * 10 + 1 = 1
商位 = 1 / 2 = 0
余数 = 1 % 2 = 1
```

所以：

```text
c = [0]
t = 1
```

---

## 第 1 位：2

```text
t = 1 * 10 + 2 = 12
商位 = 12 / 2 = 6
余数 = 12 % 2 = 0
```

所以：

```text
c = [0, 6]
t = 0
```

---

## 第 2 位：3

```text
t = 0 * 10 + 3 = 3
商位 = 3 / 2 = 1
余数 = 3 % 2 = 1
```

所以：

```text
c = [0, 6, 1]
t = 1
```

---

## 第 3 位：4

```text
t = 1 * 10 + 4 = 14
商位 = 14 / 2 = 7
余数 = 14 % 2 = 0
```

所以：

```text
c = [0, 6, 1, 7]
t = 0
```

删掉前导零后：

```text
617
```

余数是：

```text
0
```

完全正确。

---

# 六、再推一个更典型的例子

```text
7 ÷ 2
```

初始：

```text
a = [7]
t = 0
```

处理唯一一位：

```text
t = 0 * 10 + 7 = 7
商位 = 7 / 2 = 3
余数 = 7 % 2 = 1
```

所以：

* 商 = `3`
* 余数 = `1`

这和样例一致。 `/mnt/data/AcWing794高精度除法.md`

---

# 七、为什么这里不用倒序

这个点你要专门记住。

## 加、减、乘

它们通常从个位开始算，所以适合倒序存。

## 除法

它必须从高位开始试商，所以要正序存。

也就是说：

**高精度除法是高精度模板里最特别的一个。**

你以后做题时，看到“除法”，脑子里第一反应就应该是：

**从高位到低位。**

---

# 八、题解代码逐段解释

我按你上传的代码来讲。

## 1. 读入大整数和除数

```java
String sa = sc.next();
int b = sc.nextInt();
```

意思是：

* `sa` 用字符串读入超大整数
* `b` 是普通整数，直接 `int`

---

## 2. 正序拆位

```java
int la = sa.length();
int[] a = new int[la];
for(int i = 0, j = 0; i < la; i++, j++) a[i] = sa.charAt(j) - '0';
```

这段其实可以简化理解成：

```java
for (int i = 0; i < la; i++) a[i] = sa.charAt(i) - '0';
```

作用就是：

* `a[0]` 是最高位
* `a[la-1]` 是最低位

---

## 3. 调用除法函数

```java
Deque<Integer> c = new ArrayDeque<>();
int t = div(a, b, c);
```

这里：

* `c` 存商
* `div` 返回余数 `t`

这设计挺好，因为题目要求输出：

1. 商
2. 余数

---

## 4. 除法函数

```java
public static int div(int[] a, int b, Deque<Integer> c){
    int t = 0;
    for(int i = 0; i < a.length; i++){
        t = t * 10 + a[i];
        c.addLast(t / b);
        t = t % b;
    }
    while(c.size() > 1 && c.peekFirst() == 0) c.pollFirst();
    return t;
}
```

### `int t = 0;`

初始没有余数。

### `t = t * 10 + a[i];`

把上一轮余数带下来，并拼上当前位。

### `c.addLast(t / b);`

得到当前这一位商。

### `t = t % b;`

更新余数，留给下一轮。

### 去前导零

删掉商开头多余的 `0`。

### `return t;`

返回最终余数。

---

# 九、这题最容易错的地方

## 1. 还按前面几题那样倒序存

这是最常见错误。

这题不能倒序。
因为除法必须从高位开始。

---

## 2. 不理解 `t = t * 10 + a[i]`

这句如果不理解，整题就相当于没懂。

它的本质是：

**把上一轮余数“带下来”，和当前位拼成一个新数。**

---

## 3. 商的前导零没删

比如：

```text
123 ÷ 1000
```

商应该是 `0`，不是一串前导零。

---

## 4. 余数和商混了

这题输出是两行：

* 第一行商
* 第二行余数

不要只输出商。

---

# 十、这题属于什么题型

这题属于：

**高精度模板题 + 模拟竖式除法**

如果你从算法分类上记，可以记成：

* 基础算法
* 高精度
* 模拟

它不是难在思维，而是难在你能不能把“手算除法”准确翻译成代码。

---

# 十一、和前面三题连起来看

你现在可以把高精度四个基础模板连起来记：

## 高精度加法

核心：进位

## 高精度减法

核心：借位

## 高精度乘低精度

核心：乘积 + 进位

## 高精度除低精度

核心：余数下传

你会发现，它们都在模拟小学竖式，只是关注点不同。

---

# 十二、一句话总结

这题本质上就是：

**从高位到低位模拟竖式除法，用 `t` 维护当前余数，当前位商是 `t / b`，新的余数是 `t % b`。**

你最该记住的模板只有这三句：

```java
t = t * 10 + a[i];
c.addLast(t / b);
t = t % b;
```

下一步你可以自己先试着回答这两个问题，看看是否真的懂了：

1. 为什么这题不能倒序存？
2. `t = t * 10 + a[i]` 这一句到底对应手算里的哪一步？

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String s1 = bufferedReader.readLine();
        String s2 = bufferedReader.readLine();
        div(s1, s2);
    }
    public static void div(String s1, String s2) {
        int[] ints1 = new int[s1.length()];
        int int2 = Integer.parseInt(s2);
        int[] res = new int[s1.length()];

        for (int i = 0, j = 0; i < s1.length(); i++) {
            ints1[i] = s1.charAt(i) - '0';
        }
        int sum = 0;
        for (int i = 0; i < ints1.length; i++) {
            sum = sum * 10 + ints1[i];
            if (sum >= int2) {
                res[i] = sum / int2;
                sum %= int2;
            } else {
                res[i] = 0;
            }

        }
        int start;
        for (start = 0; start < res.length - 1; start++) {
            if (res[start] != 0) break;
        }
        while (start < res.length) System.out.print(res[start++]);
        System.out.println("\n" + sum);
    }
}
```