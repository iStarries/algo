# AcWing 算法基础课 -- 基础算法

## AcWing 791. 高精度加法

`难度：简单`

### 题目描述

给定两个正整数，计算它们的和

**输入格式**

共两行，每行包含一个整数。

**输出格式**

共一行，包含所求的和。

**数据范围**：
```r
1≤整数长度≤100000
```
**输入样例**：

```r
12
23
```

**输出样例**：

```r
35
```

### Solution

1. 用BitInteger来处理

```java
import java.math.BigInteger;
import java.io.*;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        BigInteger a = new BigInteger(in.readLine());
        BigInteger b = new BigInteger(in.readLine());
        System.out.println(a.add(b));
        
    }

}
```

2. 用字符串形式来，会比BigInteger的时间快一倍

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
        c = add(x, y);
        while(!c.isEmpty()){
            System.out.print(c.pop());
        }
    }
    // 人为规定 x > y
    public static Deque<Integer> add(List<Integer> x, List<Integer> y){
        if(x.size() < y.size()) return add(y, x);
        Deque<Integer> c =new ArrayDeque<>();
        // t 记录进位
        int t = 0;
        for(int i = 0; i < x.size(); i++){
            t += x.get(i);
            if(i < y.size()) t += y.get(i);
            c.push(t % 10);
            t = t / 10;
        }
        // 最后还有一个进位，别忽略
        if(t > 0) c.push(t);
        return c;
    }
}
```

3. 用数组存的方法，感觉比 List 灵活一点

```
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
        List<Integer> c = new ArrayList<>();
        for(int i = la - 1, k = 0; i >= 0; i--, k++){
            a[k] = sa.charAt(i) - '0';
        }
        for(int i = lb - 1, k = 0; i >= 0; i--, k++){
            b[k] = sb.charAt(i) - '0';
        }
        // 加法中 a 的长度 大于等于 b 的长度
        if(la > lb) c = add(a, b);
        else c = add(b, a);
        for(int i = c.size() - 1; i >= 0; i--){
            System.out.print(c.get(i));
        }
        
    }
    public static List<Integer> add(int[] a, int[] b){
        List<Integer> c = new ArrayList<>();
        // 加法，只需要记录进位
        int t = 0;
        for(int i = 0; i < a.length; i++){
            t += a[i];
            if(i < b.length) t += b[i];
            c.add(t % 10);
            t /= 10;
        }
        // 最后以防还有进位
        if(t > 0) c.add(t);
        return c;
    }
}
```
这题是**高精度加法的入门题**。

题目很简单：给两个很大的正整数，求它们的和。难点不在“加法”，而在于：

**数字太长，普通整数类型装不下。**

你上传的题解里给了三种做法：`BigInteger`、字符串+List、字符串+数组。真正算法上最值得学的是后两种，也就是**手写高精度加法**。

---

## 一、这题到底在解决什么问题

像下面这种数：

```text
123456789012345678901234567890
999999999999999999999999999999
```

长度可能到 `100000` 位。
这已经远远超过了 `int` 和 `long` 的范围，所以不能直接写：

```java
long a = ...
long b = ...
System.out.println(a + b);
```

因为根本存不下。

所以这类题的核心思想是：

**不要把整个数当作一个数存，而要把它当作“数字串”存。**

也就是把：

```text
12345
```

看成：

```text
1 2 3 4 5
```

然后模拟我们平时列竖式加法。

---

## 二、高精度加法的本质

你小时候做加法：

```text
  123
+ 789
-----
  912
```

是不是从**个位开始**算？

* 个位：`3 + 9 = 12`，写 `2`，进 `1`
* 十位：`2 + 8 + 1 = 11`，写 `1`，进 `1`
* 百位：`1 + 7 + 1 = 9`

高精度加法就是把这个过程用代码模拟出来。

所以它的核心只有一句话：

**从低位到高位逐位相加，并维护进位。**

---

## 三、为什么题解都要“倒着存”

你题解里这段：

```java
for(int i = a.length() - 1; i >= 0; i--) x.add(a.charAt(i) - '0');
for(int i = b.length() - 1; i >= 0; i--) y.add(b.charAt(i) - '0');
```

意思是把字符串倒着放进数组或 `List`。

比如：

```text
a = "1234"
```

存进去后变成：

```text
[4, 3, 2, 1]
```

为什么这样做？

因为这样下标 `0` 就是个位，下标 `1` 就是十位，下标 `2` 就是百位……

而加法本来就是从个位开始算，所以这样最顺手。

如果你不倒着存，那么个位在最后面，每次处理都很别扭。

---

## 四、核心算法怎么理解

以你题解里的 `add` 为例：

```java
public static Deque<Integer> add(List<Integer> x, List<Integer> y){
    if(x.size() < y.size()) return add(y, x);
    Deque<Integer> c =new ArrayDeque<>();
    int t = 0;
    for(int i = 0; i < x.size(); i++){
        t += x.get(i);
        if(i < y.size()) t += y.get(i);
        c.push(t % 10);
        t = t / 10;
    }
    if(t > 0) c.push(t);
    return c;
}
```

这是整题最核心的部分。

---

## 五、逐句拆开讲

### 1）先保证 `x` 比 `y` 长

```java
if(x.size() < y.size()) return add(y, x);
```

这样后面只要循环到 `x.size()` 就行了，代码更简单。

---

### 2）`t` 表示“当前位的和 + 进位”

```java
int t = 0;
```

这个 `t` 很关键。

它不是单纯记录进位，而是：

**这一位当前总共加出来的值。**

比如某一位：

```text
8 + 7 + 上一位进位1 = 16
```

那这时候：

```java
t = 16
```

然后：

* 当前位结果是 `16 % 10 = 6`
* 新的进位是 `16 / 10 = 1`

所以才有下面两句。

---

### 3）把当前位两个数加到 `t` 上

```java
t += x.get(i);
if(i < y.size()) t += y.get(i);
```

因为 `x` 一定更长，所以每一位都有 `x.get(i)`。
而 `y` 可能已经没位数了，所以要先判断。

---

### 4）当前位应该存什么

```java
c.push(t % 10);
```

比如 `t = 16`：

* 当前位写 `6`
* 进位留给下一位

所以当前位是：

```java
t % 10
```

---

### 5）更新进位

```java
t = t / 10;
```

比如 `16 / 10 = 1`，说明向高位进 `1`。

---

### 6）最后别忘了还有进位

```java
if(t > 0) c.push(t);
```

例如：

```text
999 + 1 = 1000
```

你会发现最后一位算完以后，还有一个进位 `1`，必须补上。

这一步非常重要，很多初学者会漏。

---

## 六、为什么这里用 `push`

这里你题解用的是：

```java
Deque<Integer> c = new ArrayDeque<>();
c.push(...)
```

这是因为前面两个数是**倒着存的**，运算顺序是从低位到高位。
但最后输出时，我们希望从高位到低位打印。

所以这里直接把结果不断 `push` 到栈里，最后再 `pop` 出来，就正好是正常顺序。

例如：

计算出低位到高位依次是：

```text
5 7 9
```

如果每次 `push`：

* push 5
* push 7
* push 9

栈顶会变成：

```text
9 7 5
```

这样最后打印就是正常顺序。

---

## 七、手推一个例子

我们用：

```text
a = 123
b = 89
```

倒着存后：

```text
x = [3, 2, 1]
y = [9, 8]
```

### 第 0 位

```text
t = 0 + 3 + 9 = 12
当前位 = 12 % 10 = 2
进位 = 12 / 10 = 1
```

### 第 1 位

```text
t = 1 + 2 + 8 = 11
当前位 = 1
进位 = 1
```

### 第 2 位

```text
t = 1 + 1 = 2
当前位 = 2
进位 = 0
```

最后结果是：

```text
212
```

也就是：

```text
123 + 89 = 212
```

---

## 八、这题真正要掌握的“模板感”

高精度加法模板的核心非常固定：

### 步骤 1

把字符串倒序存储，每个位置存一位数字。

### 步骤 2

从低位到高位遍历。

### 步骤 3

用 `t` 维护“当前和 + 进位”。

### 步骤 4

当前位存 `t % 10`，进位变成 `t / 10`。

### 步骤 5

最后如果进位不为 0，要补上。

这五步就是整道题的本质。

---

## 九、为什么这题是高精度的基础题

因为高精度四则运算里：

* 加法最简单，只处理进位
* 减法会多一个借位
* 乘法更复杂
* 除法也更复杂

所以这题其实是在给你打地基。

如果这题理解了，后面的高精度减法、乘低精度、除低精度就会顺很多。

---

## 十、`BigInteger` 要不要学

题解里也给了：

```java
BigInteger a = new BigInteger(in.readLine());
BigInteger b = new BigInteger(in.readLine());
System.out.println(a.add(b));
```

这个当然能做，而且写起来很短。

但如果你现在是学算法，重点不是“把题做出来”，而是学会：

**当语言不给你现成大整数类时，你自己怎么实现。**

所以算法课里更应该掌握手写高精度。

---

## 十一、数组版和 List 版有什么区别

你题解里还有数组版。它和 `List` 版本质完全一样，只是存储方式不同。

你现在可以这样理解：

* `List<Integer>`：写法灵活，容易理解
* `int[]`：更接近底层，性能通常更稳定

对初学阶段来说，先看懂“逐位相加 + 进位”最重要，容器不是核心。

---

## 十二、这题最容易错的地方

你做这类题时，最容易错的是这几个点：

### 1. 忘记倒序存

这样会导致你从高位开始加，逻辑就乱了。

### 2. 忘记判断短数字是否越界

比如：

```java
if(i < y.size()) ...
```

### 3. 忘记最后一个进位

比如 `999 + 1` 会错。

### 4. 输出顺序反了

因为内部通常是倒着存的，打印时要反过来。

---

## 十三、你现在先记住一句话

**高精度加法 = 模拟竖式加法。**

只不过：

* 人手算是写在纸上
* 代码里是把每一位存进数组或 List

---

## 十四、最短总结

这题的核心不是 Java 语法，而是算法思想：

**把大整数拆成每一位，从个位开始逐位相加，维护进位。**

---

你下一步最适合问的是这个：

**为什么高精度一定喜欢把个位放在下标 0？**

这个点一旦彻底明白，后面高精度减法、乘法都会更容易。

## 我的：不标准解法（去学习题解三）
```java
import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String s = bufferedReader.readLine();
        int len = s.length();
        char[] s1 = s.toCharArray();
        int[] n1 = new int[100010];
        for (int i = 0; i < len; i++) {
            n1[i] = s1[len - i - 1] - '0';
        }
        s = bufferedReader.readLine();
        len = s.length();
        s1 = s.toCharArray();
        int[] n2 = new int[100010];
        for (int i = 0; i < len; i++) {
            n2[i] = s1[len - i - 1] - '0';
        }
        int[] res = new int[100010];

        int i = 0;
        int t = 0;
        for (; i < 100009; i++) {
            t = n1[i] + n2[i] + t;
            res[i] = t % 10;
            t /= 10;
        }
        int start = 100009;
        for (int j = 100009; j >= 0; j--) {
            if (res[j] == 0){
                start = j;
            }
            else break;
        }
        if (start == 0){
            System.out.println(0);
            return;
        }
        for (int j = start - 1; j >= 0; j--) {
            System.out.print(res[j]);
        }
    }
}
```