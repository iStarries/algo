# AcWing 算法基础课 -- 搜索与图论

## AcWing 845. 八数码

`难度：简单`

### 题目描述

在一个3×3的网格中，1~8这8个数字和一个“x”恰好不重不漏地分布在这3×3的网格中。

例如：

```r
1 2 3
x 4 6
7 5 8
```

在游戏过程中，可以把“x”与其上、下、左、右四个方向之一的数字交换（如果存在）。

我们的目的是通过交换，使得网格变为如下排列（称为正确排列）：

```r
1 2 3
4 5 6
7 8 x
```

例如，示例中图形就可以通过让“x”先后与右、下、右三个方向的数字交换成功得到正确排列。

交换过程如下：

```r
1 2 3   1 2 3   1 2 3   1 2 3
x 4 6   4 x 6   4 5 6   4 5 6
7 5 8   7 5 8   7 x 8   7 8 x
```

现在，给你一个初始网格，请你求出得到正确排列至少需要进行多少次交换。


**输入格式**

输入占一行，将3×3的初始网格描绘出来。

例如，如果初始网格如下所示：

```r
1 2 3

x 4 6

7 5 8

则输入为：1 2 3 x 4 6 7 5 8
```

**输出格式**

输出占一行，包含一个整数，表示最少交换次数。

如果不存在解决方案，则输出”-1”。

```r
输入样例：

2  3  4  1  5  x  7  6  8 

输出样例

19
```

### Solution

字符串处理差劲，这部分还要优化。

```java
import java.util.*;

class Main{
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        StringBuilder state = new StringBuilder();
        for(int i = 0; i < 9; i++){
            state.append(sc.next());
        }
        System.out.println(bfs(state));
    }
    public static int bfs(StringBuilder state){
        String end = "12345678x";
        Queue<StringBuilder> q = new ArrayDeque<>();
        Map<String, Integer> map = new HashMap<>();
        map.put(state.toString(),0);
        q.add(state);
        
        int[] dx = {-1, 0, 1, 0};
        int[] dy = {0, -1, 0, 1};
        
        while(!q.isEmpty()){
            StringBuilder t = q.poll();
            if(t.toString().equals(end)){
                 return map.get(t.toString());
            }
            int dist = map.get(t.toString());
            int idx = t.indexOf("x");
            // x 转化为九宫格的位置
            int x = idx / 3;
            int y = idx % 3;
            // 找到可以和 x 交换的位置
            for(int i = 0; i < 4; i++){
                int a = x + dx[i], b = y + dy[i];
                // 判断坐标是否在九宫格内
                if(a >= 0 && a < 3 && b >= 0 && b < 3){
                    swap(t, a * 3 + b, x * 3 + y);
                    if(!map.containsKey(t.toString())){
                        map.put(t.toString(), dist + 1);
                        q.add(new StringBuilder(t));
                    }
                    swap(t, a * 3 + b, x * 3 + y);
                }
            }
        }
        return -1;
    }
    public static void swap(StringBuilder t, int m, int n){
        char c = t.charAt(m);
        t.setCharAt(m, t.charAt(n));
        t.setCharAt(n, c);
    }
}
```

这题我们直接开始讲。你上传的是 **AcWing 845 八数码** 的题目和一份 BFS 题解。

## 一、题型与核心考点

这题属于：

* **搜索与图论**
* 更具体地说，是 **BFS 求最短步数**
* 本质模型是：**状态图上的最短路**

### 为什么它是 BFS 题？

题目问的是：

> 从初始状态变到目标状态，**至少需要多少次交换**

看到“**最少操作次数**”“**每次操作代价都一样**”这种字眼，就要优先想到：

* 能不能把每一种局面看成一个点
* 每一次交换看成一条边
* 然后用 **BFS** 找从起点到终点的最短距离

这正是八数码的经典建模方式。

---

## 二、核心思路与整体做法

### 1. 怎么把这题看成图？

一个 3×3 棋盘局面，就是一个“状态”。

比如：

```text
1 2 3
x 4 6
7 5 8
```

可以压成一个字符串：

```text
123x46758
```

这样每个字符串就是图中的一个点。

如果某个状态里 `x` 能和上下左右某个数交换，那么交换后会得到一个新状态，这就相当于从当前点连向另一个点。

所以整题就变成了：

* 起点：输入的初始状态
* 终点：`12345678x`
* 边：一次合法交换
* 目标：求最少交换次数

---

### 2. 为什么必须用 BFS，而不是 DFS？

因为题目求的是 **最少步数**。

* **DFS** 更适合“能不能到达”“把所有方案搜出来”
* **BFS** 更适合“边权都为 1 的最短路”

BFS 是一层一层扩展状态的：

* 第 0 层：起点
* 第 1 层：一步能到的所有状态
* 第 2 层：两步能到的所有状态
* ……

所以第一次搜到终点时，步数一定最小。

---

### 3. 具体做法是什么？

整体流程就四步：

#### 第一步：读入初始状态

把 9 个输入拼成一个字符串，例如：

```text
2 3 4 1 5 x 7 6 8
```

拼成：

```text
23415x768
```

#### 第二步：准备 BFS 队列和去重表

因为状态会重复出现，所以一定要记录某个状态是否访问过，以及它的距离。

一般用：

* `Queue<String>` 存待搜索状态
* `HashMap<String, Integer>` 存状态到起点的距离

#### 第三步：每次取出一个状态，枚举 `x` 的四个移动方向

先找到 `x` 的位置，再尝试：

* 上
* 下
* 左
* 右

若移动后仍在棋盘内，就生成新状态。

#### 第四步：新状态没出现过就入队

因为 BFS 第一次到达某个状态时，已经是最短距离，所以：

* 没访问过：记录距离并入队
* 访问过：直接跳过

一旦遇到目标状态 `12345678x`，返回距离即可。

---

## 三、朴素做法与当前做法的区别

### 朴素想法

你可能会想：

* 暴力搜索所有交换方案
* 或者 DFS 一路试到底

问题在于：

* 状态非常多
* 会走回头路
* 不能保证第一次找到就是最优解

### 当前做法

BFS + 哈希判重的优势是：

* 不会重复搜同一个状态
* 按层扩展，天然保证最短步数
* 非常适合这种“小状态空间 + 最少操作次数”的题

八数码总状态数最多是 `9! = 362880`，BFS 是可以接受的。

---

## 四、代码实现讲解

你这份代码整体思路是 **对的**，而且能够正确做出这题。核心框架没有问题。

我按照实现顺序给你拆开讲。

---

### 1. 主函数：读入初始状态

```java
Scanner sc = new Scanner(System.in);
StringBuilder state = new StringBuilder();
for(int i = 0; i < 9; i++){
    state.append(sc.next());
}
System.out.println(bfs(state));
```

这里做的事很简单：

* 输入 9 个元素
* 拼成一个字符串状态
* 交给 `bfs` 去求答案

例如输入：

```text
2 3 4 1 5 x 7 6 8
```

最终 `state` 就是：

```text
23415x768
```

---

### 2. BFS 初始化

```java
public static int bfs(StringBuilder state){
    String end = "12345678x";
    Queue<StringBuilder> q = new ArrayDeque<>();
    Map<String, Integer> map = new HashMap<>();
    map.put(state.toString(),0);
    q.add(state);
```

这里三样东西最重要：

#### `end`

目标状态。

#### `q`

BFS 队列，保存当前要扩展的状态。

#### `map`

记录每个状态到起点的最短距离。

例如：

```java
map.put(state.toString(), 0);
```

表示初始状态距离自己是 0 步。

---

### 3. 四个方向数组

```java
int[] dx = {-1, 0, 1, 0};
int[] dy = {0, -1, 0, 1};
```

表示上下左右四个方向：

* `(-1, 0)` 上
* `(0, -1)` 左
* `(1, 0)` 下
* `(0, 1)` 右

这是一种很常见的网格搜索写法。

---

### 4. BFS 主循环

```java
while(!q.isEmpty()){
    StringBuilder t = q.poll();
    if(t.toString().equals(end)){
         return map.get(t.toString());
    }
    int dist = map.get(t.toString());
```

这里的逻辑是：

* 取出当前状态 `t`
* 如果它已经是终点，直接返回最短步数
* 否则取出它的距离 `dist`

因为 BFS 是按层走的，所以此时 `dist` 一定是最短距离。

---

### 5. 找到 `x` 的位置

```java
int idx = t.indexOf("x");
// x 转化为九宫格的位置
int x = idx / 3;
int y = idx % 3;
```

这里是把一维字符串位置转成二维坐标。

例如：

```text
123x46758
```

`x` 在下标 3，那么：

* `x = 3 / 3 = 1`
* `y = 3 % 3 = 0`

也就是第 2 行第 1 列。

---

### 6. 枚举 `x` 能交换的位置

```java
for(int i = 0; i < 4; i++){
    int a = x + dx[i], b = y + dy[i];
    if(a >= 0 && a < 3 && b >= 0 && b < 3){
        swap(t, a * 3 + b, x * 3 + y);
        if(!map.containsKey(t.toString())){
            map.put(t.toString(), dist + 1);
            q.add(new StringBuilder(t));
        }
        swap(t, a * 3 + b, x * 3 + y);
    }
}
```

这是整题最核心的一段。

#### 第一步：算新坐标

`a, b` 是 `x` 移动后的坐标。

#### 第二步：判断是否合法

必须还在 3×3 范围内。

#### 第三步：交换生成新状态

用 `swap` 把 `x` 和目标位置上的字符交换。

#### 第四步：判重

如果这个新状态以前没出现过：

* 记录距离 `dist + 1`
* 加入队列

#### 第五步：恢复现场

因为你是在原来的 `t` 上直接交换的，所以用完要换回来。

这就是经典的：

* **修改**
* **判断/处理**
* **恢复**

---

### 7. swap 函数

```java
public static void swap(StringBuilder t, int m, int n){
    char c = t.charAt(m);
    t.setCharAt(m, t.charAt(n));
    t.setCharAt(n, c);
}
```

这个函数没问题，就是交换两个位置上的字符。

---

## 五、你这份代码哪里写得好，哪里还能优化

### 先说结论

这份代码 **思路正确，结果也正确，可以通过这题**。

### 优点

你已经抓住了这题的本质：

* 用字符串表示状态
* 用 BFS 求最短路
* 用哈希表判重
* 用交换生成下一层状态

这些都是标准做法。

---

### 可以优化的地方

你自己也说了：

> 字符串处理差劲，这部分还要优化。

这判断很准确。主要问题就在这里：

#### 1. 队列里放 `StringBuilder`，写起来容易绕

因为 `StringBuilder` 是可变对象，所以你必须：

* 先交换
* 再 `new StringBuilder(t)` 入队
* 再换回来

虽然你现在写对了，但这种写法更容易出错。

更稳妥的写法通常是：

* 队列里直接放 `String`
* 每次需要扩展时，把它转成字符数组或 `StringBuilder` 来生成新状态

这样逻辑更清楚。

---

#### 2. `t.toString()` 被重复调用很多次

比如这里：

```java
if(t.toString().equals(end))
int dist = map.get(t.toString());
if(!map.containsKey(t.toString()))
map.put(t.toString(), dist + 1);
```

同一个状态反复 `toString()`，会有一些不必要的开销。

更好的写法是先存一下：

```java
String s = t.toString();
```

然后统一使用 `s`。

---

#### 3. 这题还可以加“无解判定”

八数码不是所有状态都能到目标状态。

标准做法常常会先判断逆序对奇偶性：

* 若逆序对个数是奇数，则无解，直接输出 `-1`
* 若是偶数，才进行 BFS

不过即使你不写这个判定，你的代码也仍然是正确的，只是无解时会把所有可达状态搜完再返回 `-1`。

---

## 六、给你一版更规范的 Java 写法

你当前代码已经能做，这里我给你一版更常见、更稳一点的写法，方便你建立标准模板。

```java
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        StringBuilder start = new StringBuilder();
        for (int i = 0; i < 9; i++) {
            start.append(sc.next());
        }

        System.out.println(bfs(start.toString()));
    }

    public static int bfs(String start) {
        String target = "12345678x";

        Queue<String> queue = new ArrayDeque<>();
        Map<String, Integer> dist = new HashMap<>();

        queue.offer(start);
        dist.put(start, 0);

        int[] dx = {-1, 0, 1, 0};
        int[] dy = {0, 1, 0, -1};

        while (!queue.isEmpty()) {
            String cur = queue.poll();

            if (cur.equals(target)) {
                return dist.get(cur);
            }

            int distance = dist.get(cur);
            int idx = cur.indexOf('x');
            int x = idx / 3;
            int y = idx % 3;

            for (int i = 0; i < 4; i++) {
                int a = x + dx[i];
                int b = y + dy[i];

                if (a < 0 || a >= 3 || b < 0 || b >= 3) {
                    continue;
                }

                char[] chars = cur.toCharArray();
                int newIdx = a * 3 + b;

                char temp = chars[idx];
                chars[idx] = chars[newIdx];
                chars[newIdx] = temp;

                String next = new String(chars);

                if (!dist.containsKey(next)) {
                    dist.put(next, distance + 1);
                    queue.offer(next);
                }
            }
        }

        return -1;
    }
}
```

这版的好处是：

* 队列里存 `String`，不容易被可变对象坑到
* 每次扩展都从当前字符串生成新状态，逻辑更直观
* 更接近这类题的通用模板

---

## 七、为什么能想到这种解法

这部分最重要，是你以后做题识别题型的关键。

### 看到哪些信号，要想到 BFS？

以后你看到下面这些特征，就要警觉：

#### 信号 1：求最少操作次数

这题问的是“至少交换多少次”。

只要每次操作代价相同，通常就是 BFS 信号。

---

#### 信号 2：每次操作会从一个状态变到另一个状态

这题不是普通网格最短路，而是“局面的变化”。

也就是说，点不一定是二维坐标，也可以是：

* 一个字符串
* 一个数组
* 一个棋盘局面
* 一个二进制状态

只要能定义“当前状态”和“下一步能转移到哪些状态”，就能往图搜索上靠。

---

#### 信号 3：状态总数不算特别大

八数码最多 `9!` 个状态，数量虽然不少，但 BFS 还能承受。

所以这是很典型的：

* **状态压缩/状态表示**
* **BFS 最短路**

---

## 八、方法迁移

这题学完后，你要把方法迁移到这几类题：

### 1. 字符串变换类

比如：

* 一次操作把某一位改掉
* 一次操作交换某两位
* 求最少几步变成目标串

核心仍然是：

* 字符串表示状态
* BFS 搜最短步数

---

### 2. 棋盘/魔板/拼图类

比如：

* 八数码
* 魔板
* 某个棋盘局面最少几步变换

这类题本质也是状态图最短路。

---

### 3. 隐式图最短路

图没有明确给你，而是让你自己“生成下一层状态”。

这时候就要想到：

* 当前状态是什么
* 如何生成邻居状态
* 如何判重
* 是否适合 BFS

---

## 九、这题你真正要记住的一句话

**“最少操作次数 + 每次操作代价相同 + 能抽象成状态之间的转移”，优先想 BFS。**

---

你这题已经抓到主线了。下一步你最值得练的，不是再抠这题本身，而是把 **“状态表示 + 判重 + BFS 分层最短路”** 这个模板真正练熟。



```java
import java.io.*;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Queue;

class Main {
    /*
        将每一种棋盘局面看成一个 String 状态。
        使用队列按层存储当前可以扩展到的状态，利用 BFS 求最少交换次数。
        使用哈希表记录每个状态是否出现过，以及该状态到初始状态的最短距离。
        枚举 x 与上下左右相邻位置交换，生成新的状态；为了继续枚举其他方向，需要在原状态上交换后再恢复。
    */
    public static String[] input;
    public static Queue<String> q = new ArrayDeque<>();
    public static HashMap<String, Integer> map = new HashMap<>();

    public static int[] xs = {1, 0, -1, 0};
    public static int[] ys = {0, 1, 0, -1};
    public static int ans = -1;

    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));
        input = bufferedReader.readLine().split("\\s+");

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 9; i++) {
            stringBuilder.append(input[i]);
        }
        String res = "12345678x";

        q.add(stringBuilder.toString());
        //起始位置的距离是0，在后续遍历的时候会遇到起始状态，那时候已经在map里了
        map.put(stringBuilder.toString(), 0);

        while (!q.isEmpty()) {
            String s = q.poll();
            if (s.equals(res)) {
                ans = map.get(s);
                break;
            }
            StringBuilder s1 = new StringBuilder(s);
            int idx = s1.indexOf("x");
            int x = idx / 3;
            int y = idx % 3;

            for (int i = 0; i < 4; i++){
                //目标交换位置
                int newx = x + xs[i];
                int newy = y + ys[i];
                if (newx >= 0 && newx < 3 && newy >= 0 && newy < 3) {
                    String news = swap(idx, newx * 3 + newy, s1);
                    if (!map.containsKey(news)) {
                        q.add(news);
                        map.put(news, map.get(s) + 1);
                    }
                    swap(idx, newx * 3 + newy, s1);
                }
            }
        }
        //注意写进缓存的东西只能是字符
        bufferedWriter.write(String.valueOf(ans));
        bufferedReader.close();
        bufferedWriter.close();
    }

    private static String swap(int idx, int newidx, StringBuilder s1) {
        char t = s1.charAt(idx);
        s1.setCharAt(idx, s1.charAt(newidx));
        s1.setCharAt(newidx, t);
        return s1.toString();
    }


}
```